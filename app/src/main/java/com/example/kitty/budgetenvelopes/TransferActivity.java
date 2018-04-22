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

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/17/2018.
 */

public class TransferActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Spinner from_envelope;
    private Spinner to_envelope;
    private EditText transfer_amount;
    private Button accept;
    private Button cancel;
    ArrayList<String> names;
    String from;
    String to;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        realm = Realm.getDefaultInstance();
        transfer_amount = (EditText) findViewById(R.id.transfer_amount);
        accept = (Button) findViewById(R.id.transfer_accept);
        cancel = (Button) findViewById(R.id.transfer_cancel);
        names = new ArrayList<String>();

        loadFromEnvelopeSpinner();

        from_envelope.setOnItemSelectedListener(this);
        to_envelope.setOnItemSelectedListener(this);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("FROM " + from);
                System.out.println("TO " + to);
                realm.beginTransaction();

                Double amount = Double.parseDouble(transfer_amount.getText().toString());

                Envelope from_env = realm.where(Envelope.class).equalTo("name", from).findFirst();
                Globals global = realm.where(Globals.class).findFirst();

                Double balance = from_env.getBalance();
                balance -= amount;
                from_env.setBalance(balance);

                int from_id = global.getGlobal_trans_id();
                Transaction from_trans = realm.createObject(Transaction.class, from_id);

                Calendar calendar = Calendar.getInstance();
                from_trans.setDate(calendar);
                from_trans.setPayee(to);
                from_trans.setAmount(amount);
                from_trans.setType("Debit");
                from_trans.setEnvelope(from);
                from_trans.setTransfer(true);

                realm.insertOrUpdate(from_env);
                realm.commitTransaction();
                realm.beginTransaction();

                Envelope to_env = realm.where(Envelope.class).equalTo("name", to).findFirst();
                balance = to_env.getBalance();
                balance += amount;
                to_env.setBalance(balance);

                int to_id = from_id + 1;
                Transaction to_trans = realm.createObject(Transaction.class, to_id);

                to_trans.setDate(calendar);
                to_trans.setPayee(from);
                to_trans.setAmount(amount);
                to_trans.setType("Credit");
                to_trans.setEnvelope(to);
                to_trans.setTransfer(true);

                global.setGlobal_trans_id(to_id+1);

                realm.insertOrUpdate(to_env);
                realm.commitTransaction();
                realm.close();
                Intent intent = new Intent(TransferActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.close();
                Intent intent = new Intent(TransferActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadFromEnvelopeSpinner() {
        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAllSorted("name");

        if(envelopes.size() != 0) {
            for(int i = 0; i < envelopes.size(); i++) {
                names.add(envelopes.get(i).getEnvelopeName());
                if(i == 0) {
                    from = envelopes.get(i).getEnvelopeName();
                    to = from;
                }
            }

            from_envelope = (Spinner) findViewById(R.id.transfer_from_envelope);
            to_envelope = (Spinner) findViewById(R.id.transfer_to_envelope);
            ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            from_envelope.setAdapter(spinner_adapter);
            to_envelope.setAdapter(spinner_adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent == from_envelope) {
            from = parent.getItemAtPosition(position).toString();
            System.out.println("from " + from);
        } else if(parent == to_envelope) {
            to = parent.getItemAtPosition(position).toString();
            System.out.println("to " + to);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
