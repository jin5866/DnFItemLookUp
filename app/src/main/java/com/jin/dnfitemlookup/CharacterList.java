package com.jin.dnfitemlookup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by SSA on 2017-12-15.
 */

public class CharacterList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterlist);

        ListView listView;
        MyAdapter adapter;

        adapter = new MyAdapter();

        listView = (ListView) findViewById(R.id.charList);
        listView.setAdapter(adapter);

        adapter.addItem(R.drawable.ic_account_circle,"box","box",90);

    }


}
