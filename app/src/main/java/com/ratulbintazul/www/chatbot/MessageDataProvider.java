package com.ratulbintazul.www.chatbot;

/**
 * Created by SAMSUNG on 9/12/2017.
 */

public class MessageDataProvider {
    public String message;

    public MessageDataProvider(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
