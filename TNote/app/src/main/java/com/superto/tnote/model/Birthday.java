package com.superto.tnote.model;

/**
 * Created by 87724 on 2018/1/17.
 */

public class Birthday {
    private int id;
    private String name;
    private String sex;
    private String date;
    private String phone;
    private String mark;
    private String countdown;

    public Birthday() {
    }

    public Birthday(String name, String sex, String date, String phone, String mark, String countdown) {
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.phone = phone;
        this.mark = mark;
        this.countdown = countdown;
    }

    public Birthday(int id, String name, String sex, String date, String phone, String mark, String countdown) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.phone = phone;
        this.mark = mark;
        this.countdown = countdown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }
}
