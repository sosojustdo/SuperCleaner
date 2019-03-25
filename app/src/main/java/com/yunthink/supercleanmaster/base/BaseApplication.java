package com.yunthink.supercleanmaster.base;

import android.app.Application;
import android.content.Context;

import com.facebook.ads.AudienceNetworkAds;

import java.util.HashMap;

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private HashMap<String, Object> mHashMap;
    private static BaseApplication appli;

    private Context mContext;


    // private Gson mG;
    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
        AudienceNetworkAds.isInAdsProcess(this);
        mInstance = this;
        setAppli(this);
        mHashMap = new HashMap<String, Object>();
        MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
        myCrashHandler.init(getApplicationContext());
        Thread.currentThread().setUncaughtExceptionHandler(myCrashHandler);
    }

    public static BaseApplication getAppli() {
        return appli;
    }

    private void setAppli(BaseApplication appli) {
        this.appli = appli;
    }
    public Object getDataObject(String key) {
//		读取值得调用方法，当key中含有delete时
//		会在取完值得时候，删除掉当前的对象内容
        Object o = mHashMap.get(key);
        if (key.indexOf("delete") > 0) {
            mHashMap.remove(key);
        }
        return o;
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setDataObject(String key, Object value) {
//		塞入数据key为String，值为object所以可以做任何类型的传值调用
        mHashMap.put(key, value);
    }


    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub

        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();

    }

}
