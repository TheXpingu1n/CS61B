import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    public void add_first_from_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        test.addFirst(1);
        assertThat(test.toList()).isEqualTo(expected);
    }
    @Test
    public void add_last_from_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        test.addLast(1);
        assertThat(test.toList()).isEqualTo(expected);
    }
    @Test
    public void add_first_nonempty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(4);
        test.addFirst(5);
        assertThat(test.toList()).containsExactly(5,4,2,1).inOrder();
    }
    @Test
    public void add_last_nonempty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(1);
        test.addLast(2);
        test.addLast(4);
        test.addLast(5);
        assertThat(test.toList()).containsExactly(1,2,4,5).inOrder();
    }
    @Test
    public void addition_is_working()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addLast(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addLast(7);
        test.addLast(69);
        test.addFirst(50);
        test.addLast(2);
        // [50,12,3,1,5,7,69,2]
        assertThat(test.toList()).containsExactly(50,12,3,1,5,7,69,2).inOrder();
    }
    @Test
    public void to_list_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        assertThat(test.toList()).isEmpty();
    }
    @Test
    public void to_list_nonempty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(32);
        assertThat(test.toList()).isNotEmpty();
    }
    @Test
    public void get_valid()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addLast(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addLast(7);
        test.addLast(69);
        test.addFirst(50);
        test.addLast(2);
        // [50,12,3,1,5,7,69,2]
        assertThat(test.get(7)).isEqualTo(2);
        assertThat(test.get(0)).isEqualTo(50);
        assertThat(test.get(3)).isEqualTo(1);
        assertThat(test.get(4)).isEqualTo(5);
        assertThat(test.get(5)).isEqualTo(7);
        assertThat(test.get(6)).isEqualTo(69);
        assertThat(test.get(1)).isEqualTo(12);
        assertThat(test.get(2)).isEqualTo(3);
    }
    @Test
    public void get_oob_large()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addLast(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addLast(7);
        test.addLast(69);
        test.addFirst(50);
        test.addLast(2);
        // [50,12,3,1,5,7,69,2]
        assertThat(test.get(8)).isEqualTo(null);
        assertThat(test.get(12)).isEqualTo(null);
    }
    @Test
    public void get_oob_neg()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addLast(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addLast(7);
        test.addLast(69);
        test.addFirst(50);
        test.addLast(2);
        // [50,12,3,1,5,7,69,2]
        assertThat(test.get(-1)).isEqualTo(null);
        assertThat(test.get(-23)).isEqualTo(null);
    }
    @Test
    public void is_empty_true()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        assertThat(test.isEmpty()).isTrue();
    }
    @Test
    public void is_empty_false()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        assertThat(test.isEmpty()).isFalse();
    }
    @Test
    public void size()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        assertThat(test.size()).isEqualTo(3);
    }
    @Test
    public void remove_first()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        int d = test.removeFirst();
        assertThat(test.size()).isEqualTo(2);
        assertThat(d).isEqualTo(14);
        assertThat(test.toList()).containsExactly(12,0).inOrder();
    }
    @Test
    public void remove_last()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        int d = test.removeLast();
        assertThat(test.size()).isEqualTo(2);
        assertThat(d).isEqualTo(0);
        assertThat(test.toList()).containsExactly(14,12).inOrder();
    }
    @Test
    public void remove_first_to_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);

        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        assertThat(test.size()).isEqualTo(0);
        assertThat(test.isEmpty()).isTrue();
        assertThat(test.toList()).isEmpty();
    }
    @Test
    public void remove_last_to_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        assertThat(test.size()).isEqualTo(0);
        assertThat(test.isEmpty()).isTrue();
        assertThat(test.toList()).isEmpty();
    }
    @Test
    public void remove_first_to_one()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        //14,12,0
        test.removeFirst();
        test.removeFirst();
        assertThat(test.size()).isEqualTo(1);
        assertThat(test.toList()).containsExactly(0).inOrder();
    }
    @Test
    public void remove_last_to_one()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        //14,12,0
        test.removeLast();
        test.removeLast();
        assertThat(test.size()).isEqualTo(1);
        assertThat(test.toList()).containsExactly(14).inOrder();
    }
    @Test
    public void add_first_after_remove_to_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.addFirst(3);
        test.addFirst(9);
        test.addFirst(12);
        test.addFirst(18);
        assertThat(test.toList()).containsExactly(18,12,9,3).inOrder();
        assertThat(test.size()).isEqualTo(4);
    }
    @Test
    public void add_last_after_remove_to_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.addLast(3);
        test.addLast(9);
        test.addLast(12);
        test.addLast(18);
        assertThat(test.toList()).containsExactly(3,9,12,18).inOrder();
        assertThat(test.size()).isEqualTo(4);
    }
    @Test
    public void size_after_remove_to_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        assertThat(test.size()).isEqualTo(0);
    }
    @Test
    public void size_after_remove_from_empty()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(12);
        test.addFirst(14);
        test.addLast(0);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        assertThat(test.size()).isEqualTo(0);
    }
    @Test
    public void StringDequeAndNulls()
    {
        Deque61B<String> test = new ArrayDeque61B();
        test.addFirst("cat");
        test.addLast("dog");
        test.addFirst("turtle");
        test.addFirst("human");
        test.addLast(null);
        test.addFirst(null);
        // null,human,turtle,cat,dog,null
        assertThat(test.size()).isEqualTo(6);
        assertThat(test.toList()).containsExactly(null,"human","turtle","cat","dog",null).inOrder();
    }
    @Test
    public void add_mix_trigger_resize()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addLast(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addLast(7);
        test.addLast(69);
        test.addFirst(50);
        test.addLast(2);
        test.addFirst(73);
        // [73,50,12,3,1,5,7,69,2]
        assertThat(test.toList()).containsExactly(73,50,12,3,1,5,7,69,2).inOrder();
        assertThat(test.size()).isEqualTo(9);
    }
    @Test
    public void add_first_trigger_resize()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addFirst(1);
        test.addFirst(5);
        test.addFirst(3);
        test.addFirst(12);
        test.addFirst(7);
        test.addFirst(69);
        test.addFirst(50);
        test.addFirst(2);
        test.addFirst(73);
        // [73,2,50,69,7,12,3,5,1]
        assertThat(test.toList()).containsExactly(73,2,50,69,7,12,3,5,1).inOrder();
        assertThat(test.size()).isEqualTo(9);
    }
    @Test
    public void add_last_trigger_resize()
    {
        Deque61B<Integer> test = new ArrayDeque61B();
        test.addLast(1);
        test.addLast(5);
        test.addLast(3);
        test.addLast(12);
        test.addLast(7);
        test.addLast(69);
        test.addLast(50);
        test.addLast(2);
        test.addLast(73);
        // [1,5,3,12,7,69,50,2,73]
        assertThat(test.toList()).containsExactly(1,5,3,12,7,69,50,2,73).inOrder();
        assertThat(test.size()).isEqualTo(9);
    }
    @Test
    public void remove_first_trigger_resize()
    {
        ArrayDeque61B<Integer> test = new ArrayDeque61B();
        for (int i = 0; i < 16; i++) {
            test.addFirst(i);
        }
        for (int i = 0; i < 8; i++) {
            test.removeFirst();
        }
        assertThat(test.getarr()).isEqualTo(16);
    }
    @Test
    public void remove_last_trigger_resize()
    {
        ArrayDeque61B<Integer> test = new ArrayDeque61B();
        for (int i = 0; i < 16; i++) {
            test.addFirst(i);
        }
        for (int i = 0; i < 8; i++) {
            test.removeLast();
        }
        assertThat(test.getarr()).isEqualTo(16);
    }
    @Test
    public void resize_up_and_resize_down()
    {
        ArrayDeque61B<Integer> test = new ArrayDeque61B();
        for (int i = 0; i < 16; i++) {
            test.addFirst(i);
        }
        for (int i = 0; i < 8; i++) {
            test.removeLast();
        }
        assertThat(test.getarr()).isEqualTo(16);
        for (int i = 0; i < 4; i++) {
            test.removeFirst();
        }
        assertThat(test.getarr()).isEqualTo(8);
    }
    @Test
    public void resize_up_and_resize_down_extreme()
    {
        ArrayDeque61B<Integer> test = new ArrayDeque61B();
        for (int i = 0; i < 100; i++) {
            test.addFirst(i);
        }
        for (int i = 0; i < 75; i++) {
            test.removeLast();
        }
       // assertThat(test.getarr()).isEqualTo();
        for (int i = 0; i < 13; i++) {
            test.removeFirst();
        }
        assertThat(test.getarr()).isEqualTo(32);
    }
}
