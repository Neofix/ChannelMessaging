package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

/**
 * Created by damien on 27/01/17.
 */

public class ChannelConvActivity extends Activity implements OnDownloadListener, View.OnClickListener{
    private int channelid;
    private String channelname;
    private String accesstoken;
    private Handler handler;
    private Button buttonEnvoyer;
    private EditText messageAenvoyer;
    private ListView listemessage;
    private Messages reslisteMessage;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channellayout);
        channelid= getIntent().getIntExtra("id",0);
        channelname= getIntent().getStringExtra("name");
        this.setTitle(channelname);

        listemessage = (ListView) findViewById(R.id.listViewMessages);
        messageAenvoyer = (EditText) findViewById(R.id.editTextmessage);
        buttonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);
        buttonEnvoyer.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        accesstoken = settings.getString("accesstoken", null);


        handler = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> envoiAccess = new HashMap<>();
                envoiAccess.put("accesstoken", accesstoken);
                envoiAccess.put("channelid", String.valueOf(channelid));
                Downloader d = new Downloader(getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=getmessages",envoiAccess,0);
                d.setOnDownloadComplete(ChannelConvActivity.this);
                d.execute();
                handler.postDelayed(this,1000);
            }
        };

        handler.postDelayed(r,1000);
    }


    @Override
    public void OnDownloadComplete(String result, int requestcode) {

        if(requestcode == 1)
        {
            Gson gson = new Gson();
            Retour retour = gson.fromJson(result, Retour.class);
            if(retour.getCode()==200)
                Toast.makeText(getApplicationContext(),"Message envoyé", Toast.LENGTH_SHORT).show();
            messageAenvoyer.setText("");

        }
        else {
            Gson gson = new Gson();
            reslisteMessage = gson.fromJson(result, Messages.class);
            listemessage.setAdapter(new ChannelAdapter(getApplicationContext(), R.layout.channellayout, reslisteMessage.getMessages()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonEnvoyer)
        {
            HashMap<String,String> envoimsg = new HashMap<>();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            accesstoken = settings.getString("accesstoken", null);
            String message = messageAenvoyer.getText().toString();

            envoimsg.put("accesstoken", accesstoken);
            envoimsg.put("channelid", String.valueOf(channelid));
            envoimsg.put("message", message);
            Downloader d = new Downloader(getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=sendmessage",envoimsg,1);
            d.setOnDownloadComplete(ChannelConvActivity.this);
            d.execute();
        }
        else if (v.getId()==R.id.buttonPhoto)
        {
            File f = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/toto.jpg");
            // Android a depuis Android Nougat besoin d'un provider pour donner l'accès à un répertoire pour une autre app, cf : http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Création de l’appel à l’application appareil photo pour récupérer une image
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //Emplacement de l’image stockée
            startActivityForResult(intent, 1);
        }
    }
}
