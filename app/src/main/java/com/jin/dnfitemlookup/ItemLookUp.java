package com.jin.dnfitemlookup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by SSA on 2017-12-14.
 */

public class ItemLookUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlookup);

        TextView t = (TextView) findViewById(R.id.textView);

        t.setText(DnFapi.GetInstance().getSearchCharJson());
    }
}