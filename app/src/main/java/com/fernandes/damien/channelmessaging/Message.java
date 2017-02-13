package com.fernandes.damien.channelmessaging;

import java.util.Date;

/**
 * Created by damien on 23/01/17.
 */

public class Message {
    private int userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
