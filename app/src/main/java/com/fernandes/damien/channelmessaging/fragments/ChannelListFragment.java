package com.fernandes.damien.channelmessaging.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fernandes.damien.channelmessaging.ChannelMainActivity;
import com.fernandes.damien.channelmessaging.ChannelListArrayAdapter;
import com.fernandes.damien.channelmessaging.Channels;
import com.fernandes.damien.channelmessaging.Downloader;
import com.fernandes.damien.channelmessaging.OnDownloadListener;
import com.fernandes.damien.channelmessaging.R;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by fernandd on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadListener{

    private ListView listechannel ;
    private String accesstoken;
    public Channels channels = new Channels();


    public static final String PREFS_NAME = "MyPrefsFile";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_channellist, container);
        listechannel = (ListView) v.findViewById(R.id.listViewChannel);

        return v;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HashMap<String,String> envoiAccess = new HashMap<>();
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        accesstoken = settings.getString("accesstoken", null);
        envoiAccess.put("accesstoken", accesstoken);
        Downloader d = new Downloader(getActivity(),"http://www.raphaelbischof.fr/messaging/?function=getchannels",envoiAccess, 0);
        d.setOnDownloadComplete(this);
        d.execute();
        listechannel.setOnItemClickListener((ChannelMainActivity) getActivity());
    }



    @Override
    public void OnDownloadComplete(String result, int requestcode) {
        Gson gson = new Gson();
        channels=gson.fromJson(result, Channels.class);
        listechannel.setAdapter(new ChannelListArrayAdapter(getActivity().getApplicationContext(), R.layout.channellayout, channels.getChannels()));

    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(), ChannelConvActivity.class);
        String name = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
        myIntent.putExtra("id", this.channels.getChannels().get(position).getChannelID());
        myIntent.putExtra("name", name);
        startActivity(myIntent);
    }*/
}
