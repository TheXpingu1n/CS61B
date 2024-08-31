import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> mp = new HashMap<>();
        char c = 'A';
        for(int i = 1; i <= 26; i++)
        {
            mp.put(c,i);
            c++;
        }
        return mp;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            mp.put(nums.get(i), (int) Math.pow(nums.get(i), 2));
        }
        return mp;
    }

    public static void main(String[] args) {

    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> mp = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            if(mp.containsKey(words.get(i)))
            {
                int v = mp.get(words.get(i));
                mp.put(words.get(i),v++);
            }
            else
                mp.put(words.get(i),1);
        }
        return mp;
    }
}
