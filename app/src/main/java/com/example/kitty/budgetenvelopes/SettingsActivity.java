package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;
import com.example.kitty.budgetenvelopes.model.Transaction;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/22/2018.
 */

public class SettingsActivity extends BaseActivity {

    private Button envelope_settings;
    private Button reset_budget;
    private Button view_savings;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        envelope_settings = (Button) findViewById(R.id.settings_envelope_settings);
        reset_budget = (Button) findViewById(R.id.settings_reset);
        view_savings = (Button) findViewById(R.id.settings_savings_envelope);

        realm = Realm.getDefaultInstance();

        envelope_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, EnvelopeSettingsActivity.class);
                startActivity(intent);
            }
        });

        reset_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Globals global = realm.where(Globals.class).findFirst();
                        global.setGlobal_balance(0.0);
                        global.setGlobal_trans_id(0);
                        RealmResults<Transaction> transactions = realm.where(Transaction.class).findAll();
                        transactions.deleteAllFromRealm();
                        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();
                        envelopes.deleteAllFromRealm();
                        realm.close();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        view_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, DetailEnvelopeActivity.class);
                intent.putExtra("envelope name", "Savings");
                realm.close();
                startActivity(intent);
            }
        });
    }
}
