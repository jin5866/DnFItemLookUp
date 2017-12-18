package com.jin.dnfitemlookup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SSA on 2017-12-15.
 */

public class CharacterList extends Activity {

    ArrayList<CharInfoItem> itemList;

    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterlist);



        //어뎁터
        adapter = new MyAdapter();

        //아아..
        listView = (ListView) findViewById(R.id.charList);
        listView.setAdapter(adapter);


        //보이는 리스트에 캐릭터 정보 추가 --수정필요.

        new Thread() {
            public void run(){
                //검색한 캐릭터 JSON
                String searchCharJson = DnFapi.GetInstance().getSearchCharJson();

                //정보를 헨들러에 넘겨줌
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

            itemList =  DnFapi.GetInstance().getSearchCharList(html);

            adapter.addItem(itemList);

            //Log.e("error","aaaaaa");

            //캐릭터 정보 눌렀을떄
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    CharInfoItem item =  (CharInfoItem) adapterView.getAdapter().getItem(position);
                    DnFapi.GetInstance().setCharacterId(item.id );

                    Intent intent = new Intent(CharacterList.this,CharEquipmentLookUp.class);
                    startActivity(intent);
                }
            });

            listView.requestLayout();
        }
    };
}
