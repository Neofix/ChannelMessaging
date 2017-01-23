package com.fernandes.damien.channelmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by damien on 23/01/17.
 */

public class ChannelListActivity extends Activity{
    ListView listechannel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channellist);

        listechannel = (ListView) findViewById(R.id.listViewChannel);
        
    }
}
