package com.fernandes.damien.channelmessaging.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fernandes.damien.channelmessaging.ChannelAdapter;
import com.fernandes.damien.channelmessaging.ChannelConvActivity;
import com.fernandes.damien.channelmessaging.Downloader;
import com.fernandes.damien.channelmessaging.Messages;
import com.fernandes.damien.channelmessaging.OnDownloadListener;
import com.fernandes.damien.channelmessaging.R;
import com.fernandes.damien.channelmessaging.Retour;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by fernandd on 09/03/2017.
 */
public class ChannelConvFragment extends Fragment implements View.OnClickListener, OnDownloadListener {
    private int channelid;
    private String channelname;
    private String accesstoken;
    private Handler handler;
    private Button buttonEnvoyer;
    private EditText messageAenvoyer;
    private ListView listemessage;
    private Messages reslisteMessage;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_channelconv, container);
        listemessage = (ListView) v.findViewById(R.id.listViewMessages);
        messageAenvoyer = (EditText) v.findViewById(R.id.editTextmessage);
        buttonEnvoyer = (Button) v.findViewById(R.id.buttonEnvoyer);
        buttonEnvoyer.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        accesstoken = settings.getString("accesstoken", null);


        handler = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> envoiAccess = new HashMap<>();
                envoiAccess.put("accesstoken", accesstoken);
                envoiAccess.put("channelid", String.valueOf(channelid));
                Downloader d = new Downloader(getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getmessages",envoiAccess,0);
                d.setOnDownloadComplete(ChannelConvFragment.this);
                d.execute();
                handler.postDelayed(this,1000);
            }
        };

        handler.postDelayed(r,1000);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonEnvoyer)
        {
            HashMap<String,String> envoimsg = new HashMap<>();
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            accesstoken = settings.getString("accesstoken", null);
            String message = messageAenvoyer.getText().toString();

            envoimsg.put("accesstoken", accesstoken);
            envoimsg.put("channelid", String.valueOf(channelid));
            envoimsg.put("message", message);
            Downloader d = new Downloader(getActivity(), "http://www.raphaelbischof.fr/messaging/?function=sendmessage",envoimsg,1);
            d.setOnDownloadComplete(ChannelConvFragment.this);
            d.execute();
        }
    }

    @Override
    public void OnDownloadComplete(String result, int requestcode) {
        if(requestcode == 1)
        {
            Gson gson = new Gson();
            Retour retour = gson.fromJson(result, Retour.class);
            if(retour.getCode()==200)
                Toast.makeText(getActivity(),"Message envoyé", Toast.LENGTH_SHORT).show();
            messageAenvoyer.setText("");

        }
        else {
            Gson gson = new Gson();
            reslisteMessage = gson.fromJson(result, Messages.class);
            listemessage.setAdapter(new ChannelAdapter(getActivity(), R.layout.channellayout, reslisteMessage.getMessages()));
        }
    }
}
