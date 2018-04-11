package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;
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
    Button edit_button;
    Button delete_button;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_envelope);

        envelope_name = (TextView) findViewById(R.id.name_text_view);
        envelope_balance = (TextView) findViewById(R.id.view_balance);
        list_view = (ListView) findViewById(R.id.env_transaction_list);
        transaction_array = new ArrayList<Transaction>();
        edit_button = (Button) findViewById(R.id.edit_envelope_button);
        delete_button = (Button) findViewById(R.id.delete_envelope_button);

        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        String envelope = (String) intent.getStringExtra("envelope name");

        Log.d(TAG, "onCreate: " + envelope);

        current_envelope = realm.where(Envelope.class).equalTo("name", envelope).findFirst();

        Log.d(TAG, "onCreate: Realm query" + current_envelope.getEnvelopeName().toString());

        envelope_name.setText(current_envelope.getEnvelopeName().toString());
        Double bal = current_envelope.getBalance();
        DecimalFormat bal_decimal = new DecimalFormat("#0.00");
        envelope_balance.setText(bal_decimal.format(bal));

        final RealmResults<Transaction> transactions = realm.where(Transaction.class).equalTo("envelope", envelope).findAll();

        if(transactions.size() != 0) {
            for (int i = 0; i < transactions.size(); i++) {
                transaction_array.add(transactions.get(i));
            }

            TransactionListAdapter transaction_list_adapter = new TransactionListAdapter(this, R.layout.transaction_list_view, transaction_array);
            list_view.setAdapter(transaction_list_adapter);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Transaction trans_detail = transaction_array.get(position);
                    int trans_id = trans_detail.getTrans_id();
                    Intent intent = new Intent(DetailEnvelopeActivity.this, DetailTransactionActivity.class);
                    intent.putExtra("transaction id", trans_id);
                    realm.close();
                    startActivity(intent);
                }
            });
        }

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailEnvelopeActivity.this, EditEnvelopeActivity.class);
                intent.putExtra("name", envelope_name.getText().toString());
                realm.close();
                startActivity(intent);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Globals global = realm.where(Globals.class).findFirst();
                        Double global_balance = global.getGlobal_balance();
                        if(Double.parseDouble(envelope_balance.getText().toString()) > 0.0) {
                            global_balance -= Double.parseDouble(envelope_balance.getText().toString());
                        } else {
                            global_balance += Double.parseDouble(envelope_balance.getText().toString());
                        }
                        global.setGlobal_balance(global_balance);
                        transactions.deleteAllFromRealm();
                        RealmResults<Envelope> env_result = realm.where(Envelope.class).equalTo("name",envelope_name.getText().toString()).findAll();
                        env_result.deleteAllFromRealm();
                    }
                });

                realm.close();
                Intent intent = new Intent(DetailEnvelopeActivity.this, EnvelopeActivity.class);
                startActivity(intent);
            }
        });
    }
}
