package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.kitty.budgetenvelopes.model.Transaction;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class TransactionActivity extends BaseActivity {

    ListView transaction_list;
    ArrayList<Transaction> transaction_array;
    FloatingActionButton transaction_fab;

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

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        RealmResults<Transaction> transactions = realm.where(Transaction.class).findAll();

        if(transactions.size() != 0) {
            for(int i = 0; i < transactions.size(); i++) {
                transaction_array.add(transactions.get(i));
            }

            TransactionListAdapter transaction_list_adapter = new TransactionListAdapter(this, R.layout.transaction_list_view, transaction_array);
            transaction_list.setAdapter(transaction_list_adapter);
            /*
            transaction_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Transaction trans_detail = transaction_array.get(position);
                    String trans_payee = trans_detail.getPayee();
                    Intent intent = new Intent(TransactionActivity.this, DetailTransactionActivity.class);
                    intent.putExtra("transaction payee", trans_payee);
                    realm.close();
                    startActivity(intent);
                }
            });
            */
        }

        transaction_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

}
