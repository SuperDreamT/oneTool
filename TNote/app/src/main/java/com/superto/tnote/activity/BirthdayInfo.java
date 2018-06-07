package com.superto.tnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;
import com.superto.tnote.adapter.BirthdayAdapter;
import com.superto.tnote.dao.BirthdayDao;
import com.superto.tnote.model.Bank;
import com.superto.tnote.model.Birthday;

import java.util.List;

public class BirthdayInfo extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView ivBack;
    private ListView listview;

    private List<Birthday> cbs;
    private BirthdayDao dao;

    private int this_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_content);
        initViews();
        dao = new BirthdayDao(this);
        //首先返回所有的数据
        cbs = dao.getDataInfoList();
        setAdapters();
        setClickLinner();
    }

    private void setAdapters() {
        BirthdayAdapter adapter = new BirthdayAdapter(this, cbs);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(BirthdayInfo.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setClickLinner() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirthdayInfo.this, MainActivity.class));
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Birthday cb = cbs.get(position);
                this_id = position;
                int cbId = cb.getId();
                Bundle b = new Bundle();
                //这个key是告诉Activity该显示哪个页面
                b.putString("key", "up");
                b.putInt("id", cbId);
                Intent i = new Intent(BirthdayInfo.this, BirthdayHomeActivity.class);
                i.putExtras(b);
                startActivity(i);

            }
        });
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_listview_title);
        tvTitle.setText("生日管家");
        ivBack = findViewById(R.id.iv_listview_back);
        listview = findViewById(R.id.listview);
    }
}
