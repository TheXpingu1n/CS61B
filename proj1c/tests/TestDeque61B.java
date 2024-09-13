import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class TestDeque61B {
    @Test
    public void LLD_Test_of_for_each()
    {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]

        assertThat(lld1).containsExactly("front", "middle", "back");
        Deque61B<String> lld2 = new LinkedListDeque61B<>();
        assertThat(lld2).containsExactly();
    }
    @Test
    public void AL_Test_of_for_each()
    {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addFirst(1); //1
        lld1.addLast(2); //1,2
        lld1.addLast(5); //1,2,5
        lld1.addFirst(3);//3,1,2,5
        lld1.addFirst(9);//9,3,1,2,5
        lld1.addFirst(69);//69,9,3,1,2,5
        lld1.addLast(90);//69,9,3,1,2,5,90
        lld1.addLast(12);//69,9,3,1,2,5,90,12
        lld1.addLast(89);//69,9,3,1,2,5,90,12,89
        assertThat(lld1).containsExactly(69,9,3,1,2,5,90,12,89).inOrder();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        assertThat(lld2).containsExactly();
    }
    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testNotEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("mid");
        lld2.addLast("back");

        assertThat(lld1).isNotEqualTo(lld2);
    }
    @Test
    public void testEqualDeques61BInt() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testNotEqualDeques61BInt() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(3);
        lld1.addLast(2);

        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);

        assertThat(lld1).isNotEqualTo(lld2);
    }
    @Test
    public void testEqualADeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testNotEqualADeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("mid");
        lld2.addLast("back");

        assertThat(lld1).isNotEqualTo(lld2);
    }
    @Test
    public void testNotEqualADeques61BInt() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addLast(1);
        lld2.addLast(3);
        lld2.addLast(2);

        assertThat(lld1).isNotEqualTo(lld2);
    }
    @Test
    public void testEqualADeques61BInt() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addLast(1);
        lld2.addLast(2);
        lld2.addLast(3);

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testEqualADeques61Bempty() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testEqualDeques61Bempty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void toStringTest()
    {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");
        assertThat(lld1.toString()).isEqualTo(lld2.toString());
        Deque61B<String> lld3 = new LinkedListDeque61B<>();
        Deque61B<String> lld4 = new ArrayDeque61B<>();
        assertThat(lld3.toString()).isEqualTo(lld4.toString());
        Deque61B<Integer> lld5 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld6 = new ArrayDeque61B<>();
        lld5.addLast(1);
        lld5.addLast(2);
        lld5.addLast(3);
        lld6.addLast(1);
        lld6.addLast(2);
        lld6.addLast(2);
        assertThat(lld5.toString()).isNotEqualTo(lld6.toString());
    }
}
