package com.superto.tnote.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.R;
import com.superto.tnote.dao.BankDao;
import com.superto.tnote.dao.DataBaseHelper;
import com.superto.tnote.model.Bank;

/**
 * Created by 87724 on 2018/1/20.
 */

public class UpBankActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivDelete;
    private TextView tvTitle;
    private EditText etCardNumber;
    private EditText etCardName;
    private EditText etCardHolder;
    private Button btnOk;
    private Button btnCanner;

    private String key;
    int id = 0;
    private String date;
    private DataBaseHelper helper;
    public int id_this;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_bank);
        initView();
        setDatas();
        fillDatas();
        setClick();
    }

    private void setClick() {
        final String number = etCardNumber.getText().toString().trim();
        final String name = etCardName.getText().toString().trim();
        final String namas = etCardHolder.getText().toString().trim();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpBankActivity.this,BankInfo.class));
                finish();
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        // TODO 调取到数据库文件，将数据删除
                        BankDao dao = new BankDao(UpBankActivity.this);
                        int num = dao.deleteData(id);
                        if (num>0){
                            shows("删除成功");
                            startActivity(new Intent(UpBankActivity.this,BankInfo.class));
                            finish();
                        }else {
                            shows("删除失败");
                        }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bank cb = new Bank(id,number,name,namas);
                BankDao dao = new BankDao(UpBankActivity.this);
                int num = dao.updataData(cb);
                if(num>0){
                    shows("修改成功");
                    startActivity(new Intent(UpBankActivity.this,BankInfo.class));
                    finish();
                }else {
                    shows("修改失败");
                }
            }
        });
        btnCanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpBankActivity.this,BankInfo.class));
            }
        });
    }

    private void shows(String content) {
        Toast.makeText(UpBankActivity.this,content,Toast.LENGTH_SHORT).show();
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
        helper = new DataBaseHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("tb_bank", new String[] { "_id", "cardNumber",
                        "cardName", "cardHolder" }, "_id='" + id + "'", null, null,
                null, null);
        cursor.moveToFirst();
        String cardNumber = cursor.getString(1);
        String cardName = cursor.getString(2);
        String name = cursor.getString(3);
        cursor.close();

        etCardNumber.setText(cardNumber);
        etCardName.setText(cardName);
        etCardHolder.setText(name);
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_upbank_back);
        tvTitle = findViewById(R.id.tv_upbank_title);
        ivDelete = findViewById(R.id.iv_upbank_delete);
        etCardNumber = findViewById(R.id.et_upbank_number);
        etCardName = findViewById(R.id.et_upbank_bankname);
        etCardHolder = findViewById(R.id.et_upbank_name);
        btnOk = findViewById(R.id.btn_upok);
        btnCanner = findViewById(R.id.btn_upcancel);
    }
}
