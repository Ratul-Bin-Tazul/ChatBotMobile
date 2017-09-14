package com.ratulbintazul.www.chatbot;

/**
 * Created by SAMSUNG on 9/13/2017.
 */

public class StringRefiner {

    private String q="";
    public static String refineString(String s) {
        String q="";
        String r="";
        q=s;
        q=q.trim(); //trimming
        q=q.toLowerCase(); //converting everything to lowercase for consistency
        for(int i=0;i<q.length();i++) { //getting rid of exclamation
            char c=q.charAt(i);
            switch(c) {
                case '!' :
                    break;
                case '?' :
                    break;
                case ',' :
                    break;
                case '.' :
                    break;
                case '"' :
                    break;
                case ':' :
                    break;
                case ' ' :
                    break;
                default :
                    r+=c;
            }
        }

        return r;//refined string without any space,sign or anything
    }

}
