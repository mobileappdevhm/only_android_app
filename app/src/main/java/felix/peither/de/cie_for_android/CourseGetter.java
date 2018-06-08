package felix.peither.de.cie_for_android;

import java.util.ArrayList;

public class CourseGetter {

    public ArrayList<Course> getCourses() {
        ArrayList<Course> retList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            retList.add(new Course("Course" + Integer.toString(i), "Prof." + Integer.toString(i), i, "Lothstraße 64."));
        }

        return retList;
    }
}
