package com.superto.tnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;

public class ChanngePassActivity extends AppCompatActivity {
    private EditText etOldPws;
    private EditText etNewPass;
    private ImageView iv1;
    private ImageView iv2;
    private Button btnOk;
    private Button btnCanner;
    private String pass;
    private String oldPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        initViews();
        setClicks();
        setTextListeners();
    }

    private void setTextListeners() {
        etOldPws.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences =getSharedPreferences("DataInfo",MODE_PRIVATE);
                pass=sharedPreferences.getString("pass",null);
                if(pass.equals(etOldPws.getText().toString())){
                    iv1.setImageResource(R.mipmap.pass);
                    iv1.setVisibility(View.VISIBLE);
                }else {
                    iv1.setImageResource(R.mipmap.error);
                    iv1.setVisibility(View.VISIBLE);
                    btnCanner.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<4){
                    btnOk.setEnabled(false);
                    iv1.setVisibility(View.INVISIBLE);
                }
                if(pass.equals(etOldPws.getText().toString())&&etNewPass.length()==4){
                    btnOk.setEnabled(true);
                }
            }
        });
        etNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etNewPass.length()==4){
                    iv2.setImageResource(R.mipmap.pass);
                    iv2.setVisibility(View.VISIBLE);
                    if(etOldPws.getText().toString().equals(pass)){
                        btnOk.setEnabled(true);
                    }
                }else {
                    iv2.setImageResource(R.mipmap.error);
                    iv2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<4){
                    btnOk.setEnabled(false);
                    btnCanner.setEnabled(true);
                    iv2.setVisibility(View.INVISIBLE);
                }
                if(s.length()==0){
                    btnCanner.setEnabled(false);
                }
            }
        });
    }


    private void setClicks() {
        btnCanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNewPass.setText("");
                etOldPws.setText("");
                btnCanner.setEnabled(false);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("DataInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pass", String.valueOf(etNewPass));
                editor.commit();
                Toast.makeText(ChanngePassActivity.this,"安全模式已开启", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChanngePassActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    private void initViews() {
        etOldPws = findViewById(R.id.et_OldPass);
        etNewPass = findViewById(R.id.et_NewPass);
        iv1 = findViewById(R.id.iv_oldPass);
        iv2 = findViewById(R.id.iv_newPass);
        btnOk = findViewById(R.id.btn_change_ok);
        btnCanner = findViewById(R.id.btn_change_canner);
    }
}
