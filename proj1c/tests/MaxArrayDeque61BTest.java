import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    private static class IntegerLengthComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }
    private static class DoubleLengthComparator implements Comparator<Double> {
        public int compare(Double a, Double b) {
            if(a-b > 0)
                return 1;
            else if (a-b < 0) {
                return -1;
            }
            return 0;
        }
    }
    private static class CharLengthComparator implements Comparator<Character> {
        public int compare(Character a, Character b) {
            return a - b;
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
        assertThat(mad.max(new StringLengthComparator())).isEqualTo("fury road");
    }
    @Test
    public void basicTestInt() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerLengthComparator());
        mad.addFirst(1);
        mad.addFirst(69);
        mad.addFirst(3);
        mad.addFirst(4);
        assertThat(mad.max()).isEqualTo(69);
        assertThat(mad.max(new IntegerLengthComparator())).isEqualTo(69);
    }
    @Test
    public void basicTestChar() {
        MaxArrayDeque61B<Character> mad = new MaxArrayDeque61B<>(new CharLengthComparator());
        MaxArrayDeque61B<Character> m = new MaxArrayDeque61B<>(new CharLengthComparator());
        mad.addFirst('a');
        mad.addFirst('b');
        mad.addFirst('q');
        mad.addFirst('v');
        assertThat(mad.max()).isEqualTo('v');
        assertThat(mad.max(new CharLengthComparator())).isEqualTo('v');
        assertThat(m.max()).isEqualTo(null);
        assertThat(m.max(new CharLengthComparator())).isEqualTo(null);
    }
    @Test
    public void naturalOrderTest()
    {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        m.addFirst(22);
        m.addFirst(69);
        m.addFirst(128);
        m.addFirst(4000);
        assertThat(m.max()).isEqualTo(4000);
    }
    @Test
    public void basicTestDouble() {
        MaxArrayDeque61B<Double> mad = new MaxArrayDeque61B<>(new DoubleLengthComparator());
        mad.addFirst(1.7);
        mad.addFirst(69.8);
        mad.addFirst(3.14);
        mad.addFirst(4.2);
        assertThat(mad.max()).isEqualTo(69.8);
        assertThat(mad.max(new DoubleLengthComparator())).isEqualTo(69.8);
    }
}
