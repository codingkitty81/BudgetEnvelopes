package com.example.kitty.budgetenvelopes.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Kitty on 2/16/2018.
 */

@SuppressWarnings("serial")
public class Envelope extends RealmObject {
    @PrimaryKey
    @Required
    private String key_name;

    private String name;
    private double balance;
    private boolean round_up_flag = false;
    private boolean move_balance_flag = false;
    private String move_balance_destination;

    //adapted from https://stackoverflow.com/questions/48205164/how-to-get-data-from-realm-database-using-date-object/48205516#48205516
    private Date move_balance_date;

    public Envelope() {}

    public Envelope(String name, double balance) {
        this.key_name = name;
        this.name = name;
        this.balance = balance;
    }

    public String getEnvelopeName() {
        return name;
    }

    public void setEnvelopeName(String name) { this.name = name; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isRound_up_flag() {
        return round_up_flag;
    }

    public void setRound_up_flag(boolean round_up_flag) {
        this.round_up_flag = round_up_flag;
    }

    public boolean isMove_balance_flag() {
        return move_balance_flag;
    }

    public void setMove_balance_flag(boolean move_balance_flag) {
        this.move_balance_flag = move_balance_flag;
    }

    public String getMoveBalanceDestination() { return this.move_balance_destination; }

    public void setMoveBalanceDestination(String move_balance_destination) { this.move_balance_destination = move_balance_destination; }

    public Date getMoveBalanceDate() { return this.move_balance_date; }

    public void setMoveBalanceDate(Date date) {
        date.setMonth(date.getMonth() + 1);
        this.move_balance_date = date;
    }
}
