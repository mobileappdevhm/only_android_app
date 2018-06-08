package felix.peither.de.cie_for_android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CourseGetter {

    public List<Course> getCourses() {
        ArrayList<String> response = new ArrayList<>();

        try {
            URL url = new URL("https://nine.wi.hm.edu/api/v2/courses/FK%2013/CIE/SoSe%2018 ");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                String[] lines = line.split(",");
                for (String newLine: lines) {
                    response.add(newLine);
                    response.add("\r\n");
                }
            }
            rd.close();

        } catch (Exception ex) {

        }

        return make_courses(response);
    }

    private List<Course> make_courses(List<String> lines) {
        List<Course> courseList = new ArrayList<>();

        String tmpName = "bla";
        String tmpShortName = "bla";
        String tmpProfessor = "bla";
        String tmpCourse_ID = "bla";
        String tmpLocation = "bla";
        String tmpDescription = "bla";



        for (String line: lines) {
            if (line.contains("\"id\"")) {
                tmpCourse_ID = line; //.substring(6, line.length() - 2);
            } else if (line.contains("\"lecturer\"")) {
                tmpProfessor = line;
            } else if (line.contains("\"description\"")) {
                tmpDescription = line;
            } else if (line.contains("\"name\"")) {
                tmpName = line;
            } else if (line.contains("\"shortName\"")) {
                tmpShortName = line;
                courseList.add(new Course("" + tmpName, "" + tmpShortName, "" + tmpProfessor, "" + tmpCourse_ID, "" + tmpLocation, "" + tmpDescription));
            } else if (line.contains("\"location\"")) {
                tmpLocation = line;
            }
        }

        for (int i = 0; i < 10; i++) {
            courseList.add(new Course("Course" + Integer.toString(i), "C" + Integer.toString(i), "Prof." + Integer.toString(i), Integer.toString(i), "Lothstraße 64.", "This is a course"));
        }


        return courseList;
    }
}
