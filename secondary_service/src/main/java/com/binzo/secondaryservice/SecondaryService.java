package com.binzo.secondaryservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by liubingzhao on 2018/1/26.
 */

public class SecondaryService extends Service {

    private static final String TAG = "SecondaryService";

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind(), intent:" + intent.toString());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnBind(), intent:" + intent.toString());
        return super.onUnbind(intent);
    }

    private final ISecondary.Stub mBinder = new ISecondary.Stub() {
        @Override
        public int getPid() throws RemoteException {
            Log.i(TAG, "getPid()");
            return Process.myPid();
        }
    };
}
