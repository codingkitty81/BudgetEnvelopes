package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 3/12/2018.
 */

public class DetailEnvelopeActivity extends BaseActivity {

    private static final String TAG = "DetailEnvelopeActivity";

    TextView envelope_name;
    TextView envelope_balance;
    Envelope current_envelope;
    ListView list_view;
    ArrayList<Transaction> transaction_array = new ArrayList<Transaction>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_envelope);

        envelope_name = (TextView) findViewById(R.id.name_text_view);
        envelope_balance = (TextView) findViewById(R.id.view_balance);
        list_view = (ListView) findViewById(R.id.env_transaction_list);
        transaction_array = new ArrayList<Transaction>();

        Realm realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        String envelope = (String) intent.getStringExtra("envelope name");

        Log.d(TAG, "onCreate: " + envelope);

        current_envelope = realm.where(Envelope.class).equalTo("name", envelope).findFirst();

        Log.d(TAG, "onCreate: Realm query" + current_envelope.getEnvelopeName().toString());

        envelope_name.setText(current_envelope.getEnvelopeName().toString());
        Double bal = current_envelope.getBalance();
        DecimalFormat bal_decimal = new DecimalFormat("#0.00");
        envelope_balance.setText(bal_decimal.format(bal));

        RealmResults<Transaction> transactions = realm.where(Transaction.class).equalTo("envelope", envelope).findAll();

        if(transactions.size() != 0) {
            for (int i = 0; i < transactions.size(); i++) {
                transaction_array.add(transactions.get(i));
            }

            TransactionListAdapter transaction_list_adapter = new TransactionListAdapter(this, R.layout.transaction_list_view, transaction_array);
            list_view.setAdapter(transaction_list_adapter);
            /*
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Envelope env_detail = envelope_array.get(position);
                    String env_detail_name = env_detail.getEnvelopeName();
                    Intent intent = new Intent(EnvelopeActivity.this, DetailEnvelopeActivity.class);
                    intent.putExtra("envelope name", env_detail_name);
                    Log.d(TAG, "onItemClick: " + env_detail_name);
                    realm.close();
                    startActivity(intent);
                }
            });*/
        }
    }
}
