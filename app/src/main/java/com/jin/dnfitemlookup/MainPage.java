package com.jin.dnfitemlookup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_INTERNET = 1;

    EditText e;
    Switch searchOption;

    int serverPos;
    String charName;

    String[] wordTypes = {"단어 일치","단어 포함"};

    String neopleUrl = "http://developers.neople.co.kr";

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
                        serverPos= position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        serverPos = 0;
                    }

                }
        );

        //입력값
        e = (EditText) findViewById(R.id.charName);

        //검색 조건
        searchOption = (Switch) findViewById(R.id.searchOption);

        //처음상태
        searchOption.setText(wordTypes[0]);
        searchOption.setChecked(false);

        //바뀌면 글자 바꾸기
        searchOption.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            searchOption.setText(wordTypes[1]);
                        }
                        else
                        {
                            searchOption.setText(wordTypes[0]);
                        }
                    }
                }
        );
    }



    //조회하기 버튼
    public void onClickLookUp(View v) {

        //권한요구
        //권한요구
        //권한요구
        //권한요구
        //권한요구
        requestNetworkPermission();

        //캐릭터 이름
        charName = e.getText().toString();
        if(charName.length()<=0) {
            //알람 띄우기

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // 제목셋팅
            alertDialogBuilder.setTitle("짧아");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("닉네임이 짧습니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    //취소
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // 다이얼로그를 취소한다
                                    dialog.cancel();
                                }
                            });

            // 다이얼로그 생성
            AlertDialog alertDialog = alertDialogBuilder.create();

            // 다이얼로그 보여주기
            alertDialog.show();

            //다음으로 넘어가지 않기
            return;
        }

        DnFapi.GetInstance().setCharName(charName);

        //서버이름
        Resources res = getResources();
        String server = res.getStringArray(R.array.server_en)[serverPos];
        DnFapi.GetInstance().setServerName(server);

        //검색 옵션
        DnFapi.GetInstance().setSearchOption(searchOption.isChecked());

        Intent intent = new Intent(this,CharacterList.class);
        startActivity(intent);
    }

    //네오플 버튼
    public void onClickNeople(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목셋팅
        alertDialogBuilder.setTitle("네오플");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("네오플api를 사용하여 제작되었습니다.\n확인을 누를시 네오플 사이트로 이동합니다.")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(neopleUrl));
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();

    }
    private void requestNetworkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionResult = checkSelfPermission(Manifest.permission.INTERNET);

            if (permissionResult == PackageManager.PERMISSION_DENIED)
            {
                //권한 없을떄

                if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET))
                {
                    //거부이력 있을때
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainPage.this);

                    dialog.setTitle("권한이 필요합니다.")

                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"인터넷\" 권한이 필요합니다. 계속하시겠습니까?")

                            .setPositiveButton("네", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {



                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        requestPermissions(new String[]{Manifest.permission.INTERNET}, 1000);

                                    }



                                }

                            })

                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(MainPage.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();

                                }
                            }).create().show();


                }
                else
                {
                    //최초 요청
                    requestPermissions(new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
                }
            }
            else
            {
                //권한 있을떄
                //Log.e("a","aaa");
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
