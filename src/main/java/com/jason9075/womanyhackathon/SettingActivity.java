package com.jason9075.womanyhackathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jason9075.womanyhackathon.manager.SharedPreferencesManager;

import javax.inject.Inject;

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
                if (!studentNameEditText.getText().toString().equals("")) {
                    pref.setUserName(studentNameEditText.getText().toString());
                }
                if (!updateMinusEditText.getText().toString().equals("")) {
                    pref.setUpdateMinus(Integer.parseInt(updateMinusEditText.getText().toString()));
                }
                finish();
            }
        });


    }
}
