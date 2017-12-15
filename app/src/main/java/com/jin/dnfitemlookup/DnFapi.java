package com.jin.dnfitemlookup;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SSA on 2017-12-14.
 */

public class DnFapi extends Activity {

    String apiURL = "https://api.neople.co.kr";
    String apiKey = "5j7OCSLo8bXCI2p44DKIeQhowxOpASWk";

    //검색 조건
    String[] wordTypes = {"match","full"};

    int searchOption;

    String serverName = "";

    String characterName = "";
    String characterId = "";

    String searchCharFormat = "/df/servers/%s/characters?characterName=%s&limit=50&wordType=%s&apikey=%s";//서버,닉네임,검색조건,api키


    public static DnFapi instance = null;

    public static DnFapi GetInstance()
    {
        if(instance == null)
        {
            instance = new DnFapi();
        }
        return instance;
    }

    private DnFapi() {

    }

    public void setCharName(String name)
    {
        this.characterName = encodeURIComponent(name);
    }
    public void setServerName(int i){
        Resources res = getResources();
        serverName = res.getStringArray(R.array.server_en)[i];
        return;
    }
    public void setServerName(String i){
        serverName = i;
        return;
    }
    public  void setSearchOption(boolean option) {
        if(option)
        {
            searchOption = 1;
        }
        else
        {
            searchOption = 0;
        }
    }

    public static String encodeURIComponent(String component)   {
        String result = null;

        try {
            result = URLEncoder.encode(component, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            result = component;
        }
            return result;
    }


    String getSearchCharUrl() {
        return apiURL+String.format(searchCharFormat,serverName,characterName,wordTypes[searchOption],apiKey);
    }
    String getHttpHTML(String urlToRead) {
        String result = "";

        URL url =null;
        HttpsURLConnection http = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try{
            url = new URL(urlToRead);
            http = (HttpsURLConnection) url.openConnection();
            http.setConnectTimeout(3*1000);
            http.setReadTimeout(3*1000);

            http.setDoOutput(false);

            isr = new InputStreamReader(http.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                result += str + "\n";
            }

        }catch(Exception e){
            Log.e("Exception", e.toString());
            result += e.toString();
        }finally{
            if(http != null){
                try{http.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }

        return result;

    }
    public String getSearchCharJson()
    {
        return getHttpHTML(getSearchCharUrl());
    }
}
