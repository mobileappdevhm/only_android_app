package felix.peither.de.cie_for_android.CourseData;

import java.util.Collections;
import java.util.List;

public class Course {

    private final String name;
    private final String shortName;
    private final String course_ID;
    private final String description;

    List<Date> dates;
    List<Correlation> correlations;

    public Course(String name, String shortName, String ID, String description, List<Date> dates, List<Correlation> correlations) {
        this.name = name;
        this.shortName = shortName;
        this.course_ID = ID;
        this.description = description;

        this.dates = dates;
        this.correlations = correlations;
    }

    public String getDescription() { return description; }

    public String getShortName() { return shortName; }

    public String getName() {
        return name;
    }

    public String getCourse_ID() {
        return course_ID;
    }

    public List<Date> getDates() {
        return Collections.unmodifiableList(dates);
    }

    public List<Correlation> getCorrelations() {
        return Collections.unmodifiableList(correlations);
    }
}
