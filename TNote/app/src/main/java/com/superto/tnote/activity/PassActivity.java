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
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;

/**
 * 登录页面
 *              【要求实现的功能】
 *                     1、获取文本输入框的字符串，将其值与本地储存的对比
 *                     2、判断密码正确则进入主界面
 */
public class PassActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private Button num0;
    private Button delete;
    private EditText editText;
    private TextView tv_PassError;//提示输入的密码错误
    private String et_pass;//文本输入框的密码
    private int nopassnum=0;//用户输入的密码错误次数
    private int pass;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw);

        initViews();//初始化控件

        initEvent();//初始化事件

        initTextCL();//文本框监听

    }




    private void initTextCL() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //s     输入框中改变后的字符串信息
                //start 输入框中改变后的字符串的起始位置
                //count 输入框中改变前的字符串的位置 默认为0
                //after 输入框中改变后的一共输入字符串的数量
                if(count==0){
                    iv1.setImageResource(R.mipmap.so);
                    tv_PassError.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  输入框中改变前的字符串信息
                //stsart 输入框中改变前的字符串的起始位置
                //before 输入框中改变前后的字符串改变数量一般为0
                //count 输入框中改变后的字符串与起始位置的偏移量

                if(start==1){
                    iv2.setImageResource(R.mipmap.so);

                }
                if(start==2){
                    iv3.setImageResource(R.mipmap.so);
                }
                if(start==3){
                    iv4.setImageResource(R.mipmap.so);
                    num0.setClickable(false);
                    num1.setClickable(false);
                    num2.setClickable(false);
                    num3.setClickable(false);
                    num4.setClickable(false);
                    num5.setClickable(false);
                    num6.setClickable(false);
                    num7.setClickable(false);
                    num8.setClickable(false);
                    num9.setClickable(false);
                    et_pass = editText.getText().toString().trim();
                    int a = Integer.parseInt(et_pass);
                    SharedPreferences sharedPreferences =getSharedPreferences("DataInfo",MODE_PRIVATE);
                    pass=sharedPreferences.getInt("pass",0);
                    //TODO 程序主页面的入口
                    if(pass==a){
                        intent.putExtra("num",1);
                        intent=new Intent(PassActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        /**
                         * 安全密码错误
                         */
                        tv_PassError.setVisibility(View.VISIBLE);
                        nopass();
                        nopassnum++;

                        // TODO 后期写跳转到重置密码页面
                        if(nopassnum==4){
                            Toast.makeText(PassActivity.this,"尝试次数过多，请重置密码", Toast.LENGTH_SHORT).show();
                        }else if (nopassnum>4){
                            Toast.makeText(PassActivity.this,"请30s后再次尝试", Toast.LENGTH_SHORT).show();
//                            Thread.sleep(3000);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //edit  输入结束呈现在输入框中的信息
            }
        });
    }

    private void initEvent() {
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    private void initViews() {
        iv1 = findViewById(R.id.PSWActivity_iv1);
        iv2 = findViewById(R.id.PSWActivity_iv2);
        iv3 = findViewById(R.id.PSWActivity_iv3);
        iv4 = findViewById(R.id.PSWActivity_iv4);
        num0 = findViewById(R.id.PSWActivity_bt0);
        num1 = findViewById(R.id.PSWActivity_bt1);
        num2 = findViewById(R.id.PSWActivity_bt2);
        num3 = findViewById(R.id.PSWActivity_bt3);
        num4 = findViewById(R.id.PSWActivity_bt4);
        num5 = findViewById(R.id.PSWActivity_bt5);
        num6 = findViewById(R.id.PSWActivity_bt6);
        num7 = findViewById(R.id.PSWActivity_bt7);
        num8 = findViewById(R.id.PSWActivity_bt8);
        num9 = findViewById(R.id.PSWActivity_bt9);
        delete = findViewById(R.id.PSWActivity_btDelete);
        editText = findViewById(R.id.PSWActivity_et1);
        tv_PassError = findViewById(R.id.tv_Error);

    }

    /**
     * 按钮的点击事件
     *
     * @param view 选中的按钮
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.PSWActivity_bt0:
                editText.append("0");
                break;
            case R.id.PSWActivity_bt1:
                editText.append("1");
                break;
            case R.id.PSWActivity_bt2:
                editText.append("2");
                break;
            case R.id.PSWActivity_bt3:
                editText.append("3");
                break;
            case R.id.PSWActivity_bt4:
                editText.append("4");
                break;
            case R.id.PSWActivity_bt5:
                editText.append("5");
                break;
            case R.id.PSWActivity_bt6:
                editText.append("6");
                break;
            case R.id.PSWActivity_bt7:
                editText.append("7");
                break;
            case R.id.PSWActivity_bt8:
                editText.append("8");
                break;
            case R.id.PSWActivity_bt9:
                editText.append("9");
                break;
            case R.id.PSWActivity_btDelete:
                nopass();
                break;

        }
    }

    //登陆界面数据重置
    private void nopass() {

        editText.setText("");
        iv1.setImageResource(R.mipmap.sn);
        iv2.setImageResource(R.mipmap.sn);
        iv3.setImageResource(R.mipmap.sn);
        iv4.setImageResource(R.mipmap.sn);
        num0.setClickable(true);
        num1.setClickable(true);
        num2.setClickable(true);
        num3.setClickable(true);
        num4.setClickable(true);
        num5.setClickable(true);
        num6.setClickable(true);
        num7.setClickable(true);
        num8.setClickable(true);
        num9.setClickable(true);

    }

}
