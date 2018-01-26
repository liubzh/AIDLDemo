package com.binzo.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by liubingzhao on 2018/1/26.
 */

public class RemoteService extends Service {

    public static final String TAG = "RemoteService";
    private IRemoteServiceCallback mCallback = null;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        // Return the interface
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            // Does nothing
            Log.i(TAG, "basicTypes(...)");
        }

        public int getPid(){
            Log.i(TAG, "getPid()");
            if (mCallback != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Do some time-consuming tasks here
                            mCallback.valueChanged(1);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000/* 3 seconds */);
            }
            return Process.myPid();
        }

        @Override
        public void registerCallback(IRemoteServiceCallback cb) throws RemoteException {
            mCallback = cb;
        }

        @Override
        public void unregisterCallback(IRemoteServiceCallback cb) throws RemoteException {
            mCallback = null;
        }
    };
}