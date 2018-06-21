package felix.peither.de.cie_for_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;
import android.widget.GridLayout;

public class SecondActivity extends AppCompatActivity {

    Toolbar second_activity_toolbar;

    GridLayout mainGrid;
    CardView card_courses;
    CardView card_favorites;
    CardView card_locations;
    CardView card_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        card_courses = (CardView) findViewById(R.id.cardCourses);
        card_favorites = (CardView) findViewById(R.id.cardFavorites);
        card_locations = (CardView) findViewById(R.id.cardLocations);
        card_schedule = (CardView) findViewById(R.id.cardSchedule);

        card_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courses_intent = new Intent(SecondActivity.this, CoursesActivity.class);
                startActivity(courses_intent);
            }
        });

        card_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favorites_intent = new Intent(SecondActivity.this, FavoritesActivity.class);
                startActivity(favorites_intent);
            }
        });

        card_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations_intent = new Intent(SecondActivity.this, LocationsActivity.class);
                startActivity(locations_intent);
            }
        });

        card_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations_intent = new Intent(SecondActivity.this, ScheduleActivity.class);
                startActivity(locations_intent);
            }
        });
    }

}
