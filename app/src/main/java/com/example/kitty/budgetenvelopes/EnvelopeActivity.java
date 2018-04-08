package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EnvelopeActivity extends BaseActivity {

    private static final String TAG = "EnvelopeActivity";

    Button message;
    ListView list_view;
    ArrayList<Envelope> envelope_array = new ArrayList<Envelope>();
    FloatingActionButton envelope_fab;
    TextView balance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(EnvelopeActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_envelopes:
                    intent = new Intent(EnvelopeActivity.this, EnvelopeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_transactions:
                    intent = new Intent(EnvelopeActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envelope);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.envelope_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        message = (Button) findViewById(R.id.message);
        list_view = (ListView) findViewById(R.id.envelope_list_view);
        envelope_fab = (FloatingActionButton) findViewById(R.id.add_envelope_fab);
        balance = (TextView) findViewById(R.id.env_balance_view);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        double global_bal = realm.where(Globals.class).findFirst().getGlobal_balance();
        DecimalFormat bal_decimal = new DecimalFormat("#0.00");
        balance.setText(bal_decimal.format(global_bal));

        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();

        if(envelopes.size() != 0) {
            for (int i = 0; i < envelopes.size(); i++) {
                envelope_array.add(envelopes.get(i));
            }

            EnvelopeListAdapter envelope_list_adapter = new EnvelopeListAdapter(this, R.layout.envelope_list_view, envelope_array);
            list_view.setAdapter(envelope_list_adapter);

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
            });
        } else {
            message.setVisibility(View.VISIBLE);

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EnvelopeActivity.this, AddEnvelopeActivity.class);
                    startActivity(intent);
                }
            });
        }

        envelope_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnvelopeActivity.this, AddEnvelopeActivity.class);
                startActivity(intent);
            }
        });
    }

}
