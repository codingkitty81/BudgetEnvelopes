package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {

    private Button new_envelope;
    private Button settings;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_envelopes:
                    intent = new Intent(MainActivity.this, EnvelopeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_transactions:
                    intent = new Intent(MainActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        realm = Realm.getDefaultInstance();

        RealmResults<Globals> globals = realm.where(Globals.class).findAll();

        if(globals.size() == 0) {
            realm.beginTransaction();
            realm.createObject(Globals.class);
            realm.commitTransaction();
        }

        new_envelope = (Button) findViewById(R.id.main_new_envelope);
        settings = (Button) findViewById(R.id.main_settings_button);

        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAll();

        if(envelopes.size() == 0) {
            realm.beginTransaction();
            Envelope savings_env = realm.createObject(Envelope.class, "Savings");
            savings_env.setEnvelopeName("Savings");
            savings_env.setBalance(0.0);
            realm.commitTransaction();

            new_envelope.setVisibility(View.VISIBLE);
            settings.setVisibility(View.INVISIBLE);
            new_envelope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realm.close();
                    Intent intent = new Intent(MainActivity.this, AddEnvelopeActivity.class);
                    startActivity(intent);
                }
            });
        } else if(envelopes.size() == 1) {
            new_envelope.setVisibility(View.VISIBLE);
            settings.setVisibility(View.INVISIBLE);
            new_envelope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realm.close();
                    Intent intent = new Intent(MainActivity.this, AddEnvelopeActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            realm.close();
            new_envelope.setVisibility(View.INVISIBLE);
            settings.setVisibility(View.VISIBLE);
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
