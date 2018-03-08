package com.example.kitty.budgetenvelopes;

import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

/**
 * Created by Kitty on 3/7/2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected Realm realm;

    @Override
    public void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onPause() {
        super.onPause();
        realm.close();
    }
}
