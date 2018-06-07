package com.superto.tnote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.superto.tnote.model.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87724 on 2018/1/17.
 */

public class BankDao {
    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public BankDao(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getReadableDatabase();
    }

    //数据添加
    public long insertData(Bank cb){
        ContentValues cv = new ContentValues();
        //存储数据类型
        cv.put("cardNumber",cb.getCardNumber());
        cv.put("cardName",cb.getCardName());
        cv.put("cardHolder",cb.getCardHolder());
        return db.insert("tb_bank",null,cv);
    }

    //数据修改
    public int updataData(Bank cb){
        ContentValues cv =new ContentValues();
        cv.put("cardNumber",cb.getCardNumber());
        cv.put("cardName",cb.getCardName());
        cv.put("cardHolder",cb.getCardHolder());
        String where = "_id="+cb.getId();
        return db.update("tb_bank",cv,where,null);
        //从具体那个地方删除
    }

    //数据删除
    public int deleteData(int id){
        String where="_id="+id;
        return db.delete("tb_bank",where,null);
    }

    public List<Bank> getDataInfoList(){
        List<Bank> ins=new ArrayList<>();
        String sql="select * from tb_bank";
        Cursor c=db.rawQuery(sql,null);
        while(c.moveToNext()){
            int id=c.getInt(c.getColumnIndex("_id"));
            String cardNumber=c.getString(c.getColumnIndex("cardNumber"));
            String cardName=c.getString(c.getColumnIndex("cardName"));
            String cardHolder=c.getString(c.getColumnIndex("cardHolder"));
            Bank in=new Bank(id,cardNumber,cardName,cardHolder);
            ins.add(in);//对象添加到集合
        }
        return ins;
    }
    //根据id查找到数据
    public Bank getDataInfoById(int id){
        String sql="select * from tb_bank where _id="+id;
        Cursor c=db.rawQuery(sql,null);
        if(c.moveToNext()){
            //从结果集中读出这一行数据的值
            String cardNumber=c.getString(c.getColumnIndex("cardNumber"));
            String cardName=c.getString(c.getColumnIndex("cardName"));
            String cardHolder=c.getString(c.getColumnIndex("cardHolder"));
            //将这些值放入对象中
            Bank cb=new Bank(cardNumber,cardName,cardHolder);
            return cb;
        }
        return null;

    }
}
