package velez2210.j.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.os.Bundle;
import android.widget.GridLayout;

public class SecondActivity extends AppCompatActivity {

    GridLayout mainGrid;
    CardView card_courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        card_courses = (CardView) findViewById(R.id.cardCourses);

        card_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });

    }

}
