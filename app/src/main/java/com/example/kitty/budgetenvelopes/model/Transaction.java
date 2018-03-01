package com.example.kitty.budgetenvelopes.model;


import io.realm.RealmObject;

/**
 * Created by Kitty on 2/16/2018.
 */

public class Transaction extends RealmObject {

    private String payee;
    private double amount;
    private Envelope envelope;
    //private Calendar date;
    private boolean cleared = false;

    public Transaction() {}

    public Transaction(String payee, double amount, Envelope envelope /*, Calendar date*/) {
        this.payee = payee;
        this.amount = amount;
        this.envelope = envelope;
        //this.date = date;
    }

    public void process(Envelope envelope, double amount) {

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

    public Envelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(final Envelope envelope) {
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

}
