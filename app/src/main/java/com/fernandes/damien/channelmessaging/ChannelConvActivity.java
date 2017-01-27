package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by damien on 27/01/17.
 */

public class ChannelConvActivity extends Activity {
    private int channelid;
    private String channelname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channellayout);
        channelid= getIntent().getIntExtra("id",0);
        channelname= getIntent().getStringExtra("name");
        this.setTitle(channelname);
    }
}
