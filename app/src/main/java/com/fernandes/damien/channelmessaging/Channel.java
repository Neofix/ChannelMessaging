package com.fernandes.damien.channelmessaging;

import java.io.Serializable;

/**
 * Created by damien on 23/01/17.
 */

public class Channel implements Serializable {
    private int channelID;
    private String name;
    private int connectedusers;

    public int getChannelID() {
        return channelID;
    }

    public String getName() {
        return name;
    }

    public int getConnectedusers() {
        return connectedusers;
    }
}
