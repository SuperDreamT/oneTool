package com.superto.tnote.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;
import com.superto.tnote.dao.NoteDao;
import com.superto.tnote.model.Note;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    Intent intent = new Intent();
    private TextView tvShows;
    private ImageView ivBack;
    private ImageView ivOk;
    private EditText etTitle;
    private EditText etConten;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        setTime();
        setOnClick();

    }


    private void setOnClick() {
        //点击返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AddNoteActivity.this,NoteInfo.class);
                startActivity(intent);
                finish();
            }
        });
        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String content = etConten.getText().toString();
                if(title.length()>0||content.length()>0){
                    Note cb = new Note(title,content,date);
                    NoteDao dao = new NoteDao(AddNoteActivity.this);
                    long num = dao.insertData(cb);
                    if(num>0){
                        shows("添加成功");
                        startActivity(new Intent(AddNoteActivity.this,NoteInfo.class));
                        finish();
                    }else {
                        shows("添加失败");
                    }
                }else {
                    startActivity(new Intent(AddNoteActivity.this,NoteInfo.class));
                    finish();
                }

            }


        });
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_addData_back);
        ivOk = findViewById(R.id.iv_addData_Yes);
        etTitle = findViewById(R.id.et_addData_title);
        etConten = findViewById(R.id.et_addData_content);
        tvShows = findViewById(R.id.dataShow);
        tvShows.setText("新建备忘录");

    }

    public void shows(String content){
        Toast.makeText(AddNoteActivity.this,content,Toast.LENGTH_SHORT).show();
    }

    private void setTime() {
        //假设日期现在初始化需要得到当前系统日期
        Calendar c= Calendar.getInstance();//当前日历对象
        final int m = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        final int d = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int mm = c.get(Calendar.MINUTE);
        if(mm<9){
            date=m+"月"+d+"日"+"  "+h+":0"+mm;
        }else{
            date=m+"月"+d+"日"+"  "+h+":"+mm;
        }
    }
}
