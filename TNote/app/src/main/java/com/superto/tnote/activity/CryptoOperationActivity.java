package com.superto.tnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;

/**
 * Created by 87724 on 2018/1/18.
 */

public class CryptoOperationActivity extends AppCompatActivity {
    private EditText etSetPass;
    private EditText etSetAgainPass;
    private EditText etSetCard;
    private EditText etUpOldPass;
    private EditText etUpNewPass;
    private Button btnSetOK;
    private Button btnSetCanner;
    private Button btnUpAmend;
    private Button btnUpDelete;

    private int num;
    private int pass;
    private int againPass;
    private String passCard;
    private int oldPass;
    private int newPass;
    Intent intent = new Intent();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDatas();
        if (num==1){
            setContentView(R.layout.opensafecenter);
        }else {
            setContentView(R.layout.setsafecenter);
        }

        initview();
        setOnClick();
    }

    private void setOnClick() {
        if(num==1){
            //保存密码
            btnSetOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pass = Integer.parseInt(etSetPass.getText().toString().trim());
                    againPass = Integer.parseInt(etSetAgainPass.getText().toString().trim());
                    passCard = etSetCard.getText().toString().trim();
                    if(etSetPass.length()==4&&pass==againPass){
                        if(passCard.length()==4){
                            SharedPreferences sharedPreferences = getSharedPreferences("DataInfo",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("pass",againPass);
                            editor.putString("card",passCard);
                            editor.commit();
                            startActivity(new Intent(CryptoOperationActivity.this, SafetyCenterActivty.class));
                            shows("密码设置成功");
                            finish();
                        }else {
                            shows("密保不能为空");
                        }

                    }else {
                        shows("两次密码不一致");
                    }


                }
            });

            btnSetCanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CryptoOperationActivity.this,SafetyCenterActivty.class));
                    finish();
                }
            });

        }else {
            btnUpDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldPass = Integer.parseInt(etUpOldPass.getText().toString().trim());
                    SharedPreferences sharedPreferences = getSharedPreferences("DataInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int pass=sharedPreferences.getInt("pass",1);
                    if(oldPass==pass){
                        editor.remove("pass");
                        editor.clear();
                        editor.commit();
                        int a = sharedPreferences.getInt("pass",0);
                        if(a==0){
                            shows("删除密码成功");
                            startActivity(new Intent(CryptoOperationActivity.this,SafetyCenterActivty.class));
                            finish();
                        }else {
                            shows("密码删除失败");
                        }
                    }else {
                        shows("原密码错误");
                    }
                }
            });
            //修改密码
            btnUpAmend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("DataInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int pass=sharedPreferences.getInt("pass",1);
                    oldPass = Integer.parseInt(etUpOldPass.getText().toString().trim());
                    newPass= Integer.parseInt(etUpNewPass.getText().toString().trim());
                    if(etUpOldPass.length()==4&&etUpNewPass.length()==4){
                        if(pass==oldPass){
                            editor.putInt("pass",newPass);
                            editor.commit();
                            shows("密码修改成功，下次进入程序生效");
                            startActivity(new Intent(CryptoOperationActivity.this,SafetyCenterActivty.class));
                            finish();
                        }else {
                            shows("原密码错误");
                        }

                    }else {
                        shows("密码要求4位");
                    }
                }
            });
        }
    }

    private void shows(String content) {
        Toast.makeText(CryptoOperationActivity.this,content,Toast.LENGTH_SHORT).show();
    }

    private void initview() {
        etSetPass = findViewById(R.id.et_setPass);
        etSetAgainPass = findViewById(R.id.et_againPass);
        etSetCard = findViewById(R.id.et_passCard);
        etUpNewPass = findViewById(R.id.et_up_newPass);
        etUpOldPass = findViewById(R.id.et_up_oldPass);
        btnSetCanner = findViewById(R.id.btn_setP_cancel);
        btnSetOK = findViewById(R.id.btn_setP_ok);
        btnUpAmend = findViewById(R.id.btn_up_amend);
        btnUpDelete = findViewById(R.id.btn_up_delete);


    }

    private void setDatas() {
        intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(getIntent()!= null) {
            num = bundle.getInt("num");
        }
    }
}
