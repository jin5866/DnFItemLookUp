package com.jin.dnfitemlookup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SSA on 2017-12-18.
 */

public class EquipmentAdapter extends BaseAdapter {

    private ArrayList<Equipment> itemList = new ArrayList<Equipment>();

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
            view = inflater.inflate(R.layout.equipmentitem,parent,false);
        }

        // 안에 내용물
        ImageView iconImageView = (ImageView) view.findViewById(R.id.itemImage);
        TextView itemName = (TextView) view.findViewById(R.id.itemName);
        TextView itemInfo = (TextView) view.findViewById(R.id.equipInfo);

        Equipment equipment = itemList.get(i);

        iconImageView.setImageResource(equipment.getIcon());
        itemName.setText(equipment.getItemName());
        itemInfo.setText(equipment.getItemInfo());

        Log.e("error",equipment.getItemInfo());

        return view;
    }

    public  void addItem(ArrayList<Equipment> list)
    {
        for(int i =0;i<list.size();i++)
        {
            itemList.add(list.get(i));
        }
    }
}
