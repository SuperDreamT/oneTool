package com.superto.tnote.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.dao.DataBaseHelper;
import com.superto.tnote.R;
import com.superto.tnote.dao.NoteDao;
import com.superto.tnote.model.Note;

import java.util.Calendar;

public class UpNoteActivity extends AppCompatActivity {
    private TextView tvShows;
    private ImageView ivBack;
    private ImageView ivOk;
    private EditText etTitle;
    private EditText etConten;
    private Button btnDelect;
    private String key;
    int id = 0;
    private String date;
    private DataBaseHelper helper;
    public int id_this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        setTime();
        setDatas();
        fillDatas();
        setOnClicks();
    }

    private void setDatas() {
        //从intet中获得传入的值——Id和key
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(getIntent()!= null) {
            id = bundle.getInt("id");
            key = bundle.getString("key");
        }
    }

    private void fillDatas() {
//        CostBean cb = dao.getDataInfoById(id);
//        //根据对象将值填充到输入框
//        etTitle.setText(cb.getTitle());
//        etConten.setText(cb.getContent());

        helper = new DataBaseHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query("tb_note", new String[] { "_id", "title",
                        "content", "time" }, "_id='" + id + "'", null, null,
                null, null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String content = cursor.getString(2);
        cursor.close();

        etTitle.setText(title);
        etConten.setText(content);
    }

    private void setOnClicks() {
        final String title = etTitle.getText().toString().trim();
        final String content = etConten.getText().toString().trim();

        btnDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 调取到数据库文件，将数据删除
                NoteDao dao = new NoteDao(UpNoteActivity.this);
                int num = dao.deleteData(id);
                if (num>0){
                    shows("删除成功");
                    startActivity(new Intent(UpNoteActivity.this,NoteInfo.class));
                    finish();
                }else {
                    shows("删除失败");
                }

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpNoteActivity.this,NoteInfo.class));
                finish();
            }
        });
        //保存数据
        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updata(title,content);
            }
        });
    }

    private void updata(String title, String content) {
        Note cb = new Note(id,title,content,date);
        NoteDao dao = new NoteDao(UpNoteActivity.this);
        int num = dao.updataData(cb);
        if(num>0){
            shows("修改成功");
            startActivity(new Intent(UpNoteActivity.this,NoteInfo.class));
            finish();
        }else {
            shows("修改失败");
        }
    }


    private void shows(String content) {
        Toast.makeText(UpNoteActivity.this,content, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_addData_back);
        ivOk = findViewById(R.id.iv_addData_Yes);
        etTitle = findViewById(R.id.et_addData_title);
        etConten = findViewById(R.id.et_addData_content);
        btnDelect = findViewById(R.id.btn_addData_delete);
        btnDelect.setVisibility(View.VISIBLE);
        tvShows = findViewById(R.id.dataShow);
        tvShows.setText("修改备忘录");
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
