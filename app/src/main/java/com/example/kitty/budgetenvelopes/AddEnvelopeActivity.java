package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kitty.budgetenvelopes.model.Envelope;

import io.realm.Realm;

public class AddEnvelopeActivity extends AppCompatActivity {

    private static final String TAG = "AddEnvelopeActivity";

    private EditText envelope_name;
    private EditText opening_balance;
    private EditText refill_date_1;
    private EditText refill_amount;
    private Button cancel_envelope;
    private Button accept_envelope;

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

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.commitTransaction();

        accept_envelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEnvelope();
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

    private void saveEnvelope() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Envelope envelope = bgRealm.createObject(Envelope.class);
                envelope.setEnvelopeName(envelope_name.getText().toString().trim());
                envelope.setBalance(Double.parseDouble(opening_balance.getText().toString().trim()));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Data written successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: Error occurred while writing");
            }
        });
    }
}
