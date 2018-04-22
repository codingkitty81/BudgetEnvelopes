package com.example.kitty.budgetenvelopes.model;


import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Transaction extends RealmObject {

    @PrimaryKey
    private int trans_id;

    private String payee;
    private double amount;
    private String type;
    private Date date;
    private boolean transfer;

    @Required
    private String envelope;

    public Transaction() {}

    public Transaction(String payee, double amount, Calendar date) {
        this.payee = payee;
        this.amount = amount;
        this.date = date.getTime();
    }

    public Transaction(int trans_id, String payee, double amount, String envelope , Calendar date) {
        this.trans_id = trans_id;
        this.payee = payee;
        this.amount = amount;
        this.envelope = envelope;
        this.date = date.getTime();
    }

    public int getTrans_id() {
        return trans_id;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(final String payee) {
        this.payee = payee;
    }

    public String getType() { return type; }

    public void setType(final String type) { this.type = type; }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(final String envelope) {
        this.envelope = envelope;
    }

    public Calendar getDate() {
        Calendar var = Calendar.getInstance();
        var.setTime(this.date);
        return var;
    }

    public void setDate(final Calendar date) {
        this.date = date.getTime();
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }
}
