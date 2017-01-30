package com.fernandes.damien.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by damien on 23/01/17.
 */

public class ChannelListArrayAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private List<Channel> liste;

    public ChannelListArrayAdapter(Context context, int resource, List<Channel> objects) {
        super(context, resource, 0, objects);
        this.context=context;
        this.liste=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayoutchannels, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView subtitle = (TextView) rowView.findViewById(R.id.textViewSubTitle);

        title.setText(getItem(position).getName());
        subtitle.setText("Nombre d'utilisateurs connectés : " + getItem(position).getConnectedusers());

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return this.getItem(position).getChannelID();
    }
}
