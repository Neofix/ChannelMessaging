package com.fernandes.damien.channelmessaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fernandes.damien.channelmessaging.fragments.ChannelConvFragment;
import com.fernandes.damien.channelmessaging.fragments.ChannelListFragment;

/**
 * Created by damien on 23/01/17.
 */

public class ChannelMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listechannel ;
    private String accesstoken;
    Channels channels = new Channels();


    public static final String PREFS_NAME = "MyPrefsFile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channellist);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent myIntent = new Intent(getApplicationContext(), ChannelConvActivity.class);
        String name = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
        myIntent.putExtra("id", channels.getChannels().get(position));
        myIntent.putExtra("name", name);
        startActivity(myIntent);*/

        ChannelListFragment listChannel = (ChannelListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentListChannel_ID);
        ChannelConvFragment convChannel = (ChannelConvFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentConvChannel_ID);

        if(convChannel==null || !convChannel.isInLayout())
        {
            Intent i = new Intent(getApplicationContext(), ChannelConvActivity.class);
            i.putExtra("id", listChannel.channels.getChannels().get(position).getChannelID());
            i.putExtra("name", "YOLO");
            startActivity(i);
        }
        else
        {
            convChannel.fillView();
        }

        
    }
}
