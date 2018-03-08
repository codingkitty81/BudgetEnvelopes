package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;

import io.realm.Realm;
import io.realm.RealmResults;

public class EnvelopeActivity extends BaseActivity {

    TextView message;
    ListView list_view;

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

        message = (TextView) findViewById(R.id.message);
        list_view = (ListView) findViewById(R.id.envelope_list_view);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();
        EnvelopeListAdapter envelopeListAdapter = new EnvelopeListAdapter(envelopes);
        list_view.setAdapter(envelopeListAdapter);

    }

}
