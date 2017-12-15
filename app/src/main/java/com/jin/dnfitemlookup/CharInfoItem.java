package com.jin.dnfitemlookup;

/**
 * Created by SSA on 2017-12-15.
 */

public class CharInfoItem {
    private int icon;
    String name;
    String id;
    String job;
    int level;

    CharInfoItem()
    {

    }
    CharInfoItem(int icon,String name,String job,int level)
    {
        this.icon = icon;
        this.name = name;
        this.job = job;
        this.level = level;
    }
    CharInfoItem(int icon,String name,String job,int level,String id)
    {
        this.icon = icon;
        this.name = name;
        this.job = job;
        this.level = level;
        this.id = id;
    }

    int getIcon(){return icon;}
    String getName(){return name;}
    String getInfo(){return "레벨 : "+level +"    전직 : "+job;}
}
