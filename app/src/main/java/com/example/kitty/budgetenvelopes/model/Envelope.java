package com.example.kitty.budgetenvelopes.model;

import io.realm.RealmObject;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Envelope extends RealmObject {
    private String name;
    private double balance;
    private boolean round_up_flag;
    private boolean move_balance_flag;
    private Envelope move_balance_destination;
    //private Calendar move_balance_date;

    public Envelope() {}

    public Envelope(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getEnvelopeName() {
        return name;
    }

    public void setEnvelopeName(String name) {
        this.name = name;
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
