package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
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

public class TransactionActivity extends BaseActivity {

    ListView transaction_list;
    ArrayList<Transaction> transaction_array;
    FloatingActionButton transaction_fab;
    Button new_env;
    TextView balance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(TransactionActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_envelopes:
                    intent = new Intent(TransactionActivity.this, EnvelopeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_transactions:
                    intent = new Intent(TransactionActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.transaction_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        transaction_array = new ArrayList<Transaction>();
        transaction_list = (ListView) findViewById(R.id.transaction_list);
        transaction_fab = (FloatingActionButton) findViewById(R.id.add_transaction_fab);
        new_env = (Button) findViewById(R.id.trans_env_button);
        balance = (TextView) findViewById(R.id.trans_balance_view);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        double global_bal = realm.where(Globals.class).findFirst().getGlobal_balance();
        DecimalFormat bal_decimal = new DecimalFormat("#0.00");
        balance.setText(bal_decimal.format(global_bal));

        envelopeCheck();

        new_env.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, AddEnvelopeActivity.class);
                startActivity(intent);
            }
        });

        RealmResults<Transaction> transactions = realm.where(Transaction.class).findAll();

        if(transactions.size() != 0) {
            for(int i = 0; i < transactions.size(); i++) {
                transaction_array.add(transactions.get(i));
            }

            TransactionListAdapter transaction_list_adapter = new TransactionListAdapter(this, R.layout.transaction_list_view, transaction_array);
            transaction_list.setAdapter(transaction_list_adapter);

            transaction_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Transaction trans_detail = transaction_array.get(position);
                    int trans_id = trans_detail.getTrans_id();
                    Intent intent = new Intent(TransactionActivity.this, DetailTransactionActivity.class);
                    intent.putExtra("transaction id", trans_id);
                    realm.close();
                    startActivity(intent);
                }
            });
        }

        transaction_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void envelopeCheck() {
        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();

        if(envelopes.size() == 0) {
            new_env.setVisibility(View.VISIBLE);
        } else {
            new_env.setVisibility(View.INVISIBLE);
        }
    }
}
