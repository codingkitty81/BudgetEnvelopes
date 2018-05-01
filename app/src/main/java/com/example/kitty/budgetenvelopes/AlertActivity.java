package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kitty on 4/30/2018.
 */

public class AlertActivity extends BaseActivity {

    private Button accept;
    private Button cancel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        accept = (Button) findViewById(R.id.alert_accept_button);
        cancel = (Button) findViewById(R.id.alert_reject_button);

        intent = new Intent();
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("response", "accept");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("response", "cancel");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
