package com.example.mbenben.httplib;

/**
 * Created by MBENBEN on 2016/6/20.
 * 丹利模式
 */
public class HttpHelper {
    private static HttpHelper Instance;
    private RequestQueue mQueue;

    private HttpHelper(){
        mQueue = new RequestQueue();
    }

    private static HttpHelper getInstance(){
        if(Instance ==null){
            synchronized (HttpHelper.class){
                if(Instance == null){
                    Instance = new HttpHelper();
                }
            }
        }
        return Instance;
    }

    public static void addRequest(Request request){
        getInstance().mQueue.addRequest(request);
    }
}
