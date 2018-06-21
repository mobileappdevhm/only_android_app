package felix.peither.de.cie_for_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class DepartmentActivity extends AppCompatActivity {

    GridLayout mainGrid;
    CardView department_4;
    CardView department_6;
    CardView department_7;
    CardView department_9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        department_4 = (CardView) findViewById(R.id.department4);
        department_6 = (CardView) findViewById(R.id.department6);
        department_7 = (CardView) findViewById(R.id.department7);
        department_9 = (CardView) findViewById(R.id.department9);

        department_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courses_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(courses_intent);
            }
        });

        department_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favorites_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(favorites_intent);
            }
        });

        department_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(locations_intent);
            }
        });

        department_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locations_intent = new Intent(DepartmentActivity.this, CoursesActivity.class);
                startActivity(locations_intent);
            }
        });
    }
}
