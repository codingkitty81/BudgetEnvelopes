package com.example.kitty.budgetenvelopes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kitty on 3/26/2018.
 */

public class TransactionListAdapter extends ArrayAdapter<Transaction> {

    private Context this_context;
    private int this_resource;
    private int last_position = -1;

    static class ViewHolder {
        TextView payee;
        TextView date;
        TextView amount;
    }

    public TransactionListAdapter(@NonNull Context context, int resource, ArrayList<Transaction> objects) {
        super(context, resource, objects);
        this_context = context;
        this_resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String payee = getItem(position).getPayee();
        Calendar date = getItem(position).getDate();
        Double money = getItem(position).getAmount();


        Transaction transaction = new Transaction(payee, money, date);

        final View result;
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this_context);
            convertView = inflater.inflate(this_resource, parent, false);

            holder = new ViewHolder();
            holder.payee = (TextView) convertView.findViewById(R.id.payee_view);
            holder.date = (TextView) convertView.findViewById(R.id.date_view);
            holder.amount = (TextView) convertView.findViewById(R.id.amount_view);

            convertView.setTag(holder);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.payee.setText(transaction.getPayee());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        holder.date.setText(sdf.format(transaction.getDate().getTime()));
        Double amounts = transaction.getAmount();
        DecimalFormat bal_decimal = new DecimalFormat("#0.00");
        holder.amount.setText(bal_decimal.format(amounts));

        return convertView;
    }
}
