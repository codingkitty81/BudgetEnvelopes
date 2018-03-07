package com.example.kitty.budgetenvelopes.model;


import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Transaction extends RealmObject {

    private String payee;
    private double amount;
    //private Calendar date;
    private boolean cleared = false;
    private boolean recurring = false;

    @Required
    private String envelope;

    public Transaction() {}

    public Transaction(String payee, double amount, String envelope /*, Calendar date*/) {
        this.payee = payee;
        this.amount = amount;
        this.envelope = envelope;
        //this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(final String payee) {
        this.payee = payee;
    }

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

    /*public LocalDate getDate() {
        return date;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }*/

    public boolean toggleClear() {
        if(!cleared) {
            cleared = true;
        } else {
            cleared = false;
        }
        return cleared;
    }

    public boolean toggleRecurring() {
        if(!recurring) {
            recurring = true;
        } else {
            recurring = false;
        }
        return recurring;
    }
}
