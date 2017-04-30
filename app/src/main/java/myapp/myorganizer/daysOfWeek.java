package myapp.myorganizer;

import java.util.ArrayList;

/**
 * Created by Amit Desai on 30-01-2016.
 */
public class daysOfWeek {
    String day;
    int i = 0;
    Lecture lec[]= new Lecture[10];
    daysOfWeek(String d)
    {
        day = d;
    }
    void addLecture(String addlec)
    {
        lec[i] = new Lecture(addlec);
        i++;
    }
    void display(ArrayList ttItems) {
        int j;
        for (j = 0; j < i; j++) {
            ttItems.add(j, lec[j].lect);

            // System.out.println(lec[j].lect);
        }
    }
}
