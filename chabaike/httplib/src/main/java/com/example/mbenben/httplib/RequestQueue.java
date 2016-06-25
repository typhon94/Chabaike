package com.example.mbenben.httplib;



import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by MBENBEN on 2016/6/20.
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class RequestQueue {
   //新建一个双向队列，先进先出，放Request，和取Request
   private BlockingDeque<Request> requesQueue = new LinkedBlockingDeque<>();

    //后台访问网络的线程个数
    private final int MAX_THREADS = 3;

    //后台线程的引用

    private NetWorkDispatcher[] workers = new NetWorkDispatcher[MAX_THREADS];

    //创建队列的时候，也创建几个阻塞的线程
    public RequestQueue(){
        initDispatcher();
    }

    private void initDispatcher() {
        for(int i=0;i<workers.length;i++){
            //创建一个访问网络的线程类
            workers[i] = new NetWorkDispatcher(requesQueue);
            workers[i].start();

        }

    }

    public void addRequest(Request request){
        requesQueue.add(request);

    }
    private void stop(){
        for(int i=0;i<workers.length;i++){
            workers[i].flag = false;
            workers[i].interrupt();
        }

    }

}
