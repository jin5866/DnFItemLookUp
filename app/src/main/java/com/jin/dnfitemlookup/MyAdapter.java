package com.jin.dnfitemlookup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SSA on 2017-12-15.
 */

public class MyAdapter extends BaseAdapter {

    private ArrayList<CharInfoItem> itemList = new ArrayList<CharInfoItem>();


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final int pos = i;
        final Context context = parent.getContext();

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.charinfoitem,parent,false);
        }

        ImageView iconImageView = (ImageView) view.findViewById(R.id.charImage);
        TextView name = (TextView) view.findViewById(R.id.charName);
        TextView charInfo = (TextView) view.findViewById(R.id.charInfo);

        CharInfoItem charInfoItem = itemList.get(i);

        iconImageView.setImageResource(charInfoItem.getIcon());
        name.setText(charInfoItem.getName());
        charInfo.setText(charInfoItem.getInfo());

        return view;
    }

    public void addItem(int icon, String name, String job, int level) {
        itemList.add(new CharInfoItem(icon,name,job,level));
    }
}
