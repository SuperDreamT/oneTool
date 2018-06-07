package com.superto.tnote.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.R;
import com.superto.tnote.dao.DataBaseHelper;
import com.superto.tnote.model.Birthday;

import org.w3c.dom.Text;

public class BirthdayHomeActivity extends AppCompatActivity {

    private ImageView ivPeople;
    private ImageView ivsex;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvDate;
    private TextView tvXingZuo;
    private EditText etMark;
    private Button btnPhone;
    private Button btnSMS;
    private ImageView ivBack;
    private TextView tvBianji;

    private DataBaseHelper helper;

    private int id;
    private String key;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_home);
        initView();
        setDatas();
        setfills();
        setClick();
    }

    private void setfills() {
        if ("up".equals(key)) {
            helper = new DataBaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("tb_birthday", new String[]{"_id", "name",
                            "sex", "date", "phone", "mark"},
                    "_id='" + id + "'", null, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(1);
            String sex = cursor.getString(2);
            String date = cursor.getString(3);
            mPhone = cursor.getString(4);
            cursor.close();
            if ("男".equals(sex)) {
                ivsex.setImageResource(R.mipmap.ic_user_male);
                ivPeople.setImageResource(R.mipmap.ic_man);
            } else {
                ivsex.setImageResource(R.mipmap.ic_user_female);
                ivPeople.setImageResource(R.mipmap.ic_woman);
            }
            String resilt = date.substring(5,7);
            if("01".equals(resilt)){
                tvXingZuo.setText("水瓶座");
            }else if("02".equals(resilt)){
                tvXingZuo.setText("双鱼座");
            }else if("03".equals(resilt)){
                tvXingZuo.setText("白羊座");
            }else if("04".equals(resilt)){
                tvXingZuo.setText("金牛座");
            }else if("05".equals(resilt)){
                tvXingZuo.setText("双子座");
            }else if("06".equals(resilt)){
                tvXingZuo.setText("巨蟹座");
            }else if("07".equals(resilt)){
                tvXingZuo.setText("狮子座");
            }else if("08".equals(resilt)){
                tvXingZuo.setText("处女座");
            }else if("09".equals(resilt)){
                tvXingZuo.setText("天平座");
            }else if("10".equals(resilt)){
                tvXingZuo.setText("天蝎座");
            }else if("11".equals(resilt)){
                tvXingZuo.setText("射手座");
            }else if("12".equals(resilt)){
                tvXingZuo.setText("魔蝎座");
            }
//
            tvName.setText(name);
            tvDate.setText(date);
        }
    }

    private void setClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirthdayHomeActivity.this, BirthdayInfo.class));
                finish();
            }
        });
        tvBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                //这个key是告诉Activity该显示哪个页面
                b.putString("key", key);
                b.putInt("id", id);
                Intent i = new Intent(BirthdayHomeActivity.this, AddBirthdayActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mPhone));
                startActivity(intent);
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayHomeActivity.this);
                builder.setIcon(R.mipmap.ic_sms);
                builder.setTitle("发送短信");
                builder.setCancelable(false);
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(BirthdayHomeActivity.this).inflate(R.layout.item_sendsms, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                final EditText StrConten = view.findViewById(R.id.sendSMS_content);
                StrConten.setText("生日快乐！");
                final String msg = StrConten.getText().toString().trim();
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(msg.length()>0) {
                            String msg = StrConten.getText().toString().trim();
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(mPhone, null, msg, null, null);
                            Toast.makeText(BirthdayHomeActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }else {
                            Toast.makeText(BirthdayHomeActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setView(view);
                builder.create();
                builder.show();
            }
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_upbirthday_back);
        tvBianji = findViewById(R.id.tv_birthdayHome_redact);
        ivPeople = findViewById(R.id.iv_birthdayHome_people);
        ivsex = findViewById(R.id.iv_birthdayHome_sex);
        tvName =findViewById(R.id.tv_birthdayHome_name);
        tvDate = findViewById(R.id.tv_birthdayHome_time);
        tvXingZuo = findViewById(R.id.tv_birthdayHome_xingzuo);
        btnPhone = findViewById(R.id.btn_birthdayHome_phone);
        btnSMS = findViewById(R.id.btn_birthdayHome_sms);

    }

    private void setDatas() {
        //从intet中获得传入的值——Id和key
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (getIntent() != null) {
            id = bundle.getInt("id");
            key = bundle.getString("key");
        }
    }

}
