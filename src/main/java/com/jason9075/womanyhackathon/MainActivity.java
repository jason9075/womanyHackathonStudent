package com.jason9075.womanyhackathon;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jason9075.womanyhackathon.manager.MyLocationManager;
import com.jason9075.womanyhackathon.manager.RetrofitManager;
import com.jason9075.womanyhackathon.manager.SharedPreferencesManager;
import com.jason9075.womanyhackathon.model.GoogleMapLocationResult;
import com.jason9075.womanyhackathon.model.StudentLocationData;
import com.jason9075.womanyhackathon.utils.DateFormatCached;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

    private TextView timeTextView;
    private TextView userTextView;
    private ImageButton internetStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.getComponents().inject(this);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        timeTextView = (TextView) findViewById(R.id.time_text_view);
        userTextView = (TextView) findViewById(R.id.user_name_textview);
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        internetStatusButton = (ImageButton) findViewById(R.id.internet_status_button);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        compositeSubscription.add(Observable.just(1)
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
                .subscribe(new Subscriber<GoogleMapLocationResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        internetStatusButton.setSelected(true);
                        Toast.makeText(MainActivity.this, "網路連線錯誤", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GoogleMapLocationResult locationResult) {
                        Location currentLocation = MyLocationManager.tryGetLastLocation();
                        if (currentLocation != null && !pref.getUserName().equals("")) {
                            System.out.println(">>>送出一筆資料");
                            StudentLocationData studentLocationData = new StudentLocationData(UUID.randomUUID().toString(), pref.getUserName());
                            studentLocationData.setLatitude(currentLocation.getLatitude());
                            studentLocationData.setLongitude(currentLocation.getLongitude());
                            studentLocationData.setAddress(RetrofitManager.INSTANCE.getLastAddress());
                            SimpleDateFormat formatter = DateFormatCached.INSTANCE.getFormat("yyyy/MM/dd HH:mm:ss");
                            studentLocationData.setDate(formatter.format(new Date()));
                            mFirebaseDatabaseReference.child(STUDENT_LOCATION_TABLE).push().setValue(studentLocationData);
                        }

                    }
                }));


    }

    @Override
    protected void onResume() {
        super.onResume();

        timeTextView.setText(pref.getUpdateMinus() + "");
        userTextView.setText(pref.getUserName());
    }

    @Override
    protected void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
