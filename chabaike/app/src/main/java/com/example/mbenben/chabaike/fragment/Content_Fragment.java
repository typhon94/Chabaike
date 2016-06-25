package com.example.mbenben.chabaike.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mbenben.chabaike.R;
import com.example.mbenben.chabaike.beans.JsonInfo;
import com.example.mbenben.chabaike.ui.ShowActivity;
import com.example.mbenben.httplib.BitmapRequest;
import com.example.mbenben.httplib.HttpHelper;
import com.example.mbenben.httplib.Request;
import com.example.mbenben.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by MBENBEN on 2016/6/22.
 */
public class Content_Fragment  extends Fragment{

    private PtrClassicFrameLayout mRefreshView;
    private String[] urls;
    private TextView textView;
    int id;
    private ListView listview;
    private MyLvAda adapter;
    private List<JsonInfo> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getContext(),R.layout.home_fragment,null);
        urls=new String[]{"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=1&rows=20&method=news.getHeadlines"
                ,"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=1&rows=20&method=news.getListByType&type=16",
                "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=1&rows=20&method=news.getListByType&type=52",
                "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=1&rows=20&method=news.getListByType&type=53",
                "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&page=1&rows=20&method=news.getListByType&type=54"
        };
        initReview(view);
        listview = (ListView) view.findViewById(R.id.home_frg_lv);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowActivity.class);
                intent.putExtra("url",list.get(position).getJs_id());
                intent.putExtra("title",list.get(position).getJs_title());
                startActivity(intent);
            }
        });
       // textView = (TextView) view.findViewById(R.id.home_frg_tv);
        id =getArguments().getInt("id");
       // textView.setText("我是Fragment" + id);
        getNetData();
        //adapter = new MyLvAda();
       // listview.setAdapter(adapter);
       // adapter.notifyDataSetChanged();
        for(int i=0;i<list.size();i++){
            System.out.println(list.toString());
        }
        return view;
    }

    private void initReview(View view) {
        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mRefreshView.setResistance(1.7f);
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        // default is false
        mRefreshView.setPullToRefresh(true);
        // default is true
        mRefreshView.setKeepHeaderWhenRefresh(true);
        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getNetData();
            }
        });
    }

    public void getNetData() {
        String url = urls[id];

        StringRequest request = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onResonse( String response) {
              /*  Log.i("data_download",response);
                System.out.print(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(response);
                    }
                });*/
                Log.i("responsedata",response+"===============");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    parseJson2List(jsonObject,list);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("onResponse", "==========Json转换异常");
                }


                if(adapter==null) {
                    adapter = new MyLvAda();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // textView.setText(response);
                                adapter = new MyLvAda();
                                listview.setAdapter(adapter);
                        }
                    });
                }
                else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
               // adapter.notifyDataSetChanged();
                Log.i("onResponse","=========="+list.get(1).getJs_title());
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(request);
    }


    class MyLvAda extends BaseAdapter{


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = View.inflate(getContext(),R.layout.item_home_list,null);
                viewHolder = new ViewHolder();
                viewHolder.imageview = (ImageView) convertView.findViewById(R.id.item_image);
                viewHolder.textview1 = (TextView) convertView.findViewById(R.id.title);
                viewHolder.textview2 = (TextView) convertView.findViewById(R.id.description);
                viewHolder.textview3 = (TextView) convertView.findViewById(R.id.source);
                viewHolder.textview4 = (TextView) convertView.findViewById(R.id.author);
                viewHolder.textview5 = (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.imageview.setImageResource(R.mipmap.ic_launcher);
            viewHolder.textview1.setText(list.get(position).getJs_title());
            viewHolder.textview2.setText(list.get(position).getDescription());
            viewHolder.textview3.setText(list.get(position).getSource());
            viewHolder.textview4.setText(list.get(position).getNickname());
            viewHolder.textview5.setText(list.get(position).getCreate_timme());
            loadImageView(viewHolder.imageview,list.get(position).getWap_thumb());
            return convertView;
        }


    }
    public void loadImageView(final ImageView view , final String url) {
        view.setTag(url);
        Log.i("textbitmap",url);
        BitmapRequest request = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onResonse(final Bitmap response) {
                if(view!=null&&response!=null&&((String)(view.getTag())).equals(url)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(response);
                        }
                    });
                }

            }
        });
        HttpHelper.addRequest(request);

    }
     class ViewHolder{
         private ImageView imageview;
         private TextView textview1,textview2,textview3,textview4,textview5;


     }

    public void parseJson2List(JSONObject jsonObject, List<JsonInfo> list){
        if(list.size()!=0){
            list.clear();
        }
        try {
            //JSONObject json = new JSONObject(jsonstr);
            JSONArray Ja =jsonObject.getJSONArray("data");
            for(int i=0;i< Ja.length();i++){
                JsonInfo jsonInfo = new JsonInfo();
                JSONObject json1=Ja.getJSONObject(i);
                jsonInfo.setJs_id(json1.getString("id"));
                jsonInfo.setCreate_timme(json1.getString("create_time"));
                jsonInfo.setDescription(json1.getString("description"));
                jsonInfo.setJs_title(json1.getString("title"));
                jsonInfo.setNickname(json1.getString("nickname"));
                jsonInfo.setWap_thumb(json1.getString("wap_thumb"));
                jsonInfo.setSource(json1.getString("source"));
                list.add(jsonInfo);
                if(jsonInfo==null){
                    Log.i("rrttyyii","jsoninfo 为空");
                    System.out.print("jsoninfo 为空");
                }
                Log.i("rrttyyii",jsonInfo.toString());
                System.out.print(jsonInfo.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("rrttyyii", "youyichang");
        }

    }
}
