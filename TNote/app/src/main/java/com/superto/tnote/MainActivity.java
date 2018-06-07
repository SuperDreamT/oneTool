package com.superto.tnote;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.superto.tnote.activity.AddBirthdayActivity;
import com.superto.tnote.activity.AddNoteActivity;
import com.superto.tnote.activity.BankInfo;
import com.superto.tnote.activity.BirthdayInfo;
import com.superto.tnote.activity.ChanngePassActivity;
import com.superto.tnote.activity.NoteInfo;
import com.superto.tnote.activity.SafetyCenterActivty;
import com.superto.tnote.adapter.NavLeftAdapter;
import com.superto.tnote.adapter.ShareDialog;
import com.superto.tnote.dao.BankDao;
import com.superto.tnote.model.Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout = null;
    private ImageButton navLeft;
    private ListView listView;
    private LinearLayout l1;
    private LinearLayout l2;
    private LinearLayout l3;
    private ImageView btnAddData;

    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setAdapter();
        setOnListeners();


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setOnListeners() {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                navLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //侧面菜单栏
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, SafetyCenterActivty.class));
                        break;
                }
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NoteInfo.class));
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BankInfo.class));
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BirthdayInfo.class));
            }
        });
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareDialog(MainActivity.this, R.style.dialog, new ShareDialog.OnItemClickListener() {
                    @Override
                    public void onClick(final Dialog dialog, int position) {
                        dialog.dismiss();
                        switch (position){
                            case 1:
                                startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
                                break;
                            case 2:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                               //显示一个对话
                                builder.setIcon(R.mipmap.bank);
                                builder.setTitle("添加银行卡");
                                builder.setCancelable(true);//灰色部分点击不关闭对话框
                                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_bank, null);
                                //    设置我们自己定义的布局文件作为弹出框的Content

                                final EditText StrNumber = view.findViewById(R.id.et_addBank_number);
                                final EditText StrName = view.findViewById(R.id.et_addBank_name);
                                final EditText strHolder = view.findViewById(R.id.et_addBank_holder);
                                Button ok = view.findViewById(R.id.btn_addBank);
                                Button canner = view.findViewById(R.id.btn_CannerBank);
                                ok.setOnClickListener(new android.view.View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       String number = StrNumber.getText().toString();
                                       String name = StrName.getText().toString();
                                       String holder = strHolder.getText().toString();
                                        if(number.length()>0&&name.length()>0){
                                            Bank cb = new Bank(number,name,holder);
                                            BankDao dao = new BankDao(MainActivity.this);
                                            long num = dao.insertData(cb);
                                            if(num>0){
                                                shows("添加成功");
                                                startActivity(new Intent(MainActivity.this,BankInfo.class));
                                                finish();
                                            }else {
                                                shows("添加失败");
                                            }
                                        }else {
                                            shows("请输入完整信息");
                                        }
                                    }
                                });
                                canner.setOnClickListener(new android.view.View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog. cancel();
                                    }
                                });
                                builder.setView(view);
                                builder.create();
                                builder.show();
                                break;
                            case 3:
                                Bundle b = new Bundle();
                                //这个key是告诉Activity该显示哪个页面
                                b.putString("key", "add");
                                Intent i = new Intent(MainActivity.this, AddBirthdayActivity.class);
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this,"敬请期待...",Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }).show();
            }
        });


    }


    private void setAdapter() {
        // 设置适配器的图片资源
        int[] imageId = new int[] { R.mipmap.note,R.mipmap.tools };

        // 设置标题
        String[] title = new String[] { "私密模式", "设置"};
//        String[] title = new String[] { "私密模式", "设置", "生日管家", "工具"};
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        // 将上述资源转化为list集合
        for (int i = 0; i < title.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageId[i]);
            map.put("title", title[i]);
            listitem.add(map);
        }
        NavLeftAdapter adapter = new NavLeftAdapter(this, listitem);
        listView.setAdapter(adapter);
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navLeft = findViewById(R.id.btn);
        listView = findViewById(R.id.mainListview);
        l1 = findViewById(R.id.ll_note);
        l2 = findViewById(R.id.ll_bank);
        l3 = findViewById(R.id.ll_bb);
        btnAddData = findViewById(R.id.btn_addData);

    }
    public void shows(String content){
        Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();
    }
}
