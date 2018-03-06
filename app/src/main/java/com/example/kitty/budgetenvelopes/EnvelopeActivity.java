package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;

import io.realm.Realm;

public class EnvelopeActivity extends AppCompatActivity {

    Realm realm;
    TextView message;

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
                case R.id.navigation_add_envelopes:
                    intent = new Intent(EnvelopeActivity.this, AddEnvelopeActivity.class);
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

        realm = Realm.getDefaultInstance();
        message = (TextView) findViewById(R.id.message);

        Envelope envelope = realm.where(Envelope.class).equalTo("name", "kitty").findFirst();
        if(envelope != null) {
            String name = envelope.getEnvelopeName();
            message.setText(name);
        }

    }

}
