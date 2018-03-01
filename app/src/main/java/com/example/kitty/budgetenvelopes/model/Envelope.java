package com.example.kitty.budgetenvelopes.model;

import io.realm.RealmObject;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Envelope extends RealmObject {
    private String name;

    public String getPayee() {
        return name;
    }

    public void setPayee(String name) {
        this.name = name;
    }
}
