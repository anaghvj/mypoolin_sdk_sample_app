package com.example.hemavathi.mypoolinapp;

import android.app.Application;

import com.yesbank.common.UiUtil;
import com.yesbank.common.data.preferences.SharedPreferenceHelper;

/**
 * Created by hemavathi on 22/01/18.
 */

public class MyMpoolinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceHelper.initSharedPreferenceHelper(this);
        UiUtil.initLogos();
    }
}
