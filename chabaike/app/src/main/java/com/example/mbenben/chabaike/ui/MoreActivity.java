package com.example.mbenben.chabaike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.chabaike.R;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

    }

    public void onCollection(View view) {
        Intent intent = new Intent(this,CollectionActivity.class);
        startActivity(intent);
    }
}
