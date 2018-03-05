package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTransactionActivity extends AppCompatActivity {

    private static final String TAG = "AddTransactionActivity";

    private EditText date_view;
    private EditText payee_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        date_view = (EditText) findViewById(R.id.date_transaction_edit_text);

        Intent incoming_intent = getIntent();
        String date = incoming_intent.getStringExtra("date");
        date_view.setText(date);

        date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTransactionActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.debit_credit_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.debit_credit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        payee_view = (EditText) findViewById(R.id.name_edit_text);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){

        String date = data.getStringExtra("date");
        date_view.setText(date);
    }

}
