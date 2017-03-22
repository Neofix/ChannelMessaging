package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fernandes.damien.channelmessaging.fragments.ChannelConvFragment;

/**
 * Created by damien on 27/01/17.
 */

public class ChannelConvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channellayout);

        ChannelConvFragment conv = (ChannelConvFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentConvChannel_ID);
        int id  = getIntent().getIntExtra("id", 0);
        conv.channelid = id;
    }


}
