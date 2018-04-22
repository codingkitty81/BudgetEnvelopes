package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.annotation.Nullable;

import io.realm.Realm;

/**
 * Created by Kitty on 4/10/2018.
 */

public class EditEnvelopeActivity extends BaseActivity {

    private TextView name;
    private EditText balance;
    private CheckBox round_up;
    private CheckBox balance_move;
    private Button accept;
    private Button cancel;
    ArrayList<String> envelope_names;
    String holder;
    Envelope envelope;
    Globals global;
    Double old_balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_envelope);

        realm = Realm.getDefaultInstance();
        envelope_names = new ArrayList<String>();

        Intent incoming_intent = getIntent();
        holder = incoming_intent.getStringExtra("name");
        envelope = realm.where(Envelope.class).equalTo("name", holder).findFirst();

        name = (TextView) findViewById(R.id.edit_envelope_name_view);
        balance = (EditText) findViewById(R.id.edit_envelope_opening_balance_edit);
        round_up = (CheckBox) findViewById(R.id.edit_round_up_check);
        balance_move = (CheckBox) findViewById(R.id.edit_move_balance_check);
        accept = (Button) findViewById(R.id.edit_envelope_accept_button);
        cancel = (Button) findViewById(R.id.edit_envelope_cancel);

        name.setText(envelope.getEnvelopeName());
        Double envelope_balance = envelope.getBalance();
        DecimalFormat format_amount = new DecimalFormat("#0.00");
        balance.setText(format_amount.format(envelope_balance));
        if(envelope.isRound_up_flag() == true) {
            round_up.setChecked(true);
        }

        if(envelope.isMove_balance_flag() == true) {
            balance_move.setChecked(true);
        }

        old_balance = envelope.getBalance();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                global = realm.where(Globals.class).findFirst();
                Double global_balance = global.getGlobal_balance();
                global_balance -= old_balance;
                global_balance += Double.parseDouble(balance.getText().toString());
                global.setGlobal_balance(global_balance);

                envelope.setBalance(Double.parseDouble(balance.getText().toString()));
                if(round_up.isChecked()) {
                    envelope.setRound_up_flag(true);
                } else {
                    envelope.setRound_up_flag(false);
                }

                if(balance_move.isChecked() == true) {
                    envelope.setMove_balance_flag(true);
                } else {
                    envelope.setMove_balance_flag(false);
                }

                realm.insertOrUpdate(envelope);
                realm.commitTransaction();
                realm.close();
                Intent intent = new Intent(EditEnvelopeActivity.this, EnvelopeActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.close();
                Intent intent = new Intent(EditEnvelopeActivity.this, EnvelopeActivity.class);
                startActivity(intent);
            }
        });
    }
}
