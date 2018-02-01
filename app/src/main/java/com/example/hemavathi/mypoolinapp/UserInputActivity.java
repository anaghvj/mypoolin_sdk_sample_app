package com.example.hemavathi.mypoolinapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserInputActivity extends AppCompatActivity {
    private static final String TAG = "UserInputActivity";
    public Button next;
    public EditText P2PID,P2PKEY,P2MID,P2MKEY,GROUPID,GROUPKEY;
    public CheckBox P2P,P2M,GROUP;
    public String CHECK_P2P = "P2P";
    public String CHECK_P2M = "P2M";
    public String CHECK_GROUP = "GROUP";
    public String CHECK_All = "All";
    public AccessStringInfoData accessStringInfoData = new AccessStringInfoData();
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        activity = this;
        next = findViewById(R.id.next);
        P2PID = findViewById(R.id.edit1);
        P2PKEY = findViewById(R.id.edit2);
        P2MID = findViewById(R.id.edit3);
        P2MKEY = findViewById(R.id.edit4);
        GROUPID = findViewById(R.id.edit5);
        GROUPKEY = findViewById(R.id.edit6);
        P2P = findViewById(R.id.check_P2P);
        P2M = findViewById(R.id.check_P2M);
        GROUP = findViewById(R.id.check_GROUP);

        P2P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"test P2P");
               if( P2P.isChecked()){
                   Log.d(TAG,"checked");
                   accessStringInfoData.setType(CHECK_P2P);
               }else{
                   Log.d(TAG,"Unchecked");
                   P2PID.setText("");
                   P2PKEY.setText("");
                }
            }
        });
        P2M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"test P2M");
                if(P2M.isChecked()){
                accessStringInfoData.setType(CHECK_P2M);
                }else{
                    Log.d(TAG,"Unchecked");
                    P2MID.setText("");
                    P2MKEY.setText("");
                }
            }
        });
        GROUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"test GROUP");
                if(GROUP.isChecked()){
                    accessStringInfoData.setType(CHECK_GROUP);
                }else{
                    Log.d(TAG,"Unchecked");
                    GROUPID.setText("");
                    GROUPKEY.setText("");
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callNext();
            }
        });

    }
    private void callNext(){
        if(P2M.isChecked() && P2P.isChecked() && GROUP.isChecked()){
            Log.d(TAG,"test here");
            accessStringInfoData.setType("");
            accessStringInfoData.setSelectAll(CHECK_All);
        }
        accessStringInfoData.setP2P_ID(P2PID.getText().toString());
        accessStringInfoData.setP2P_KEY(P2PKEY.getText().toString());
        accessStringInfoData.setP2M_ID(P2MID.getText().toString());
        accessStringInfoData.setP2M_KEY(P2MID.getText().toString());
        accessStringInfoData.setGROUP_ID(GROUPID.getText().toString());
        accessStringInfoData.setGROUP_KEY(GROUPKEY.getText().toString());
        Intent start = new Intent(activity,MainActivity.class);
        start.putExtra("accessStringInfoData",accessStringInfoData);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
        P2P.setChecked(false);
        P2M.setChecked(false);
        GROUP.setChecked(false);

    }
}
