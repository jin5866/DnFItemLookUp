package com.jin.dnfitemlookup;

import android.app.Activity;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by SSA on 2017-12-14.
 */

public class DnFapi extends Activity {

    String apiURL = "https://api.neople.co.kr";
    String apiKey = "5j7OCSLo8bXCI2p44DKIeQhowxOpASWk";

    String serverName = "";

    String characterName = "";
    String characterId = "";

    String searchCharFormat = "/df/servers/%s/charaters?characterName=%s&apikey=%s";//서버,닉네임,api키


    public static DnFapi instance = null;

    public static DnFapi GetInstance()
    {
        if(instance == null)
        {
            instance = new DnFapi();
        }
        return instance;
    }

    private DnFapi()
    {

    }

    public void setCharName(String name)
    {
        this.characterName = encodeURIComponent(name);
    }
    public void setServerName(int i){
        Resources res = getResources();
        serverName = res.getStringArray(R.array.server_en)[i];
        return;
    }public void setServerName(String i){
        serverName = i;
        return;
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


    String getSearchCharUrl()
    {
        return apiURL+String.format(searchCharFormat,serverName,characterName,apiKey);
        //return encodeURIComponent(apiURL+String.format(searchCharFormat,serverName,characterName,apiKey));
    }

    String getHttpHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getSearchCharJson()
    {
        return getHttpHTML(getSearchCharUrl());
    }
}
