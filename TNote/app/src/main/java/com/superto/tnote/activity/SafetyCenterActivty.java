package com.superto.tnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;

/**
 * Created by 87724 on 2018/1/18.
 */

public class SafetyCenterActivty extends AppCompatActivity {
    private Button btnSet;
    private Button btnUp;
    private TextView tvState;
    private ImageView ivState;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_center);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("DataInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int pass=sharedPreferences.getInt("pass",1);
        if(pass==1){
            ivState.setImageResource(R.mipmap.ic_anquan2);
            btnUp.setVisibility(View.INVISIBLE);
            tvState.setText("不安全");
        }else {
            ivState.setImageResource(R.mipmap.ic_anquan1);
            btnSet.setVisibility(View.GONE);
            tvState.setText("密码保护中");
        }
        setOnclickLinner();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(SafetyCenterActivty.this,MainActivity.class));
            finish();
        }
        return super.onKeyUp(keyCode, event);
    }


    private void setOnclickLinner() {
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatas(1);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatas(2);
            }
        });
    }

    private void initView() {
        btnSet = findViewById(R.id.btn_safetycenter_set);
        btnUp = findViewById(R.id.btn_safetycenter_up);
        tvState = findViewById(R.id.tv_safety);
        ivState = findViewById(R.id.iv_safety);
    }

    private void setDatas(int i) {
        Bundle b = new Bundle();
        //这个key是告诉Activity该显示哪个页面
        b.putInt("num", i);
        Intent intent = new Intent(SafetyCenterActivty.this, CryptoOperationActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
