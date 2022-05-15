package com.group22.news_management.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler {

    public static String getThumbnailSRC(String cdata){
        Pattern pattern = Pattern.compile("src=\".*\" >");
        Matcher matcher = pattern.matcher(cdata);
        if(matcher.find()){
            String str = matcher.group();
            int firstQuotesIndex = str.indexOf("\"");
            int lastQuotesIndex = str.lastIndexOf("\"");
            String result = str.substring(firstQuotesIndex + 1, lastQuotesIndex);
            return result;
        }
        return "";
    }
}
