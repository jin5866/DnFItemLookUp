package com.jin.dnfitemlookup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by SSA on 2017-12-14.
 */

public class ItemLookUp extends Activity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlookup);



        t = (TextView) findViewById(R.id.textView);
        t.setText("준비중");



        new Thread() {
            public void run(){
                String searchCharJson = DnFapi.GetInstance().getSearchCharJson();

                Bundle bun = new Bundle();
                bun.putString("JSON",searchCharJson);
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }
        }.start();


    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String html = bun.getString("JSON");
            t.setText(html);
        }
    };
}