package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEnvelopeActivity extends AppCompatActivity {

    private static final String TAG = "AddEnvelopeActivity";

    private EditText envelope_name;
    private EditText opening_balance;
    private EditText refill_date_1;
    private EditText refill_amount;
    private Button cancel_envelope;
    private Button accept_envelope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_envelope);

        refill_date_1 = (EditText) findViewById(R.id.envelope_refill_date_1);

        refill_date_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEnvelopeActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        envelope_name = (EditText) findViewById(R.id.name_edit_text);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){

        String date = data.getStringExtra("date");
        refill_date_1.setText(date);
    }
}
