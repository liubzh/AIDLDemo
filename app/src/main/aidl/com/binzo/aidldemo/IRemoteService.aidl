// IRemoteService.aidl
package com.binzo.aidldemo;

// Declare any non-default types here with import statements
import com.binzo.aidldemo.IRemoteServiceCallback;

interface IRemoteService {
    /**
      * Demonstrates some basic types that you can use as parameters
      * and return values in AIDL.
      */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    int getPid();
    void registerCallback(in IRemoteServiceCallback cb);
    void unregisterCallback(in IRemoteServiceCallback cb);
}
