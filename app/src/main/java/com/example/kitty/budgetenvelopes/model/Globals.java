package com.example.kitty.budgetenvelopes.model;

import io.realm.RealmObject;

/**
 * Created by Kitty on 4/7/2018.
 */

public class Globals extends RealmObject {
    private double global_balance;
    private int global_trans_id;

    public Globals() {
        this.global_balance = 0.0;
        this.global_trans_id = 0;
    }

    public double getGlobal_balance() {
        return global_balance;
    }

    public void setGlobal_balance(double global_balance) {
        this.global_balance = global_balance;
    }

    public int getGlobal_trans_id() {
        return global_trans_id;
    }

    public void setGlobal_trans_id(int global_trans_id) {
        this.global_trans_id = global_trans_id;
    }
}
