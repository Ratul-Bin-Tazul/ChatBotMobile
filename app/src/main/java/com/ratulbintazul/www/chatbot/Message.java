package com.ratulbintazul.www.chatbot;

/**
 * Created by SAMSUNG on 9/13/2017.
 */

public class Message {
    String ques,ans;
    public Message() {
    }

    public Message(String ans, String ques) {
        this.ans = ans;
        this.ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }
}
