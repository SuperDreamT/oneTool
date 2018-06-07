package com.superto.tnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;

/**
 * 程序进入停留的页面
 */
public class SplashActivity extends AppCompatActivity {
    private static final long DELAY_TIME = 3000L;//页面停留的时间
    private int pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences =getSharedPreferences("DataInfo",MODE_PRIVATE);
        pass=sharedPreferences.getInt("pass",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pass==0){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,PassActivity.class));
                    finish();
                }

            }
        },DELAY_TIME);
    }
}
