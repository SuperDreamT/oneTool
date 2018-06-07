package com.superto.tnote.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 87724 on 2018/1/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TB_NOTE  ="create table if not exists tb_note("+
            "_id integer primary key autoincrement," +
            "title varchar, " +
            "content text, " +
            "time varchar not null)" ;

    private static final String TB_URL  ="create table if not exists tb_url("+
            "_id integer primary key autoincrement," +
            "name varchar, " +
            "url text, " +
            "mark varchar)" ;

    private static final String TB_BIRTHDAY ="create table if not exists tb_birthday("+
            "_id integer primary key autoincrement," +
            "name varchar not null, " +//姓名
            "sex varchar not null, " +//性别
            "date varchar not null, " +//时间
            "countdown varchar, "+//剩余时间
            "phone varchar, " +//手机号
            "mark varchar)";//备注

    private static final String TB_BANK ="create table if not exists tb_bank("+
            "_id integer primary key autoincrement," +
            "cardNumber varchar, " +//卡号
            "cardName text, " +//卡方
            "cardHolder varchar)" ;//持卡人

    private static final  String DB_NAME = "datainfo.db";
    private static final int DB_VERSION=1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行创建三个表
        db.beginTransaction();//启用事务
        try{
            db.execSQL(TB_NOTE);
            db.execSQL(TB_BANK);
            db.execSQL(TB_BIRTHDAY);
            db.execSQL(TB_URL);
            db.setTransactionSuccessful();//提交事务
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            db.endTransaction();//结束事务
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
