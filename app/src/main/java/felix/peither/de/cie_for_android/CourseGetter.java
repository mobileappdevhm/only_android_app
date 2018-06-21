package felix.peither.de.cie_for_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import felix.peither.de.cie_for_android.CourseData.Correlation;
import felix.peither.de.cie_for_android.CourseData.Course;
import felix.peither.de.cie_for_android.CourseData.Date;
import felix.peither.de.cie_for_android.CourseData.Lecturer;
import felix.peither.de.cie_for_android.CourseData.Room;

public class CourseGetter implements Runnable {

    private List<Course> courses = new ArrayList<>();
    private List<Thread> allThreads = new ArrayList<>();

    private String getValueOfJSONString(String JSONString) {
        String value = "not a type value pair";
        String[] typeAndValue = JSONString.split(":");
        if (typeAndValue.length > 1)
            value = typeAndValue[1].substring(1,typeAndValue[1].length()-1);
        return value;
    }

    @Override
    public void run() {
        List<String> response = new ArrayList<>();

        try {
            URL url = new URL("https://nine.wi.hm.edu/api/v2/courses/FK%2013/CIE/SoSe%2018");
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Course> courseList = new ArrayList<>();

        String tmpCourseName = "";
        String tmpCourseShortName = "";
        String tmpCourseID = "";
        String tmpCourseDescription = "";

        List<Correlation> tmpCorrelations = new ArrayList<>();

        String tmpCorrOrganizer = "";
        String tmpCorrCurriculum = "";

        List<Date> tmpDates = new ArrayList<>();

        String tmpDateBegin = "";
        String tmpDateEnd = "";
        String tmpDateTitle = "";
        boolean tmpDateCanceled = false;

        List<Lecturer> tmpLecturers = new ArrayList<>();

        String tmpLecTitle = "";
        String tmpLecFirstName = "";
        String tmpLecLastName = "";

        List<Room> tmpRooms = new ArrayList<>();

        String tmpRoomNumber = "";
        String tmpRoomBuilding = "";
        String tmpRoomCampus = "";

        boolean inCorrelations = false;
        boolean inDates = false;
        boolean inRooms = false;
        boolean inLecturers = false;

        for (String line: response) {
            String formatedLine = getValueOfJSONString(line);
//            System.out.println(line);
            if (line.contains("\"id\"")) {
                tmpCourseID = formatedLine; //.substring(6, line.length() - 2);
            } else if (line.contains("\"description\"")) {
                tmpCourseDescription = formatedLine;
            } else if (line.contains("\"name\"")) {
                tmpCourseName = formatedLine;
            } else if (line.contains("\"shortName\"")) {
                tmpCourseShortName = formatedLine;
                courseList.add(new Course(tmpCourseName, tmpCourseShortName, tmpCourseID, tmpCourseDescription, tmpDates, tmpCorrelations));
                // COURSE END ---------------------------------------------------------------------------------
            } else if (line.contains("\"correlations\"")) {
                // CORRELATIONS START -------------------------------------------------------------------------
                inCorrelations = true;
            } else if (line.contains("\"organiser\"")) {
                tmpCorrOrganizer = formatedLine;
            } else if (line.contains("\"curriculum\"")) {
                tmpCorrCurriculum = formatedLine;
            } else if (line.contains("\"actions\"") && inCorrelations && !inRooms && !inDates && !inLecturers) {
                tmpCorrelations.add(new Correlation(tmpCorrOrganizer, tmpCorrCurriculum));
                inCorrelations = false;
                // CORRELATION END ----------------------------------------------------------------------------
            } else if (line.contains("\"rooms\"")) {
                // ROOM START -------------------------------------------------------------------------
                inRooms = true;
            } else if (inDates && inRooms && line.contains("\"number\"")) {
                tmpRoomNumber = formatedLine;
            } else if (inDates && inRooms && line.contains("\"building\"")) {
                tmpRoomBuilding = formatedLine;
            } else if (inDates && inRooms && line.contains("\"campus\"")) {
                tmpRoomCampus = formatedLine;
            } else if (inDates && inRooms && line.contains("\"actions\"") && !inCorrelations && !inLecturers) {
                tmpRooms.add(new Room(tmpRoomNumber, tmpRoomBuilding, tmpRoomCampus));
                inRooms = false;
                // ROOM END ----------------------------------------------------------------------------
            } else if (line.contains("\"correlations\"")) {
                // LECTURERS START -------------------------------------------------------------------------
                inLecturers = true;
            } else if (inDates && inLecturers && line.contains("\"title\"")) {
                tmpLecTitle = formatedLine;
            } else if (inDates && inLecturers && line.contains("\"firstName\"")) {
                tmpLecFirstName = formatedLine;
            } else if (inDates && inLecturers && line.contains("\"lastName\"")) {
                tmpLecLastName = formatedLine;
            } else if (inDates && inLecturers && line.contains("\"actions\"") && !inCorrelations && !inRooms) {
                tmpLecturers.add(new Lecturer(tmpLecTitle, tmpLecFirstName, tmpLecLastName));
                inLecturers = false;
                // LECTURERS END ----------------------------------------------------------------------------
            } else if (line.contains("\"correlations\"")) {
                // DATE START -------------------------------------------------------------------------
                inDates = true;
            } else if (inDates && line.contains("\"begin\"")) {
                tmpDateBegin = formatedLine;
            } else if (inDates && line.contains("\"end\"")) {
                tmpDateEnd = formatedLine;
            } else if (inDates && line.contains("\"title\"")) {
                tmpDateTitle = formatedLine;
            } else if (inDates && line.contains("\"canceled\"")) {
                tmpDateCanceled = formatedLine.contains("true");
            } else if (inDates && line.contains("\"actions\"") && !inCorrelations && !inRooms && !inLecturers) {
                inDates = false;
                tmpDates.add(new Date(tmpDateBegin, tmpDateEnd, tmpDateTitle, tmpDateCanceled, tmpRooms, tmpLecturers));
                tmpLecturers.clear();
                tmpRooms.clear();
                // DATE END ----------------------------------------------------------------------------
            }
        }

        courses = courseList;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }
}
