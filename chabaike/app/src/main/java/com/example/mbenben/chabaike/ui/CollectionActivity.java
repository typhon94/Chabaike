package com.example.mbenben.chabaike.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mbenben.chabaike.DbHelper.DBUtils;
import com.example.mbenben.chabaike.R;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/6/23.
 */
public class CollectionActivity extends AdvActivity {
    private ListView listView;
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<String> list_id = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collec);
        //获得数据库的数据
        initdata();
        listView = (ListView) findViewById(R.id.col_lv);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        //listView.setAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, ShowActivity.class);
                intent.putExtra("url",list_id.get(position));
                intent.putExtra("title",list.get(position));
                startActivity(intent);
            }
        });

    }

    private void initdata() {
        DBUtils dbutils = new DBUtils(this);
        Cursor cursor=dbutils.queryAll();
        while(cursor.moveToNext()){

            list.add(cursor.getString(cursor.getColumnIndex("title")));
            list_id.add(cursor.getString(cursor.getColumnIndex("url")));
        }
    }
}
