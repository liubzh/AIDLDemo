// IRemoteServiceCallback.aidl
package com.binzo.aidldemo;

// Declare any non-default types here with import statements

interface IRemoteServiceCallback {
    /**
     * Basic types that you can use as parameters
     * and you can return values in AIDL.
     */
    void valueChanged(int value);
}
