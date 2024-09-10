import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
public class ArrayDeque61B<T> implements Deque61B<T>
{
    private T[] deque;
    private int size;
    private int tail;
    private int head;
    private double usageF;
    private int arr_size;
    public ArrayDeque61B() {
        deque = (T[]) new Object[8];
        arr_size = this.deque.length;
    }
    public int getarr()
    {
       return arr_size;
    }
    @Override
    public void addFirst(T x)
    {
        size++;
        usageF = (double) this.size/this.deque.length;
        head--;
        if(head < 0)
            head = Math.floorMod(head,this.deque.length);
        this.deque[head] = x;
        if(size == deque.length)
            resize(2);
    }

    @Override
    public void addLast(T x)
    {
        size++;
        this.deque[tail] = x;
        usageF = (double) this.size/this.deque.length;
        tail++;
        if(tail >= this.deque.length)
            tail = Math.floorMod(tail,this.deque.length);
        if(size == this.deque.length)
            resize(2);
    }

    @Override
    public List<T> toList() {
        if(this.size == 0)
            return List.of();
        List<T> returnList = new ArrayList<>();
        int counter = 0;
        for (int i = this.head; i < this.deque.length; i++)
        {
            if(counter >= size)
                break;

            returnList.add(this.deque[i]);
            counter++;
            if(i == this.deque.length-1)
            {
                    i = -1;
            }
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if(size == 0)
            return null;
        size--;
        usageF = (double) this.size/this.deque.length;
        if(usageF <= 0.25 && this.deque.length >= 16)
        {
            resize(0.5);
        }
        T item = this.deque[head];
        head++;
        if(head >= this.deque.length)
            head = Math.floorMod(head,this.deque.length);
        return item;
    }

    @Override
    public T removeLast()
    {
        if(size == 0)
            return null;
        size--;
        tail--;
        if(tail < 0)
            tail = Math.floorMod(tail,this.deque.length);
        T item = this.deque[tail];
        usageF = (double) this.size/this.deque.length;
        if(usageF <= 0.25 && this.deque.length >= 16)
        {
            resize(0.5);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if(index >= this.size || index < 0)
            return null;
        int ind = Math.floorMod(this.head+index,this.deque.length);
        return this.deque[ind];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
   /** private void resizeUp()
    {
        this.arr_size = (int) (2*this.deque.length);
        T[] newArray = (T[]) new Object[this.arr_size];
        int portionFromHeadtoEnd = this.deque.length - this.head;
        int start = this.arr_size - portionFromHeadtoEnd;
        int tempHead = this.head;
        int counter = 0;
        for (int i = start; i < newArray.length; i++)
        {
            counter++;
            newArray[i] = this.deque[tempHead];
            tempHead++;
        }
        for (int i = 0; i < this.tail; i++) {
            if(counter >= this.size)
                break;
            newArray[i] = this.deque[i];
        }

        this.head = start;
        this.deque = newArray;
    }
   private void resizeDown()
    {
        int start;
        this.arr_size = (int) (0.5*this.deque.length);
        T[] newArray = (T[]) new Object[this.arr_size];
        int temp = this.head;
        int part = (this.deque.length - this.head);
        if(arr_size == 8)
            start = -1*(this.arr_size - part);
        else
            start = (this.arr_size - part);

        int cnt = 0;
        for (int i = start; i < newArray.length; i++) {
            newArray[i] = this.deque[temp];
            temp++;
            cnt++;
        }
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = this.deque[i];
            cnt++;
            if(cnt >= size)
            {
                break;
            }
        }
        this.head = start;
        this.deque = newArray;
    } **/
    public void resize(double scale)
    {
        int start;
        this.arr_size = (int) (scale*this.deque.length);
        T[] newArray = (T[]) new Object[this.arr_size];
        int temp = this.deque.length-1;
        int part = (this.deque.length - this.head);
        start = Math.floorMod((this.arr_size - part),this.arr_size);
        int cnt = 0;
        for (int i = newArray.length-1; i >= start; i--) {
            newArray[i] = this.deque[temp];
            temp--;
            cnt++;
        }
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = this.deque[i];
            cnt++;
            if(cnt >= size)
            {
                break;
            }
        }
        this.head = start;
        this.deque = newArray;
    }
}
