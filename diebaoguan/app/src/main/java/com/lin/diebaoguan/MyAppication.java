package com.lin.diebaoguan;

import android.app.Application;

/**
 * It's Created by NewLand-JianFeng on 2016/12/27.
 */

public class MyAppication extends Application {

    private static final MyAppication myAppication=new MyAppication();

    @Override
    public void onCreate() {
        super.onCreate();

    }
    public static MyAppication getInstance(){
        return myAppication;
    }
}
