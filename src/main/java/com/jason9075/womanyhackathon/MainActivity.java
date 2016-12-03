package com.jason9075.womanyhackathon;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
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
import rx.subscriptions.CompositeSubscription;

import static com.jason9075.womanyhackathon.utils.Constants.STUDENT_LOCATION_TABLE;

public class MainActivity extends AppCompatActivity {

    @Inject
    MyLocationManager locationManager;

    @Inject
    SharedPreferencesManager pref;

    private DatabaseReference mFirebaseDatabaseReference;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private EditText userNameEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.getComponents().inject(this);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        userNameEdittext = (EditText) findViewById(R.id.user_name_edittext);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setUserName(userNameEdittext.getText().toString());
            }
        });


        Observable.just(1)
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Object, Observable<GoogleMapLocationResult>>() {
                    @Override
                    public Observable<GoogleMapLocationResult> call(Object o) {
                        return RetrofitManager.INSTANCE.requestAddress(MyLocationManager.tryGetLastLocation(), true);
                    }
                })
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.repeat();
                    }
                })
                .subscribe(new Action1<GoogleMapLocationResult>() {
                    @Override
                    public void call(GoogleMapLocationResult locationResult) {
                        Location currentLocation = MyLocationManager.tryGetLastLocation();
                        if (currentLocation != null && !pref.getUserName().equals("")) {
                            System.out.println(">>>送出一筆資料");
                            StudentLocationData studentLocationData = new StudentLocationData(UUID.randomUUID().toString(), pref.getUserName());
                            studentLocationData.setLatitude(currentLocation.getLatitude());
                            studentLocationData.setLongitude(currentLocation.getLongitude());
                            studentLocationData.setAddress(RetrofitManager.INSTANCE.getLastAddress());
                            mFirebaseDatabaseReference.child(STUDENT_LOCATION_TABLE).child(studentLocationData.getId()).setValue(studentLocationData);
                        }
                    }
                });


    }


}
