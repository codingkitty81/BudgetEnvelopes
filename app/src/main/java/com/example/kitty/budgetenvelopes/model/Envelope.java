package com.example.kitty.budgetenvelopes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Envelope extends RealmObject {
    @PrimaryKey
    @Required
    private String name;

    private double balance;
    private boolean round_up_flag = false;
    private boolean move_balance_flag = false;
    private String move_balance_destination;
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

    public void toggleRoundUp() {
        if(round_up_flag == false) {
            round_up_flag = true;
        } else {
            round_up_flag = false;
        }
    }

    public void toggleMoveBalance() {
        if(move_balance_flag == false) {
            move_balance_flag = true;
        } else {
            move_balance_flag = false;
        }
    }

    public String getMoveBalanceDestination() { return this.move_balance_destination; }

    public void setMoveBalanceDestination(String move_balance_destination) { this.move_balance_destination = move_balance_destination; }

}
