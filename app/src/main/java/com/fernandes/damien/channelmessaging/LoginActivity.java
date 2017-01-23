package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadListener {
    private EditText EditNom;
    private EditText EditPwd;
    private Button ButtonValider;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditNom = (EditText) findViewById(R.id.editTextName);
        EditPwd = (EditText) findViewById(R.id.editTextPwd);

        ButtonValider = (Button) findViewById(R.id.buttonValider);
        ButtonValider.setOnClickListener(this) ;




    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonValider) {
            HashMap<String,String> login = new HashMap<>();
            login.put("username", EditNom.getText().toString());
            login.put("password", EditPwd.getText().toString());
            Downloader d = new Downloader(getApplicationContext(), login);
            d.setOnDownloadComplete(this);
            d.execute();

        }
    }

    public void OnDownloadComplete(String result)
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = settings.edit();
        Retour leRetour = gson.fromJson(result, Retour.class);
        String accesstoken = leRetour.getAccesstoken();
        String message="";
        if(leRetour.getCode()==200)
            message="Connexion réussie !";
        else
            message="Erreur : "+ leRetour.getResponse();
        editor.putString("accesstoken",accesstoken);
        editor.commit();
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }
}