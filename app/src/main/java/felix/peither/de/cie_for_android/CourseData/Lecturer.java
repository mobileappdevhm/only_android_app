package felix.peither.de.cie_for_android.CourseData;

public class Lecturer {

    private final String title;
    private final String firstName;
    private final String lastName;

    public Lecturer(String title, String firstName, String lastName) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

