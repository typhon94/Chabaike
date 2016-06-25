package com.example.mbenben.chabaike.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.mbenben.chabaike.DbHelper.DBUtils;
import com.example.mbenben.chabaike.R;
import com.example.mbenben.chabaike.helper.JsonHelper;
import com.example.mbenben.chabaike.helper.MyHttpClientHelper;

import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by MBENBEN on 2016/6/23.
 */
public class ShowActivity extends Activity {
    private WebView webView;
    String id;
    String title;
    private final String TAG = "ShowActivity";
    String myurl;
    /** 当前这个页面请求网络得到的值 */
    private Map<String, Object> mapValue;
    private Boolean iscol = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        id=intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        initView();
        ContentTask contentTask =new ContentTask(this);
        contentTask.execute(myurl);
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.show_wv);
         myurl = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id="+id;
        //webView.loadUrl(myurl);
     /*   StringRequest request = new StringRequest(myurl, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onResonse(String response) {
                if(response!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }


        });*/
        //WebSettings webSettings= webView.getSettings(); // webView: 类WebView的实例
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setUseWideViewPort(true);//web1就是你自己定义的窗口对象。
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    //返回按钮
    public void onBack(View view) {
        finish();
    }
    //收藏按钮
    public void onCollection(View view) {
        DBUtils utils = new DBUtils(this);
        Cursor cursor= utils.queryWhere("url=?",new String[]{String.valueOf(myurl)});
        if(cursor.getCount()==0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", myurl);
            contentValues.put("title",title);
            utils.insert(contentValues);
            Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
            iscol = !iscol;
        }else{
            Toast.makeText(this,"已成收藏过了",Toast.LENGTH_SHORT).show();
        }
    }

    public void onSet(View view) {
        Intent intent = new Intent(this,MoreActivity.class);
        startActivity(intent);

    }
    private void showShare() {
       ShareSDK.initSDK(this);
         OnekeyShare oks = new OnekeyShare();
         //关闭sso授权
        oks.disableSSOWhenAuthorize();
       // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
         // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
         oks.setText("我是分享文本，啦啦啦~");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
          oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
          // site是分享此内容的网站名称，仅在QQ空间使用
         oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
       // 启动分享GUI
        oks.show(this);
       }
    public void onShare(View view) {
        showShare();
    }

    class ContentTask extends AsyncTask<String, Void, Object> {
        /** 上下文对象 */
        private Context context;
        /** 加载数据的提示对话框 */
        private ProgressDialog pDialog;

        public ContentTask(Context context) {
            this.context = context;
            pDialog = new ProgressDialog(this.context);
            pDialog.setTitle("请稍后");
            pDialog.setMessage("正在加载请稍后...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        /**
         * 访问网络并解析数据
         */
        @Override
        protected Object doInBackground(String... params) {
            Log.i(TAG, "doInBackground()params[0]==" + params[0]);
            String jsonString = MyHttpClientHelper.loadTextFromURL(params[0],
                    "UTF-8");
            // Log.i(TAG, "jsonString==" + jsonString);
            Map<String, Object> map = JsonHelper.jsonStringToMap(jsonString,
                    new String[]{"id", "title", "source", "wap_content",
                            "create_time"}, "data");
            if (map != null && map.size() != 0) {
                /** 添加到数据库 */
                String id = map.get("id").toString();
                String title = map.get("title").toString();
                String source = map.get("source").toString();
                String create_time = map.get("create_time").toString();

                //String sql = "INSERT INTO tb_teacontents(_id,title,source,create_time,type) values (?,?,?,?,?)";
                   /* boolean flag = db.updataData(sql, new String[] { id, title,
                            source, create_time, "1" });// 1表示已经查看过，2表示被收藏了。
                    Log.i(TAG, "是否已经存在flag==" + flag);*/
            }
            // Log.i(TAG,"doInBackground()map=="+map);
            return map;
        }

        /**
         * 网络解析得到数据后的操作
         */
        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            // Log.i(TAG, "onPostExecute()result==" + result);
            if (result != null) {
                Log.i(TAG, "map==" + (Map<String, Object>) result);
                mapValue = (Map<String, Object>) result;
                /*    textView_content_title.setText(mapValue.get("title").toString());
                    textView_content_source.setText(mapValue.get("source").toString());
                    textView_content_create_time.setText(mapValue.get("create_time")
                            .toString());*/
                webView.loadDataWithBaseURL(null,
                        mapValue.get("wap_content").toString(), "text/html",
                        "UTF-8", null);

            }
            pDialog.dismiss();

        }
    }

}
