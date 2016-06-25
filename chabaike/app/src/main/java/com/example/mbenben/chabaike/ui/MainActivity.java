package com.example.mbenben.chabaike.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.mbenben.chabaike.R;

public class MainActivity extends Activity {
    private boolean isFirst;
    private SharedPreferences sharedPreferences;
    private boolean ifFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("firstopen", Context.MODE_PRIVATE);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ifFirst=sharedPreferences.getBoolean("ifFirst",true);
                Intent intent = new Intent();
               if(ifFirst){
                   intent.setClass(MainActivity.this,AdvActivity.class);
                   startActivity(intent);
                   finish();
               }else{

               }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
