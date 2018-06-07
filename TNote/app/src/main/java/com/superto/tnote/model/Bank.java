package com.superto.tnote.model;

/**
 * Created by 87724 on 2018/1/17.
 */

public class Bank {
    private int id;
    private String cardNumber;//卡号
    private String cardName;//开卡放
    private String cardHolder;//持卡人

    public Bank() {
    }

    public Bank(String cardNumber, String cardName, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardHolder = cardHolder;
    }

    public Bank(int id, String cardNumber, String cardName, String cardHolder) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardHolder = cardHolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
