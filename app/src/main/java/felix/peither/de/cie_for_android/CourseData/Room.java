package felix.peither.de.cie_for_android.CourseData;

public class Room {

    private final String number;
    private final String building;
    private final String campus;

    public Room(String number, String building, String campus) {
        this.number = number;
        this.building = building;
        this.campus = campus;
    }

    public String getNumber() {
        return number;
    }

    public String getBuilding() {
        return building;
    }

    public String getCampus() {
        return campus;
    }
}
