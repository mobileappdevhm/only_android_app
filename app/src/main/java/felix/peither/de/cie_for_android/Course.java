package felix.peither.de.cie_for_android;

public class Course {

    private final String name;
    private final String professor;

    Course(String name, String prof) {
        this.name = name;
        this.professor = prof;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }
}
