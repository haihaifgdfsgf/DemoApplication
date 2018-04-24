package com.caohai.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmclockReceive mBroadcastReceiver = new AlarmclockReceive();
        IntentFilter intentFilter = new IntentFilter();

        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(mBroadcastReceiver, intentFilter);
        String str = "{\"id\":\"65f65a8f-6dba-493a-8495-94774f9ebf8b\",\"customerId\":\"5a6bef58-cb3a-4783-9f22-388bdd23851e\",\"authenticationSource\":2,\"authenticationLevel\":1,\"customerName\":\"申献华\",\"customerIdNo\":\"412926196802081534\",\"customerSex\":0,\"customerIdFrontUrl\":\"https://appimgs.95155.com/MobileFileServer/download.do?p\\\\u003d39EDE74569DF4DB53F600AAFD68CF8C05D6003A83E7B6E473CC8A7597069EC82\",\"customerIdBackUrl\":\"https://appimgs.95155.com/MobileFileServer/download.do?p\\\\u003d39EDE74569DF4DB53F600AAFD68CF8C0454F8AFC6E2DF0E8AAE9DD5FF2DC9FC6\",\"customerNationality\":\"汉\",\"customerAddress\":\"河南省内乡县城关镇菊潭大街土产组\",\"customerIdIssue\":\"内乡县公安局\",\"customerIdValidDateStart\":1454256000000,\"authenticationTime\":1514769796123,\"createTime\":1515058825432,\"updateTime\":1515201497681}";

    }

    public void click(View view) {


        Intent intent = new Intent(this, AlarmclockReceive.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                this, 0, intent, 0);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

    }

    public class AlarmclockReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("ceshi", "hahaha");

            Intent intent3 = new Intent();
            ComponentName component = new ComponentName("com.caohai.myapplication", "com.alibaba.android.rimet.biz.home.activity.HomeActivity");
            intent3.setComponent(component);
            startActivity(intent3);
        }

    }
}
