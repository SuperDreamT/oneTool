package com.superto.tnote.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.superto.tnote.R;
import com.superto.tnote.dao.BirthdayDao;
import com.superto.tnote.dao.DataBaseHelper;
import com.superto.tnote.model.Birthday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBirthdayActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivDelete;
    private ImageView ivPeople;
    private EditText etName;
    private EditText etPhone;
    private EditText etMark;
    private TextView tvDate;
    private RadioGroup rg;
    private RadioButton rbMan;
    private RadioButton rbWoMan;
    private Button btnOk;
    private Button btnCanner;
    private Button btnAddPhone;

    private String mDate;//出生年日
    private String mSex;
    private String mName;
    private String mPhone;
    private String tDate;//当前时间
    private String endData;//相差时间
    private int id;
    private String key;

    private DataBaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);
        initView();
        setDatas();
        setClick();

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

    private void setClick() {
        if ("add".equals(key)) {
            tvTitle.setText("添加日程");
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddBirthdayActivity.this, BirthdayInfo.class));
                    finish();
                }
            });
            rbMan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivPeople.setImageResource(R.mipmap.ic_man);
                }
            });
            rbWoMan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivPeople.setImageResource(R.mipmap.ic_woman);
                }
            });

            //获取时间
            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickDlg();
                }
            });
            //获取到男女性别
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = AddBirthdayActivity.this.findViewById(rg.getCheckedRadioButtonId());
                    mSex = rb.getText().toString();
                }
            });

            btnCanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddBirthdayActivity.this, BirthdayInfo.class));
                    finish();
                }
            });

            btnAddPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                OpenPhoneBook();
                    startActivityForResult(new Intent(
                            Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                }
            });
            //添加生日
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString();
                    String phone = etPhone.getText().toString();
                    String mark = etPhone.getText().toString();
                    if (name.length() > 0 || mDate != null) {
                        Birthday cb = new Birthday(name, mSex, mDate, phone, mark, endData);
                        BirthdayDao dao = new BirthdayDao(AddBirthdayActivity.this);
                        long num = dao.insertData(cb);
                        if (num > 0) {
                            shows("添加成功");
                            startActivity(new Intent(AddBirthdayActivity.this, BirthdayInfo.class));
                            finish();
                        } else {
                            shows("添加失败");
                        }
                    } else {
                        shows("请填写完整信息");
                    }
                }
            });
        }
        if("up".equals(key)){
            ivDelete.setVisibility(View.VISIBLE);
            tvTitle.setText("修改日程");
            //TODO 这里进入数据更新
            helper = new DataBaseHelper(this);
            final SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("tb_birthday", new String[]{"_id", "name",
                            "sex", "date", "phone", "mark"},
                    "_id='" + id + "'", null, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(1);
            String sex = cursor.getString(2);
            String date = cursor.getString(3);
            String phone = cursor.getString(4);
            String mark = cursor.getString(5);
            cursor.close();

            etName.setText(name);
            etPhone.setText(phone);
            tvDate.setText(date);
            if (mark != null) {
                etMark.setText(mark);
            }
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddBirthdayActivity.this,BirthdayInfo.class));
                    finish();
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BirthdayDao dao = new BirthdayDao(AddBirthdayActivity.this);
                    int num = dao.deleteData(id);
                    if (num>0){
                        shows("删除成功");
                        startActivity(new Intent(AddBirthdayActivity.this,BirthdayInfo.class));
                        finish();
                    }else {
                        shows("删除失败");
                    }
                }
            });

        }
    }

    private void shows(String content) {
        Toast.makeText(AddBirthdayActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        ivPeople = findViewById(R.id.iv_addbirthday_people);
        ivBack = findViewById(R.id.iv_addbirthday_back);
        ivDelete = findViewById(R.id.iv_addbirthday_delete);
        tvTitle = findViewById(R.id.tv_addbirthday_title);
        etName = findViewById(R.id.et_addbirthday_name);
        etPhone = findViewById(R.id.et_addbirthday_phone);
        tvDate = findViewById(R.id.tv_addbirthday_date);
        rg = findViewById(R.id.radioGrop);
        rbMan = findViewById(R.id.radioman);
        rbWoMan = findViewById(R.id.radiowoman);
        btnCanner = findViewById(R.id.btn_addBirthday_canner);
        btnOk = findViewById(R.id.btn_addBirthday_ok);
        etMark = findViewById(R.id.et_addbirthday_mark);
        btnAddPhone = findViewById(R.id.btn_addBirthday_phone);

    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBirthdayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AddBirthdayActivity.this.tvDate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");

                if ((monthOfYear+1)<10){
                    mDate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                }else {
                    mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                tDate = 2019 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                setTime();

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            mName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                mPhone = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                etName.setText(mName);
                etPhone.setText(mPhone);

            }

        }
    }

    private void setTime() {
        Calendar c = Calendar.getInstance();//当前日历对象
        final int y = c.get(Calendar.YEAR);
        final int m = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        final int d = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        String a = y + "-" + m + "-" + d;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2 = null;
        if (mDate != null) {
            try {
                date1 = formatter.parse(a);
                date2 = formatter.parse(tDate);
                Long aLong = (date2.getTime() - date1.getTime()) / (60 * 60 * 1000 * 24);
                endData = aLong.toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }
}
