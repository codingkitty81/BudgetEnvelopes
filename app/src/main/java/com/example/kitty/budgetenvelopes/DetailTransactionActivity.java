package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.Realm;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        date = (TextView) findViewById(R.id.transaction_detail_date_view);
        payee = (TextView) findViewById(R.id.transaction_detail_payee_view);
        amount = (TextView) findViewById(R.id.transation_detail_amount_view);
        type = (TextView) findViewById(R.id.transaction_detail_type_view);
        envelope = (TextView) findViewById(R.id.transaction_detail_envelope_view);

        Realm realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        int id = (int) intent.getIntExtra("transaction id", 0);

        transaction = realm.where(Transaction.class).equalTo("trans_id", id).findFirst();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        date.setText(sdf.format(transaction.getDate().getTime()));
        payee.setText(transaction.getPayee());
        Double trans_amount = transaction.getAmount();
        DecimalFormat bal_amount = new DecimalFormat("#0.00");
        amount.setText(bal_amount.format(trans_amount));
        type.setText(transaction.getType());
        envelope.setText(transaction.getEnvelope());

    }
}
