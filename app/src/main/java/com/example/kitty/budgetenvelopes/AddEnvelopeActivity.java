package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;

import io.realm.Realm;

public class AddEnvelopeActivity extends BaseActivity {

    private static final String TAG = "AddEnvelopeActivity";

    private EditText envelope_name;
    private EditText opening_balance;
    private Button cancel_envelope;
    private Button accept_envelope;
    private CheckBox round_up;
    private CheckBox move_balance;
    private Globals global;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_envelope);

        envelope_name = (EditText) findViewById(R.id.name_edit_text);
        opening_balance = (EditText) findViewById(R.id.open_balance_edit_text);
        accept_envelope = (Button) findViewById(R.id.accept_envelope_button);
        cancel_envelope = (Button) findViewById(R.id.cancel_envelope_button);
        round_up = (CheckBox) findViewById(R.id.round_up_check_box);
        move_balance = (CheckBox) findViewById(R.id.move_balance_check_box);

        realm = Realm.getDefaultInstance();

        global = realm.where(Globals.class).findFirst();

        accept_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Envelope new_env = realm.createObject(Envelope.class, envelope_name.getText().toString().trim());
                new_env.setEnvelopeName(envelope_name.getText().toString().trim());
                new_env.setBalance(Double.parseDouble(opening_balance.getText().toString()));

                //set round_up_flag to true
                if(round_up.isChecked()) {
                    new_env.setRound_up_flag(true);
                }

                //set move_balance_flag to true and set date for the end of the month
                if(move_balance.isChecked()) {
                    new_env.setMove_balance_flag(true);
                }

                Double global_balance = global.getGlobal_balance();
                global_balance += Double.parseDouble(opening_balance.getText().toString());
                global.setGlobal_balance(global_balance);

                realm.commitTransaction();
                realm.close();
                Intent intent = new Intent(AddEnvelopeActivity.this, EnvelopeActivity.class);
                startActivity(intent);
            }
        });

        cancel_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEnvelopeActivity.this, EnvelopeActivity.class);
                startActivity(intent);
            }
        });
    }
}
