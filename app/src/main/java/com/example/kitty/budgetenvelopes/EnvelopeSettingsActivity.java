package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.kitty.budgetenvelopes.model.Envelope;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/22/2018.
 */

public class EnvelopeSettingsActivity extends BaseActivity {

    Realm realm;
    private CheckBox round_up;
    private CheckBox move_balance;
    private Button accept;
    private Button cancel;
    ArrayList<Envelope> envelope_array = new ArrayList<Envelope>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envelope_settings);

        round_up = (CheckBox) findViewById(R.id.envelope_settings_round_up);
        move_balance = (CheckBox) findViewById(R.id.envelope_settings_move_bal);
        accept = (Button) findViewById(R.id.settings_envelope_accept);
        cancel = (Button) findViewById(R.id.settings_envelope_cancel);
        realm = Realm.getDefaultInstance();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();
                for(int i = 0; i < envelopes.size(); i++) {
                    if(!envelopes.get(i).getEnvelopeName().equals("Savings")) {
                        envelope_array.add(envelopes.get(i));
                    }
                }

                for(int i = 0; i < envelope_array.size(); i++) {
                    realm.beginTransaction();
                    if(round_up.isChecked()) {
                        envelope_array.get(i).setRound_up_flag(true);
                    } else {
                        envelope_array.get(i).setRound_up_flag(false);
                    }

                    if(move_balance.isChecked()) {
                        envelope_array.get(i).setMove_balance_flag(true);
                    } else {
                        envelope_array.get(i).setMove_balance_flag(false);
                    }

                    realm.insertOrUpdate(envelope_array.get(i));
                    realm.commitTransaction();
                }
                realm.close();
                Intent intent = new Intent(EnvelopeSettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.close();
                Intent intent = new Intent(EnvelopeSettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
