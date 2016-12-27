package com.lin.diebaoguan;

import android.app.Activity;
import android.widget.Toast;

/**
 * It's Created by NewLand-JianFeng on 2016/12/27.
 */

public class BaseActivity extends Activity {

    //本类名  方便测试打印
    protected final String classNameString=this.getClass().getName();

    protected void showToast(String message){
        Toast.makeText(BaseActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    protected void showLongTimeToast(String message){
        Toast.makeText(BaseActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
