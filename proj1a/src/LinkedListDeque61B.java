import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>
{
    private Node sentinel;
    private int size;

    public class Node{
        public Node prev;
        public T value;
        public Node next;
        public Node(Node prev, T value, Node next)
        {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
    public LinkedListDeque61B() {
        this.sentinel = new Node(null, null, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }
    @Override
    public void addFirst(T x) {
        size++;
        Node n = new Node(this.sentinel,x,this.sentinel.next);
        this.sentinel.next.prev = n;
        this.sentinel.next = n;
    }

    @Override
    public void addLast(T x) {
        size++;
       Node p = new Node(this.sentinel.prev,x,this.sentinel);
       this.sentinel.prev.next = p;
       this.sentinel.prev = p;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = this.sentinel;
        int counter = 0;
        while(counter < size)
        {
            p = p.next;
            returnList.add(p.value);
            counter++;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if(size <= 0)
            return null;
        size--;
        T item = this.sentinel.next.value;
        this.sentinel.next = this.sentinel.next.next;
        this.sentinel.next.prev.prev = null;
        this.sentinel.next.prev = this.sentinel;
        return item;
    }

    @Override
    public T removeLast() {
        if(size <= 0)
            return null;
        size--;
        T item = this.sentinel.prev.value;
        this.sentinel.prev.next = null;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.sentinel.prev.next = this.sentinel;
        return item;
    }

    @Override
    public T get(int index)
    {
        int counter = 0;
        Node p = this.sentinel.next;
        T val = p.value;
        if(index >= this.size || index < 0)
            return null;

        while(counter < index)
        {
            counter++;
            p = p.next;
            val = p.value;
        }
        return val;
    }

    @Override
    public T getRecursive(int index) {
        Node p = this.sentinel.next;
        if(index >= this.size || index < 0)
            return null;

        return helper(p,0, index);
    }
    public T helper(Node node, int start, int index)
    {
        if(index == start)
            return node.value;

        return helper(node.next,start+1,index);
    }
}
