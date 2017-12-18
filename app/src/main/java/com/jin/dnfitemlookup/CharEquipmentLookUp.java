package com.jin.dnfitemlookup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SSA on 2017-12-18.
 */

public class CharEquipmentLookUp extends Activity {

    ArrayList<Equipment> itemList;

    ListView listView;
    EquipmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charequipmentlookup);

        //어뎁터
        adapter = new EquipmentAdapter();

        //어뎁터를 리스트뷰와 연결
        listView = (ListView) findViewById(R.id.itemList);
        listView.setAdapter(adapter);

        new Thread() {
            public void run() {
                String searchCharJson = DnFapi.GetInstance().getSearchCharEquipmentJson();

                Bundle bun = new Bundle();
                bun.putString("JSON",searchCharJson);
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }
        }.start();


    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String html = bun.getString("JSON");

            itemList = DnFapi.GetInstance().getCharEquipmentList(html);

            adapter.addItem(itemList);

            listView.requestLayout();
        }
    };
}
