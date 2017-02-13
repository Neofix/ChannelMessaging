package com.fernandes.damien.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by damien on 27/01/17.
 */

public class ChannelAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> liste;

    public ChannelAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.liste=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayoutconv, parent, false);

        TextView User = (TextView) rowView.findViewById(R.id.textViewUserName);
        TextView message = (TextView) rowView.findViewById(R.id.textViewMessage);

        User.setText(getItem(position).getUsername()+" : ");
        message.setText(getItem(position).getMessage());

        return rowView;
    }
}
