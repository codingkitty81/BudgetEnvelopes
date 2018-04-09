package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/8/2018.
 */

public class EditTransactionActivity extends BaseActivity {

    private int id;
    ArrayList<String> names;
    Transaction transaction;
    private TextView date_view;
    private TextView payee_view;
    private TextView amount_view;
    private TextView type_view;
    private TextView envelope_view;
    private EditText date_edit;
    private EditText payee_edit;
    private EditText amount_edit;
    private Spinner type_edit;
    private Spinner envelope_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        realm = Realm.getDefaultInstance();
        names = new ArrayList<String>();
        loadEnvelopeSpinner();

        Intent incoming_intent = getIntent();
        id = incoming_intent.getIntExtra("transaction id", 0);
        transaction = realm.where(Transaction.class).equalTo("trans_id", id).findFirst();

        date_view = (TextView) findViewById(R.id.edit_transaction_date_view);
        payee_view = (TextView) findViewById(R.id.edit_transaction_payee_view);
        amount_view = (TextView) findViewById(R.id.edit_transaction_amount_view);
        type_view = (TextView) findViewById(R.id.edit_transaction_type_view);
        envelope_view = (TextView) findViewById(R.id.edit_transaction_envelope_view);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        date_view.setText(sdf.format(transaction.getDate().getTime()));
        payee_view.setText(transaction.getPayee());
        Double trans_amount = transaction.getAmount();
        DecimalFormat format_amount = new DecimalFormat("#0.00");
        amount_view.setText(format_amount.format(trans_amount));
        type_view.setText(transaction.getType());
        envelope_view.setText(transaction.getEnvelope());

        date_edit = (EditText) findViewById(R.id.edit_transaction_date_edit);
        payee_edit = (EditText) findViewById(R.id.edit_transaction_payee_edit);
        amount_edit = (EditText) findViewById(R.id.edit_transaction_amount_edit);
        type_edit = (Spinner) findViewById(R.id.edit_transaction_type_edit);

        Intent date_intent = getIntent();
        String date = date_intent.getStringExtra("date");
        if(date == null) {
            System.out.println("date is an empty string");
            Calendar cal = Calendar.getInstance();
            //sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            date = sdf.format(cal.getTime());
        }
        date_edit.setText(date);

        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTransactionActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void loadEnvelopeSpinner() {
        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();
        envelopes = envelopes.sort("name");

        if(envelopes.size() != 0) {
            for (int i = 0; i < envelopes.size(); i++) {
                names.add(envelopes.get(i).getEnvelopeName());
            }
            envelope_edit = (Spinner) findViewById(R.id.edit_transaction_envelope_edit);
            ArrayAdapter<String> envelope_spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
            envelope_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            envelope_edit.setAdapter(envelope_spinner_adapter);
        } else {
            System.out.println("Envelope problems in Edit Transaction");
        }
    }
}
