package felix.peither.de.cie_for_android;

public class Course {

    private final String name;
    private final String professor;
    private final int course_ID;

    Course(String name, String prof, int ID) {
        this.name = name;
        this.professor = prof;
        this.course_ID = ID;
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
