package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;
import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddTransactionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddTransactionActivity";

    private EditText date_view;
    private EditText payee_view;
    private EditText amount;
    private Spinner spinner;
    private Spinner env_spinner;
    ArrayList<String> names;
    private String env_name;
    private String transaction_type = "Debit";
    private Button accept;
    private Button cancel;
    private int trans_id;
    private Globals global;
    private Envelope envelope;
    private Double rounded_up = 0.0;
    private Double difference = 0.0;
    private Double envelope_balance;
    private Double global_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        realm = Realm.getDefaultInstance();
        names = new ArrayList<String>();
        loadEnvelopeSpinner();

        date_view = (EditText) findViewById(R.id.date_transaction_edit_text);

        Intent incoming_intent = getIntent();
        String date = incoming_intent.getStringExtra("date");
        if(date == null) {
            System.out.println("date is an empty string");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            date = sdf.format(cal.getTime());
        }
        date_view.setText(date);

        date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTransactionActivity.this,
                        CalendarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        spinner = (Spinner) findViewById(R.id.debit_credit_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.debit_credit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        payee_view = (EditText) findViewById(R.id.payee_edit_text);
        amount = (EditText) findViewById(R.id.amount_edit_text);
        accept = (Button) findViewById(R.id.accept_transaction_button);
        cancel = (Button) findViewById(R.id.cancel_transaction_button);

        spinner.setOnItemSelectedListener(this);
        env_spinner.setOnItemSelectedListener(this);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global = realm.where(Globals.class).findFirst();
                envelope = realm.where(Envelope.class).equalTo("name", env_name).findFirst();

                realm.beginTransaction();
                trans_id = global.getGlobal_trans_id();
                Transaction new_trans = realm.createObject(Transaction.class, trans_id);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                try {
                    sdf.parse(date_view.getText().toString());
                } catch(Exception e) {
                    System.out.println("Kat's retarded " + date_view.getText().toString());
                }
                Calendar cal = sdf.getCalendar();

                new_trans.setDate(cal);
                new_trans.setPayee(payee_view.getText().toString().trim());
                new_trans.setAmount(Double.parseDouble(amount.getText().toString()));
                new_trans.setType(transaction_type.trim());
                new_trans.setEnvelope(env_name);

                global.setGlobal_trans_id(trans_id+1);
                global_balance = global.getGlobal_balance();
                envelope_balance = envelope.getBalance();

                if (transaction_type.equals("Debit")) {
                    if(envelope.isRound_up_flag()) {
                        rounded_up = Math.ceil(Double.parseDouble(amount.getText().toString()));
                        difference = rounded_up - Double.parseDouble(amount.getText().toString());

                        global_balance -= rounded_up;
                        envelope_balance -= rounded_up;

                        new_trans.setRounded_up(true);
                        new_trans.setDifference(difference);

                        Envelope savings = realm.where(Envelope.class).equalTo("name", "Savings").findFirst();
                        Double savings_balance = savings.getBalance();
                        savings_balance += difference;
                        savings.setBalance(savings_balance);
                        realm.insertOrUpdate(savings);
                    } else {
                        global_balance -= Double.parseDouble(amount.getText().toString());
                        envelope_balance -= Double.parseDouble(amount.getText().toString());
                    }
                } else {
                    global_balance += Double.parseDouble(amount.getText().toString());
                    envelope_balance += Double.parseDouble(amount.getText().toString());
                }
                System.out.println(envelope_balance);

                if(envelope_balance < 0.0) {
                    System.out.println("I'm below 0.0");
                    Intent intent = new Intent(AddTransactionActivity.this, AlertActivity.class);
                    startActivityForResult(intent, 1);
                }
                global.setGlobal_balance(global_balance);
                envelope.setBalance(envelope_balance);
                realm.commitTransaction();
                realm.close();
                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.close();
                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){
        if(request_code == 0) {
            String date = data.getStringExtra("date");
            date_view.setText(date);
        } else if(request_code == 1) {
            String response = data.getStringExtra("response");
            if(response.equals("accept")) {
                Envelope savings = realm.where(Envelope.class).equalTo("name", "Savings").findFirst();
                Double savings_balance = savings.getBalance();
                savings_balance += envelope_balance;
                global_balance -= envelope_balance;
                envelope_balance = 0.0;
                savings.setBalance(savings_balance);
            } else {
                realm.cancelTransaction();
                realm.close();
                Intent intent = new Intent(AddTransactionActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        }
    }

    private void loadEnvelopeSpinner() {
        RealmResults<Envelope> envelopes = realm.where(Envelope.class).findAllSorted("name");

        if(envelopes.size() != 0) {
            for (int i = 0; i < envelopes.size(); i++) {
                names.add(envelopes.get(i).getEnvelopeName());
                if(i == 0) {
                    env_name = envelopes.get(i).getEnvelopeName();
                }
            }

            env_spinner = (Spinner) findViewById(R.id.envelope_spinner);
            ArrayAdapter<String> env_spin_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
            env_spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            env_spinner.setAdapter(env_spin_adapter);
        } else {
            realm.close();
            Intent env_intent = new Intent(AddTransactionActivity.this, AddEnvelopeActivity.class);
            startActivity(env_intent);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent == spinner) {
            transaction_type = parent.getItemAtPosition(position).toString();
            System.out.println("trans_type " + transaction_type);
        } else if(parent == env_spinner) {
            env_name = parent.getItemAtPosition(position).toString();
            System.out.println("envelope " + env_name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
