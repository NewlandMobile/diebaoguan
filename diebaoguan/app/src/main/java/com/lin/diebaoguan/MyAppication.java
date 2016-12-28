package com.lin.diebaoguan;

import android.app.Application;

/**
 * It's Created by NewLand-JianFeng on 2016/12/27.
 */

public class MyAppication extends Application {

    private static MyAppication myAppication;

    @Override
    public void onCreate() {
        super.onCreate();
        myAppication = this;
    }

    public static MyAppication getInstance() {
        if (myAppication == null) {
            myAppication = new MyAppication();
        }
        return myAppication;
    }
}
