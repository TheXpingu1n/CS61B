import java.util.Arrays;

public class CS61B {
// Variables (part a)
    private static String university = "UC Berkeley";
    private String semester;
    private CS61BStudent[] students;
    private CS61BStudent[] signups;
    private int capacity;
// Constructor (part b)
    public CS61B(int capacity,CS61BStudent[] students,String semester)
    {
        this.capacity = capacity;
        this.semester = semester;
        this.signups = students;
    }
// Methods (part c)
/** Makes every CS61BStudent enrolled in this semester of the course watch lecture. Returns the
 number of students who actually watched lecture. */
/** Takes in a new university name newUniversity and changes the university
 for all semesters of CS61B to newUniversity. */
    public int makeStudentsWatchLecture()
    {
        int watched = 0;
        for (int i = 0; i < capacity; i++) {
            if(students[i].watchLecture())
                watched++;
        }
        return watched;
    }
public static void changeUniversity(String newUniversity)
{
    university = newUniversity;
}
// Expansion (part d)
/** Expands the course to the given capacity. */
    public void expand(int newCapacity)
    {
        capacity = newCapacity;
    }
}
