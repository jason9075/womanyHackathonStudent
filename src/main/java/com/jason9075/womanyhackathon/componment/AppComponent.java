package com.jason9075.womanyhackathon.componment;

import com.jason9075.womanyhackathon.MainActivity;
import com.jason9075.womanyhackathon.MyApp;
import com.jason9075.womanyhackathon.module.MyLocationManagerModule;
import com.jason9075.womanyhackathon.module.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jason9075 on 2016/12/3.
 */

@Singleton
@Component(modules = {MyLocationManagerModule.class, SharedPrefModule.class})
public interface AppComponent {

    /* Application */

    void inject(MyApp myApp);


    /* Activity */

    void inject(MainActivity mainActivity);

}
