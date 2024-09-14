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
        T item = this.deque[head];
        head++;
        if(head >= this.deque.length)
            head = Math.floorMod(head,this.deque.length);
        if(usageF <= 0.25 && this.deque.length >= 16)
        {
            resize(0.5);
        }

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

    public void resize(double scale)
    {
        arr_size = (int) ((int) deque.length*scale);
        T[] newArray = (T[]) new Object[arr_size];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        head = 0;
        tail = size;
        this.deque = newArray;
    }
}
