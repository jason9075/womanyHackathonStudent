package com.jason9075.womanyhackathon;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.jason9075.womanyhackathon.manager.MyLocationManager;
import com.jason9075.womanyhackathon.manager.RetrofitManager;
import com.jason9075.womanyhackathon.manager.SharedPreferencesManager;
import com.jason9075.womanyhackathon.model.GoogleMapLocationResult;
import com.jason9075.womanyhackathon.model.StudentLocationData;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.jason9075.womanyhackathon.utils.Constants.STUDENT_LOCATION_TABLE;

/**
 * Created by jason9075 on 2016/12/4.
 */

public class SettingActivity extends AppCompatActivity {

    @Inject
    SharedPreferencesManager pref;

    private EditText studentNameEditText;
    private EditText updateMinusEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyApp.getComponents().inject(this);


        ImageButton submitButton = (ImageButton) findViewById(R.id.submit_button);
        studentNameEditText = (EditText) findViewById(R.id.student_name_edit_text);
        updateMinusEditText = (EditText) findViewById(R.id.minus_edit_text);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setUserName(studentNameEditText.getText().toString());
                pref.setUpdateMinus(Integer.parseInt(updateMinusEditText.getText().toString()));
                finish();
            }
        });


    }
}
