import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         assertThat(lld1.toList()).containsExactly("front").inOrder();
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

       assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void SizeChangesWithAddition()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]

        assertThat(lld1.size()).isEqualTo(3);
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.size()).isEqualTo(5);
    }
    @Test
    public void testSizeZero()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addFirst(5);
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }
    @Test
    public void testSizeOne()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        assertThat(lld1.size()).isEqualTo(1);
    }
    @Test
    public void testSizeAndIsEmpty()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isFalse();
    }
    @Test
    public void testGetWithValidIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(22); //22
        lld1.addFirst(25);//25,22
        lld1.addLast(10);//25,22,10
        lld1.addLast(19);
        //25,22,10,19
        assertThat(lld1.get(0)).isEqualTo(25);
        assertThat(lld1.get(1)).isEqualTo(22);
        assertThat(lld1.get(2)).isEqualTo(10);
        assertThat(lld1.get(3)).isEqualTo(19);
    }
    @Test
    public void testGetWith1stIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.get(0)).isNotNull();
        assertThat(lld1.get(0)).isEqualTo(33);
    }
    @Test
    public void testGetWithUnvalidIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.get(-1)).isNull();
    }
    @Test
    public void testGetWithLargeIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.get(4)).isNull();
    }
    @Test
    public void testGetRWithValidIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(22); //22
        lld1.addFirst(25);//25,22
        lld1.addLast(10);//25,22,10
        lld1.addLast(19);
        //25,22,10,19
        assertThat(lld1.getRecursive(0)).isEqualTo(25);
        assertThat(lld1.getRecursive(1)).isEqualTo(22);
        assertThat(lld1.getRecursive(2)).isEqualTo(10);
        assertThat(lld1.getRecursive(3)).isEqualTo(19);
    }
    @Test
    public void testGetRWith1stIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.getRecursive(0)).isNotNull();
        assertThat(lld1.getRecursive(0)).isEqualTo(33);
    }
    @Test
    public void testGetRWithUnvalidIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.getRecursive(-1)).isNull();
    }
    @Test
    public void testGetRWithLargeIndex()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(33);
        assertThat(lld1.getRecursive(4)).isNull();
    }
    @Test
    public void testRemoveFirst()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(22); //22
        lld1.addFirst(25);//25,22
        lld1.addLast(10);//25,22,10
        lld1.addLast(19);
        //25,22,10,19
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(22,10,19).inOrder();
    }

    @Test
    public void testRemoveLast()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(22); //22
        lld1.addFirst(25);//25,22
        lld1.addLast(10);//25,22,10
        lld1.addLast(19);
        //25,22,10,19
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(25,22,10).inOrder();
    }
    @Test
    public void testRemoveAll()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(22); //22
        lld1.addFirst(25);//25,22
        lld1.addLast(10);//25,22,10
        lld1.addLast(19);
        //25,22,10,19
        lld1.addLast(23);
        //25,22,10,19,23
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
       // lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(25).inOrder();
    }
    @Test
    public void to_list_empty()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        ArrayList<Integer> lld2 = new ArrayList<>();
        assertThat(lld1.toList()).containsExactlyElementsIn(lld2).inOrder();
    }
    @Test
    public void to_list_nonempty()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        assertThat(lld1.toList()).containsExactly(1,2,3).inOrder();
    }
    @Test
    public void add_first_after_remove_to_empty()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        ArrayList<Integer> lld2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lld1.addFirst(i);
            lld2.addFirst(i);
        }
        for (int i = 0; i < 20; i++) {
            lld1.removeFirst();
            lld2.removeFirst();
        }
        for (int i = 0; i < 20; i++) {
            lld1.addFirst(i);
            lld2.addFirst(i);
        }
        assertThat(lld1.toList()).containsExactlyElementsIn(lld2).inOrder();
    }
    @Test
    public void add_last_after_remove_to_empty()
    {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        ArrayList<Integer> lld2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lld1.addFirst(i);
            lld2.addFirst(i);
        }
        assertThat(lld1.toList()).containsExactlyElementsIn(lld2).inOrder();
        for (int i = 0; i < 20; i++) {
            lld1.removeLast();
            lld2.removeLast();
        }
        assertThat(lld1.toList()).containsExactlyElementsIn(lld2).inOrder();
        for (int i = 0; i < 20; i++) {
            lld1.addLast(i);
            lld2.addLast(i);
        }
        assertThat(lld1.toList()).containsExactlyElementsIn(lld2).inOrder();
    }

}