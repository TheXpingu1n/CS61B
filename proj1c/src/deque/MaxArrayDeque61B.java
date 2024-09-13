    package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B {

    private Comparator<T> c;
    public MaxArrayDeque61B(Comparator<T> c)
    {
        this.c = c;
    }
    public T max()
    {
        if(super.isEmpty())
            return null;
        T item = (T) super.get(0);
        for (int i = 0; i < super.size(); i++) {
            T comp = (T) super.get(i);
            if(this.c.compare(item, comp) < 0)
            {
                item = comp;
            }
        }
        return item;
    }
    public T max(Comparator<T> c)
    {
        if(super.isEmpty())
            return null;
        T item = (T) super.get(0);
        for (int i = 0; i < super.size(); i++) {
            T comp = (T) super.get(i);
            if(c.compare(item, comp) < 0)
            {
                item = comp;
            }
        }
        return item;
    }
}
