package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadListener {
    private EditText EditNom;
    private EditText EditPwd;
    private Button ButtonValider;

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
        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
    }
}