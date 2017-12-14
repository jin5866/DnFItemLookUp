package com.jin.dnfitemlookup;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainPage extends AppCompatActivity {

    EditText e;

    int serverPos;
    String charName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //스피너
        Spinner s = (Spinner) findViewById(R.id.serverName);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.server,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView,View view,int position,long id){
                        int serverPos = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //int serverPos = 0;
                    }

                }
        );

        //입력값
        e = (EditText) findViewById(R.id.charName);
    }



    public void onClickLookUp(View v) {
        charName = e.getText().toString();
        DnFapi.GetInstance().setCharName(charName);
        Resources res = getResources();
        String server = res.getStringArray(R.array.server_en)[serverPos];
        DnFapi.GetInstance().setServerName(server);
        getResources();
        Intent intent = new Intent(this,ItemLookUp.class);
        startActivity(intent);
    }
}
