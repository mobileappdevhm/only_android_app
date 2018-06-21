package felix.peither.de.cie_for_android.CourseData;

public class Correlation {

    private final String organizer;
    private final String curriculum;

    public Correlation(String organizer, String curriculum) {
        this.organizer = organizer;
        this.curriculum = curriculum;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getCurriculum() {
        return curriculum;
    }

}
