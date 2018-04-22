package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;
import com.example.kitty.budgetenvelopes.model.Globals;
import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kitty on 4/7/2018.
 */

public class DetailTransactionActivity extends BaseActivity {

    private TextView date;
    private TextView payee;
    private TextView amount;
    private TextView type;
    private TextView envelope;
    private Transaction transaction;
    private Button delete;
    private Button edit;
    private int id;
    private Globals global;
    private Envelope env;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        date = (TextView) findViewById(R.id.transaction_detail_date_view);
        payee = (TextView) findViewById(R.id.transaction_detail_payee_view);
        amount = (TextView) findViewById(R.id.transation_detail_amount_view);
        type = (TextView) findViewById(R.id.transaction_detail_type_view);
        envelope = (TextView) findViewById(R.id.transaction_detail_envelope_view);

        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        id = intent.getIntExtra("transaction id", 0);

        transaction = realm.where(Transaction.class).equalTo("trans_id", id).findFirst();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        date.setText(sdf.format(transaction.getDate().getTime()));
        payee.setText(transaction.getPayee());
        final Double trans_amount = transaction.getAmount();
        DecimalFormat bal_amount = new DecimalFormat("#0.00");
        amount.setText(bal_amount.format(trans_amount));
        type.setText(transaction.getType());
        envelope.setText(transaction.getEnvelope());

        delete = (Button) findViewById(R.id.delete_transaction_button);
        edit = (Button) findViewById(R.id.edit_transaction_button);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_intent = new Intent(DetailTransactionActivity.this, EditTransactionActivity.class);
                edit_intent.putExtra("transaction id", id);
                realm.close();
                startActivity(edit_intent);
            }
        });

        global = realm.where(Globals.class).findFirst();
        env = realm.where(Envelope.class).equalTo("name", transaction.getEnvelope()).findFirst();

        delete.setOnClickListener(new View.OnClickListener() {
            //adapted from https://stackoverflow.com/questions/36736178/how-to-delete-object-from-realm-database-android/36736415
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Double global_balance = global.getGlobal_balance();
                        Double env_balance = env.getBalance();
                        int other_trans_id;

                        if(transaction.isTransfer() == true) {
                            if(transaction.getType().equals("Debit")) {
                                other_trans_id = id + 1;
                            } else {
                                other_trans_id = id - 1;
                            }

                            Transaction other_transaction = realm.where(Transaction.class).equalTo("trans_id", other_trans_id).findFirst();
                            Envelope other_envelope = realm.where(Envelope.class).equalTo("name", other_transaction.getEnvelope()).findFirst();
                            Double other_env_balance = other_envelope.getBalance();

                            if(id < other_trans_id) {
                                env_balance += transaction.getAmount();
                                other_env_balance -= other_transaction.getAmount();
                            } else {
                                env_balance -= transaction.getAmount();
                                other_env_balance += other_transaction.getAmount();
                            }

                            env.setBalance(env_balance);
                            other_envelope.setBalance(other_env_balance);

                            RealmResults<Transaction> first_trans = realm.where(Transaction.class).equalTo("trans_id", id).findAll();
                            RealmResults<Transaction> other_trans = realm.where(Transaction.class).equalTo("trans_id", other_trans_id).findAll();

                            first_trans.deleteAllFromRealm();
                            other_trans.deleteAllFromRealm();
                        } else {
                            if(transaction.getType().equals("Debit")) {
                                global_balance += transaction.getAmount();
                                env_balance += transaction.getAmount();
                            } else {
                                global_balance -= transaction.getAmount();
                                env_balance -= transaction.getAmount();
                            }

                            global.setGlobal_balance(global_balance);
                            env.setBalance(env_balance);

                            RealmResults<Transaction> result = realm.where(Transaction.class).equalTo("trans_id", id).findAll();
                            result.deleteAllFromRealm();
                        }

                        realm.close();
                        Intent intent = new Intent(DetailTransactionActivity.this, TransactionActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
