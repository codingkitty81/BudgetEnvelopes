package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kitty.budgetenvelopes.model.Envelope;

import io.realm.Realm;

public class AddEnvelopeActivity extends BaseActivity {

    private static final String TAG = "AddEnvelopeActivity";

    private EditText envelope_name;
    private EditText opening_balance;
    private EditText refill_date_1;
    private EditText refill_amount;
    private Button cancel_envelope;
    private Button accept_envelope;
    private CheckBox round_up;
    private CheckBox move_balance;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_envelope);

        refill_date_1 = (EditText) findViewById(R.id.envelope_refill_date_1);

        refill_date_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEnvelopeActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        envelope_name = (EditText) findViewById(R.id.name_edit_text);
        opening_balance = (EditText) findViewById(R.id.open_balance_edit_text);
        accept_envelope = (Button) findViewById(R.id.accept_envelope_button);
        cancel_envelope = (Button) findViewById(R.id.cancel_envelope_button);
        refill_amount = (EditText) findViewById(R.id.envelope_refill_amount_1);
        round_up = (CheckBox) findViewById(R.id.round_up_check_box);
        move_balance = (CheckBox) findViewById(R.id.move_balance_check_box);

        realm = Realm.getDefaultInstance();

        accept_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                Envelope new_env = realm.createObject(Envelope.class, envelope_name.getText().toString().trim());
                new_env.setEnvelopeName(envelope_name.getText().toString().trim());
                new_env.setBalance(Double.parseDouble(opening_balance.getText().toString()));
                /*
                //add a transaction that recurs
                if(refill_date_1.toString() != "") {
                    Transaction recurring_transaction = new Transaction();

                    try {
                        DateFormat date_form = new SimpleDateFormat("MM/dd/yyyy");
                        Date result = date_form.parse(refill_date_1.getText().toString());
                        recurring_transaction.setDate(result);
                    } catch(ParseException parse_exception) {
                        parse_exception.printStackTrace();
                    }

                    recurring_transaction.setAmount(Double.parseDouble(refill_amount.getText().toString()));
                }
                */
                //set round_up_flag to true
                if(round_up.isChecked()) {
                    new_env.toggleRoundUp();
                }

                //set move_balance_flag to true and set date for the end of the month
                if(move_balance.isChecked()) {
                    new_env.toggleMoveBalance();

                }
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

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){

        String date = data.getStringExtra("date");
        refill_date_1.setText(date);
    }
}
