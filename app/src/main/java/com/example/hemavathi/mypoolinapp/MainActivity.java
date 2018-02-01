package com.example.hemavathi.mypoolinapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public Button P2P_Virtual, P2P_Multi, P2P_Account, P2P_CollectRequest, P2P_UPI, P2M_Pay, Split;
    private Activity activity;
    public static AccessStringInfoData accessStringInfoData;
    public String CHECK_P2P = "P2P";
    public String CHECK_P2M = "P2M";
    public String CHECK_GROUP = "GROUP";
    public String CHECK_All = "All";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        P2P_Virtual = findViewById(R.id.button1);
        P2P_Account = findViewById(R.id.button2);
        P2P_Multi = findViewById(R.id.button3);
        P2P_CollectRequest = findViewById(R.id.button4);
        P2P_UPI = findViewById(R.id.button5);
        P2M_Pay = findViewById(R.id.button6);
        Split = findViewById(R.id.button7);

       Intent intent = getIntent();
        if(intent==null) {
            finish();
            return;
        }
        accessStringInfoData = (AccessStringInfoData) intent.getSerializableExtra("accessStringInfoData");

       if(accessStringInfoData != null) {
           if (accessStringInfoData.getType().equalsIgnoreCase(CHECK_P2P)) {
               P2M_Pay.setVisibility(View.GONE);
               Split.setVisibility(View.GONE);
           } else if (accessStringInfoData.getType().equalsIgnoreCase(CHECK_P2M)) {
               P2P_Virtual.setVisibility(View.GONE);
               P2P_Account.setVisibility(View.GONE);
               P2P_Multi.setVisibility(View.GONE);
               P2P_CollectRequest.setVisibility(View.GONE);
               P2P_UPI.setVisibility(View.GONE);
               Split.setVisibility(View.GONE);
           } else if (accessStringInfoData.getType().equalsIgnoreCase(CHECK_GROUP)) {
               P2P_Virtual.setVisibility(View.GONE);
               P2P_Account.setVisibility(View.GONE);
               P2P_Multi.setVisibility(View.GONE);
               P2P_CollectRequest.setVisibility(View.GONE);
               P2P_UPI.setVisibility(View.GONE);
               P2M_Pay.setVisibility(View.GONE);
           } else if (accessStringInfoData.getSelectAll().equalsIgnoreCase(CHECK_All)) {
               P2P_Virtual.setVisibility(View.VISIBLE);
               P2P_Account.setVisibility(View.VISIBLE);
               P2P_Multi.setVisibility(View.VISIBLE);
               P2P_CollectRequest.setVisibility(View.VISIBLE);
               P2P_UPI.setVisibility(View.VISIBLE);
               P2M_Pay.setVisibility(View.VISIBLE);
               Split.setVisibility(View.VISIBLE);
           }
       }


        P2P_Virtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"P2P_Virtual");
                ShowNext("P2P_Virtual");
            }
        });
        P2P_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("P2P_Account");
            }
        });
        P2P_Multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("Multi_PAY_P2P");
            }
        });
        P2P_CollectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("P2P_CollectRequest");
            }
        });
        P2P_UPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("P2P_UPI");
            }
        });
        P2M_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("P2M_Pay");
            }
        });
        Split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNext("Split");
            }
        });
    }

    private void ShowNext(String inputTpye) {
        Log.d(TAG,"input is :"+inputTpye);
        Intent intent = new Intent(MainActivity.this, InputDetailActivity.class);
        intent.putExtra("inputType", inputTpye);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.finish();
    }
}
