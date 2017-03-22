package com.fernandes.damien.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadListener {
    private EditText EditNom;
    private EditText EditPwd;
    private ImageView mIvLogo;
    private Button ButtonValider;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Handler mHandlerTada;
    private int mShortDelay;
    private Handler mHandlerSlideLeft = new Handler();




    @Override
    protected void onResume() {
        super.onResume();
        mHandlerTada = new Handler(); // android.os.handler
        mShortDelay = 4000; //milliseconds


        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(mIvLogo);
                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);

        EditNom.setText("dfern");
        EditPwd.setText("damienfernandes");
        //ButtonValider.performClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditNom = (EditText) findViewById(R.id.etPassword);
        EditPwd = (EditText) findViewById(R.id.etIdentifiant);

        mIvLogo = (ImageView) findViewById(R.id.imageViewlogo);

        ButtonValider = (Button) findViewById(R.id.buttonValider);
        ButtonValider.setOnClickListener(this) ;

        EditNom.setText("dfern");
        EditPwd.setText("damienfernandes");
        ButtonValider.performClick();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.buttonValider) {
            HashMap<String,String> login = new HashMap<>();
            login.put("username", EditNom.getText().toString());
            login.put("password", EditPwd.getText().toString());
            Downloader d = new Downloader(getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=connect", login, 1);
            d.setOnDownloadComplete(this);
            d.execute();

        }
    }

    public void OnDownloadComplete(String result, int requestcode) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        boolean isConnected = false;
        SharedPreferences.Editor editor = settings.edit();
        Retour leRetour = gson.fromJson(result, Retour.class);
        String accesstoken = leRetour.getAccesstoken();
        String message = "";
        if (leRetour.getCode() == 200) {
            message = "Connexion réussie !";
            isConnected = true;
        }
        else {
            message = "Erreur : " + leRetour.getResponse();
            isConnected = false;
        }
        editor.putString("accesstoken",accesstoken);
        editor.commit();
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
        if(isConnected)
        {

            mHandlerSlideLeft.postDelayed(new Runnable(){
                public void run(){
                    startChannelListActivity();

                }
            }, 500);

            Animation animSlideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left);
            ButtonValider.startAnimation(animSlideLeft);


        }
    }

    public void startChannelListActivity(){
        Intent myIntent = new Intent(getApplicationContext(), ChannelMainActivity.class);
        startActivity(myIntent);
    }
}