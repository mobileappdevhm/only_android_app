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


    /* This code is for our other departments, if we get the courses from the API

        department_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent department4_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(department4_intent);
            }
        });

        department_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent department6_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(department6_intent);
            }
        });

        department_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent department7_intent = new Intent(DepartmentActivity.this, DepartmentActivity.class);
                startActivity(department7_intent);
            }
        });

        */

        department_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent department9_intent = new Intent(DepartmentActivity.this, CoursesActivity.class);
                startActivity(department9_intent);
            }
        });
    }
}
