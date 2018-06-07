package com.superto.tnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.superto.tnote.MainActivity;
import com.superto.tnote.R;
import com.superto.tnote.adapter.BankAdapter;
import com.superto.tnote.dao.BankDao;
import com.superto.tnote.model.Bank;

import java.util.List;

public class BankInfo extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView ivBack;
    private ListView listView;
    private List<Bank> cbs;
    private BankDao dao;

    private int this_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_content);
        initViews();
        dao = new BankDao(this);
        //首先返回所有的数据
        cbs = dao.getDataInfoList();
        setAdapters();
        setClickLinner();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(BankInfo.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setAdapters() {
        BankAdapter adapter = new BankAdapter(this, cbs);
        listView.setAdapter(adapter);
    }

    private void setClickLinner() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankInfo.this, MainActivity.class));
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bank cb = cbs.get(position);
                this_id = position;
                int cbId = cb.getId();
                Bundle b = new Bundle();
                b.putInt("id", cbId);
                Intent i = new Intent(BankInfo.this, UpBankActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_listview_title);
        tvTitle.setText("银行卡");
        ivBack = findViewById(R.id.iv_listview_back);
        listView = findViewById(R.id.listview);
    }
}
