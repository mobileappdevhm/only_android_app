package felix.peither.de.cie_for_android.CourseData;

import java.util.Collections;
import java.util.List;

public class Date {

    private final String begin;
    private final String end;
    private final String title;
    private final boolean isCanceled;

    private final List<Room> rooms;
    private final List<Lecturer> lecturers;

    public Date(String begin, String end, String title, boolean isCanceled, List<Room> rooms, List<Lecturer> lecturers) {
        this.begin = begin;
        this.end = end;
        this.title = title;
        this.isCanceled = isCanceled;
        this.rooms = rooms;
        this.lecturers = lecturers;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public List<Lecturer> getLecturers() {
        return Collections.unmodifiableList(lecturers);
    }
}
