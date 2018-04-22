package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;
import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/8/2018.
 */

public class EditTransactionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private int id;
    ArrayList<String> names;
    Transaction transaction;
    private EditText date_edit;
    private EditText payee_edit;
    private EditText amount_edit;
    private Spinner type_edit;
    private Spinner envelope_edit;
    private Button accept;
    private Button cancel;
    double old_amount;
    String old_envelope;
    String old_type;
    String new_envelope;
    String new_type;
    Globals global;
    Envelope envelope;

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
        String date = incoming_intent.getStringExtra("date");

        date_edit = (EditText) findViewById(R.id.edit_transaction_date_edit);
        payee_edit = (EditText) findViewById(R.id.edit_transaction_payee_edit);
        amount_edit = (EditText) findViewById(R.id.edit_transaction_amount_edit);
        type_edit = (Spinner) findViewById(R.id.edit_transaction_type_edit);
        accept = (Button) findViewById(R.id.edit_transaction_accept);
        cancel = (Button) findViewById(R.id.edit_transaction_cancel);

        SimpleDateFormat sdf;
        if(date == null) {
            sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            date_edit.setText(sdf.format(transaction.getDate().getTime()));
        } else {
            sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            date_edit.setText(sdf.format(date));
        }

        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTransactionActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        payee_edit.setText(transaction.getPayee());
        Double trans_amount = transaction.getAmount();
        DecimalFormat format_amount = new DecimalFormat("#0.00");
        amount_edit.setText(format_amount.format(trans_amount));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.debit_credit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type_edit.setAdapter(adapter);

        type_edit.setOnItemSelectedListener(this);
        envelope_edit.setOnItemSelectedListener(this);

        old_amount = transaction.getAmount();
        old_envelope = transaction.getEnvelope();
        old_type = transaction.getType();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                global = realm.where(Globals.class).findFirst();
                envelope = realm.where(Envelope.class).equalTo("name", old_envelope).findFirst();

                //adjust global and envelope balance as though transaction is getting deleted
                Double global_balance = global.getGlobal_balance();
                Double envelope_balance = envelope.getBalance();

                if (old_type.equals("Debit")) {
                    global_balance += old_amount;
                    envelope_balance += old_amount;
                } else {
                    global_balance -= old_amount;
                    envelope_balance -= old_amount;
                }
                envelope.setBalance(envelope_balance);

                transaction.setPayee(payee_edit.getText().toString());
                transaction.setAmount(Double.parseDouble(amount_edit.getText().toString()));

                if(!new_envelope.isEmpty()) {
                    envelope = realm.where(Envelope.class).equalTo("name", new_envelope).findFirst();
                    envelope_balance = envelope.getBalance();
                    transaction.setEnvelope(new_envelope);
                }

                if(!new_type.isEmpty()) {
                    transaction.setType(new_type);
                    if(new_type.equals("Debit")) {
                        global_balance -= Double.parseDouble(amount_edit.getText().toString());
                        envelope_balance -= Double.parseDouble(amount_edit.getText().toString());
                    } else {
                        global_balance += Double.parseDouble(amount_edit.getText().toString());
                        envelope_balance += Double.parseDouble(amount_edit.getText().toString());
                    }
                }

                global.setGlobal_balance(global_balance);
                envelope.setBalance(envelope_balance);

                realm.insertOrUpdate(transaction);
                realm.commitTransaction();
                realm.close();
                Intent intent = new Intent(EditTransactionActivity.this, TransactionActivity.class);
                //intent.putExtra("transaction id", id);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.close();
                Intent intent = new Intent(EditTransactionActivity.this, TransactionActivity.class);
                //intent.putExtra("transaction id", id);
                startActivity(intent);
            }
        });
    }

    public void loadEnvelopeSpinner() {
        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAllSorted("name");

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent == type_edit) {
            new_type = parent.getItemAtPosition(position).toString();
        } else if(parent == envelope_edit) {
            new_envelope = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
