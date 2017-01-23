package com.fernandes.damien.channelmessaging;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by damien on 23/01/17.
 */

public class MyChannelArrayAdapter extends ArrayAdapter {

    public MyChannelArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MyChannelArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MyChannelArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public MyChannelArrayAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MyChannelArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public MyChannelArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
