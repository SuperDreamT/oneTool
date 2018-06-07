package com.superto.tnote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.superto.tnote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *备忘录数据操作类
 */

public class NoteDao {
    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public NoteDao(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getReadableDatabase();
    }

    //数据添加
    public long insertData(Note cb){
        ContentValues cv = new ContentValues();
        cv.put("title",cb.getTitle());
        cv.put("content",cb.getContent());
        cv.put("time",cb.getDate());
        return db.insert("tb_note",null,cv);
    }

    //数据修改
    public int updataData(Note cb){
        ContentValues cv =new ContentValues();
        cv.put("title",cb.getTitle());
        cv.put("content",cb.getContent());
        cv.put("time",cb.getDate());
        String where = "_id="+cb.getId();
        return db.update("tb_note",cv,where,null);

    }

    //数据删除
    public int deleteData(int id){
        String where="_id="+id;
        return db.delete("tb_note",where,null);
    }

    public List<Note> getDataInfoList(){
        List<Note> ins=new ArrayList<>();
        String sql="select * from tb_note";
        Cursor c=db.rawQuery(sql,null);
        while(c.moveToNext()){
            int id=c.getInt(c.getColumnIndex("_id"));
            String time=c.getString(c.getColumnIndex("time"));
            String content=c.getString(c.getColumnIndex("content"));
            String title=c.getString(c.getColumnIndex("title"));
            Note in=new Note(id,title,content,time);
            ins.add(in);//对象添加到集合
        }
        return ins;
    }
    //根据id查找到数据
    public Note getDataInfoById(int id){
        String sql="select * from tb_note where _id="+id;
        Cursor c=db.rawQuery(sql,null);
        if(c.moveToNext()){
            //从结果集中读出这一行数据的值
            String time=c.getString(c.getColumnIndex("time"));
            String content=c.getString(c.getColumnIndex("content"));
            String title=c.getString(c.getColumnIndex("title"));
            //将这些值放入对象中
            Note cb=new Note(title,content,time);
            return cb;
        }
        return null;

    }

    }
