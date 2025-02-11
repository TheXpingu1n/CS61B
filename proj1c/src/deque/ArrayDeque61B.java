package deque;

import java.util.ArrayList;
import java.util.Iterator;
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
        arr_size = (int) ((int) deque.length*scale);
        T[] newArray = (T[]) new Object[arr_size];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        head = 0;
        tail = size;
        this.deque = newArray;
    }
    @Override
    public Iterator<T> iterator()
    {
        return new it(head);
    }
    public class it implements Iterator<T>
    {
        private int pos;
        private int head;

        public it(int head)
        {
            pos = 0;
            this.head = head;

        }
        @Override
        public boolean hasNext()
        {
            return pos < size;
        }
        @Override
        public T next() {
            T item = deque[head];
            pos++;
            head++;
            head = Math.floorMod(head, deque.length);
            return item;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof ArrayDeque61B<?> arr)
        {
            if(this.size != arr.size)
                return false;
            int j = 0;
            for(T i : this)
            {
                if(!i.equals(arr.get(j)))
                    return false;
                j++;
            }
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        StringBuilder returnStr = new StringBuilder("[");
        for(int i = 0; i < this.size; i++)
        {
            T  item = get(i);
            returnStr.append(item);
            if(i != this.size-1)
               returnStr.append(", ");
        }
        returnStr.append("]");
        return returnStr.toString();
    }
}
