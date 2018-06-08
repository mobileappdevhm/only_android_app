package felix.peither.de.cie_for_android;

public class Course {

    private final String name;
    private final String shortName;
    private final String professor;
    private final String course_ID;
    private final String location;
    private final String description;

    Course(String name, String shortName, String prof, String ID, String location, String description) {
        this.name = name;
        this.shortName = shortName;
        this.professor = prof;
        this.course_ID = ID;
        this.location = location;
        this.description = description;
    }

    public String getDescription() { return description; }

    public String getShortName() { return shortName; }

    public String getLocation() { return location; }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public String getCourse_ID() {
        return course_ID;
    }
}
