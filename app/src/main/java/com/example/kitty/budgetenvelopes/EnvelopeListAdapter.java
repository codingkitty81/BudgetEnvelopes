package com.example.kitty.budgetenvelopes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;

import java.util.ArrayList;

/**
 * Created by Kitty on 3/7/2018.
 */

public class EnvelopeListAdapter extends ArrayAdapter<Envelope> {
    //adapted from https://youtu.be/E6vE8fqQPTE
    //adapted from https://youtu.be/SApBLHIpH8A
    private static final String TAG = "EnvelopeListAdapter";

    private Context this_context;
    private int this_resource;
    private int last_position = -1;

    //holds the variables in a view
    static class ViewHolder {
        TextView env_name;
        TextView balance;
    }

    public EnvelopeListAdapter(@NonNull Context context, int resource, ArrayList<Envelope> objects) {
        super(context, resource, objects);
        this_context = context;
        this_resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //grab the information from the Envelope
        String envelope_name = getItem(position).getEnvelopeName();
        Double balance = getItem(position).getBalance();

        //create a new envelope object with the information
        Envelope envelope = new Envelope(envelope_name, balance);

        //create the view result for showing the animation
        final View result;
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this_context);
            convertView = inflater.inflate(this_resource, parent, false);

            holder = new ViewHolder();
            holder.env_name = (TextView) convertView.findViewById(R.id.env_name);
            holder.balance = (TextView) convertView.findViewById(R.id.env_balance);
            convertView.setTag(holder);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //if position > last_position then load_down_anim else load_up_anim
        Animation animation = AnimationUtils.loadAnimation(this_context, (position > last_position) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);

        holder.env_name.setText(envelope.getEnvelopeName());
        holder.balance.setText(Double.toString(envelope.getBalance()));

        return convertView;
    }
}
