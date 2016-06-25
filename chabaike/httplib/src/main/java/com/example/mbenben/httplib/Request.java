package com.example.mbenben.httplib;

/**
 * Created by MBENBEN on 2016/6/20.
 */

//保存请求的信息
public abstract class Request<T>  {

    private String url;
    //在JDK1.5 之前，我们定义常量都是： public static fianl.... 。现在好了，有了枚举，
    // 可以把相关的常量分组到一个枚举类型里，而且枚举提供了比常量更多的方法。
    //private Method
    private Method method;

    private Callback callback;

    public enum Method{
        POST,GET
    }

    public Request(){

    }

    public Request(String url,Method method,Callback callback){
        this.url = url;
        this.method = method;
        this.callback = callback;
    }

    public Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Callback getCallback(){
        return callback;
    }

    public interface Callback<T>{

        void onEror(Exception e);
        void onResonse(T response);
    }

    public void onError(Exception e) {
        callback.onEror(e);
    }

    protected void onResponse(T res){
        callback.onResonse(res);
    }

    abstract public void dispatchContent(byte[] content);
}
