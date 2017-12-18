package com.jin.dnfitemlookup;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    //캐릭터 검색
    String searchCharFormat = "/df/servers/%s/characters?characterName=%s&limit=50&wordType=%s&apikey=%s";//서버,닉네임,검색조건,api키

    //캐릭터 장비 검색
    String searchCharEquipment = "/df/servers/%s/characters/%s/equip/equipment?apikey=%s";//서버,ID,api키

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
    public void setCharacterId(String id)
    {
        characterId = id;
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


    //주소로 페이지 소스 파싱
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


    /*
     * 이름으로 캐릭터 검색하는 기능
     *
     */
    String getSearchCharUrl() {
        return apiURL+String.format(searchCharFormat,serverName,characterName,wordTypes[searchOption],apiKey);
    }
    public String getSearchCharJson()
    {
        return getHttpHTML(getSearchCharUrl());
    }
    public ArrayList<CharInfoItem> getSearchCharList() {
        return getSearchCharList(getSearchCharJson());
    }
    public ArrayList<CharInfoItem> getSearchCharList(String json) {
        ArrayList<CharInfoItem> result = new ArrayList<CharInfoItem>();

        try {
            JSONObject jo = new JSONObject(json);
            JSONArray jsonArray = jo.getJSONArray("rows");
            // = new JSONArray(json);

            for(int i=0;i<jsonArray.length();i++)
            {
                //Log.e("error",""+i);

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                String name = jsonObject.getString("characterName" );
                String id =  jsonObject.getString("characterId");
                String job = jsonObject.getString("jobName");
                int level = jsonObject.getInt("level");


                result.add(new CharInfoItem(R.drawable.ic_account_circle,name,job,level,id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e("error",e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e("error",e.toString());
        }

        return result;
    }


    /*
     * 캐릭터 ID 로 장착 장비 검색
     *
     */
    String getSearchCharEquipmentURL(){
        return apiURL+String.format(searchCharEquipment,serverName,characterId,apiKey);
    }
    public String getSearchCharEquipmentJson(){
        return getHttpHTML(getSearchCharEquipmentURL());
    }
    public String getSearchCharEquipmentJson(String id){
        setCharacterId(id);
        return getSearchCharEquipmentJson();
    }
    public ArrayList<Equipment> getCharEquipmentList(){
        return getCharEquipmentList(getSearchCharEquipmentJson());
    }
    public ArrayList<Equipment> getCharEquipmentList(String json){
        ArrayList<Equipment> result = new ArrayList<Equipment>();

        try {
            JSONObject jo = new JSONObject(json);
            JSONArray jsonArray = jo.getJSONArray("equipment");
            // = new JSONArray(json);

            for(int i=0;i<jsonArray.length();i++)
            {
                //Log.e("error",""+i);

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                String slotId = jsonObject.getString("slotId");
                String itemId = jsonObject.getString("itemId");
                String itemName = jsonObject.getString("itemName");
                String itemRarity = jsonObject.getString("itemRarity");
                String setItemName = jsonObject.getString("setItemName");
                int reinforce = jsonObject.getInt("reinforce");
                int refine = jsonObject.getInt("refine");
                String amplificationName = jsonObject.getString("amplificationName");


                result.add(new Equipment(slotId,itemId,itemName,itemRarity,setItemName,reinforce,refine,amplificationName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e("error",e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e("error",e.toString());
        }

        return result;
    }


}
