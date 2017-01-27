package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by damien on 23/01/17.
 */

public class ChannelListActivity extends Activity implements OnDownloadListener, AdapterView.OnItemClickListener{
    private ListView listechannel ;
    private String accesstoken;
    Channels channels = new Channels();
    private String[] listItem = {};

    public static final String PREFS_NAME = "MyPrefsFile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channellist);

        listechannel = (ListView) findViewById(R.id.listViewChannel);
        HashMap<String,String> envoiAccess = new HashMap<>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        accesstoken = settings.getString("accesstoken", null);
        envoiAccess.put("accesstoken", accesstoken);
        Downloader d = new Downloader(getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=getchannels",envoiAccess);
        d.setOnDownloadComplete(this);
        d.execute();
        listechannel.setOnItemClickListener(this);
    }


    @Override
    public void OnDownloadComplete(String result) {
        Gson gson = new Gson();
        channels=gson.fromJson(result, Channels.class);

        listechannel.setAdapter(new MyChannelArrayAdapter(getApplicationContext(), R.layout.activity_channellist, channels.getChannels()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getApplicationContext(), ChannelConvActivity.class);
        String name = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
        myIntent.putExtra("id", id);
        myIntent.putExtra("name", name);
        startActivity(myIntent);
    }
}
