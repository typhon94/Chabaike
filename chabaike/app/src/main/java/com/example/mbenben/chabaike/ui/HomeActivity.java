package com.example.mbenben.chabaike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.mbenben.chabaike.R;
import com.example.mbenben.chabaike.beans.TabInfo;
import com.example.mbenben.chabaike.fragment.Content_Fragment;

import cn.sharesdk.framework.ShareSDK;

public class HomeActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Home_adapter home_adapter;
    private TabInfo[] tabInfos = {
             new TabInfo("头条",0)
            ,new TabInfo("百科",1)
            ,new TabInfo("资讯",2)
            ,new TabInfo("经营",3)
            ,new TabInfo("数据",4)

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      //ShareSDK.initSDK(this);
        initTag();
        initView();
        //
        /*String url = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=0&rows=20&method=news.getSlideshow";

        StringRequest request = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onResonse(final String response) {
                Log.i("data_download", response);
                System.out.print(response);


            }
        });
        HttpHelper.addRequest(request);*/


    }

    private void initTag() {

    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.home_tablayout);
        viewPager = (ViewPager) findViewById(R.id.home_vp);
        home_adapter = new Home_adapter(getSupportFragmentManager());
        viewPager.setAdapter(home_adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onHome_Set(View view) {
        Intent intent = new Intent(this,MoreActivity.class);
        startActivity(intent);
    }

    class Home_adapter extends FragmentStatePagerAdapter{


        public Home_adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Content_Fragment content_fragment = new Content_Fragment();
            Bundle bundle  = new Bundle();
            bundle.putInt("id",tabInfos[position].tab_id);
            content_fragment.setArguments(bundle);
            return content_fragment;
        }

        @Override
        public int getCount() {
            return tabInfos.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabInfos[position].tab_name;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
