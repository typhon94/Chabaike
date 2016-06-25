package com.example.mbenben.httplib;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;

/**
 * Created by MBENBEN on 2016/6/20.
 */
public class NetWorkDispatcher  extends Thread{
    private static final String TAG =NetWorkDispatcher.class.getSimpleName();
    private BlockingDeque<Request> mQueue;
    //代表线程的可用状态
    public boolean flag = true;

    //当开启一个线程时，传一个请求到线程中
    NetWorkDispatcher(BlockingDeque<Request> queue){
        mQueue = queue;
    }


    //重写run方法
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void run() {
        //super.run();
        /**
         * 当心线程的标记是可用的，并且当前线程没有被打断时
         * 重请求队列中取出请求并且进行网络访问
         */
        while(flag&&!isInterrupted()){
            try {
                final Request request =mQueue.take();
                byte[] result = null;
                result = getNetworkResponse(request);
                if(result!=null){
                    request.dispatchContent(result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag=false;
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    private byte[] getNetworkResponse(Request request) throws Exception{
        if(TextUtils.isEmpty(request.getUrl())){
            throw new Exception("url is null");
        }
        else if(request.getMethod().equals(Request.Method.GET)){
            return getResponseByGET(request);
        }
        return null;
    }


    private byte[] getResponseByGET(Request request) throws Exception {

        URL url = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        url = new URL(request.getUrl());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int code = conn.getResponseCode();
        if(code!=200){
            Log.d(TAG, "getNetworkResponse() returned: response code error code=" + code);
            throw  new Exception("code error");
        }else{
            inputStream = conn.getInputStream();
            byte[] buf = new byte[2048];
            int len = 0;
            //不是if是while if((len=(inputStream.read(buf)))>0){
          //  out.write(buf,0,len);
      //  }
            while((len=(inputStream.read(buf)))>0){
                out.write(buf,0,len);
            }

        }
        inputStream.close();
        return out.toByteArray();
    }


}
