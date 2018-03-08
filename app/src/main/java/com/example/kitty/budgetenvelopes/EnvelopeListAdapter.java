package com.example.kitty.budgetenvelopes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kitty.budgetenvelopes.model.Envelope;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by Kitty on 3/7/2018.
 */

public class EnvelopeListAdapter extends RealmBaseAdapter<Envelope> {
    private static class ViewHolder {
        TextView env_name;
        TextView env_balance;
    }

    public EnvListAdapter(OrderedRealmCollection<Envelope> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.envelope_list_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.env_name = (TextView) convertView.findViewById(android.R.id.env_name);
        }
    }
}
