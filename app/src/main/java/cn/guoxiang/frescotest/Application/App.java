package cn.guoxiang.frescotest.Application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by guoxiang on 2017/9/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Application中全局初始化Fresco
        Fresco.initialize(this);
    }
}
