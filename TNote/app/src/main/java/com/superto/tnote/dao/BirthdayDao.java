package com.superto.tnote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.superto.tnote.model.Birthday;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87724 on 2018/1/17.
 */

public class BirthdayDao {
    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public BirthdayDao(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getReadableDatabase();
    }

    //数据添加
    public long insertData(Birthday cb){
        ContentValues cv = new ContentValues();
        cv.put("name",cb.getName());
        cv.put("date",cb.getDate());
        cv.put("countdown",cb.getCountdown());
        cv.put("phone",cb.getPhone());
        cv.put("mark",cb.getMark());
        cv.put("sex",cb.getSex());
        return db.insert("tb_birthday",null,cv);
    }

    //数据修改
    public int updataData(Birthday cb){
        ContentValues cv =new ContentValues();
        cv.put("name",cb.getName());
        cv.put("date",cb.getDate());
        cv.put("countdown",cb.getCountdown());
        cv.put("phone",cb.getPhone());
        cv.put("mark",cb.getMark());
        cv.put("sex",cb.getSex());
        String where = "_id="+cb.getId();
        return db.update("tb_birthday",cv,where,null);

    }

    //数据删除
    public int deleteData(int id){
        String where="_id="+id;
        return db.delete("tb_birthday",where,null);
    }

    public List<Birthday> getDataInfoList(){
        List<Birthday> ins=new ArrayList<>();
        String sql="select * from tb_birthday";
        Cursor c=db.rawQuery(sql,null);
        while(c.moveToNext()){
            int id=c.getInt(c.getColumnIndex("_id"));
            String time=c.getString(c.getColumnIndex("date"));
            String name=c.getString(c.getColumnIndex("name"));
            String sex=c.getString(c.getColumnIndex("sex"));
            String phone=c.getString(c.getColumnIndex("phone"));
            String mark=c.getString(c.getColumnIndex("mark"));
            String down = c.getString(c.getColumnIndex("countdown"));
            Birthday in=new Birthday(id,name,sex,time,phone,mark,down);
            ins.add(in);//对象添加到集合
        }
        return ins;
    }
    //根据id查找到数据
    public Birthday getDataInfoById(int id){
        String sql="select * from tb_birthday where _id="+id;
        Cursor c=db.rawQuery(sql,null);
        if(c.moveToNext()){
            //从结果集中读出这一行数据的值
            String name=c.getString(c.getColumnIndex("name"));
            String sex=c.getString(c.getColumnIndex("sex"));
            String date=c.getString(c.getColumnIndex("date"));
            String phone=c.getString(c.getColumnIndex("phone"));
            String mark=c.getString(c.getColumnIndex("mark"));
            String down = c.getString(c.getColumnIndex("countdown"));
            //将这些值放入对象中
            Birthday cb=new Birthday(name,date,down,phone,mark,sex);
            return cb;
        }
        return null;

    }
}
