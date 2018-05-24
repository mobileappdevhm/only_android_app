package felix.peither.de.cie_for_android;

public class Course {

    private final String name;
    private final String professor;
    private final int course_ID;
    private final String location;

    Course(String name, String prof, int ID, String location) {
        this.name = name;
        this.professor = prof;
        this.course_ID = ID;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public int getCourse_ID() {
        return course_ID;
    }
}
