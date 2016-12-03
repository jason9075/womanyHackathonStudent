package com.jason9075.womanyhackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jason9075.womanyhackathon.manager.MyLocationManager;
import com.jason9075.womanyhackathon.model.StudentLocationData;

import javax.inject.Inject;

import static com.jason9075.womanyhackathon.utils.Constants.STUDENT_LOCATION_TABLE;

public class MainActivity extends AppCompatActivity {

    @Inject
    MyLocationManager locationManager;

    private DatabaseReference mFirebaseDatabaseReference;
    private EditText userNameEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.getComponents().inject(this);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        userNameEdittext = (EditText)  findViewById(R.id.user_name_edittext);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        StudentLocationData studentLocationData = new StudentLocationData("sadasd","John");
        studentLocationData.setLatitude(123.0);
        studentLocationData.setLongitude(23.3);
        studentLocationData.setAddress("地址");
        mFirebaseDatabaseReference.child(STUDENT_LOCATION_TABLE).child(studentLocationData.getId()).setValue(studentLocationData);
        System.out.println(">>>");


    }
}
