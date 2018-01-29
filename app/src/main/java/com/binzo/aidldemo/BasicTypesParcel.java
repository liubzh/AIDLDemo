package com.binzo.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by binzo on 2018/1/29.
 */

public class BasicTypesParcel implements Parcelable {

    private int anInt;
    private long aLong;
    private boolean aBoolean;
    private float aFloat;
    private double aDouble;
    private String aString;

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public String getaString() {
        return aString;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public BasicTypesParcel(int i, long l, boolean b, float f, double d, String s) {
        anInt = i;
        aLong = l;
        aBoolean = b;
        aFloat = f;
        aDouble = d;
        aString = s;
    }

    protected BasicTypesParcel(Parcel in) {
        anInt = in.readInt();
        aLong = in.readLong();
        aBoolean = in.readByte() != 0;
        aFloat = in.readFloat();
        aDouble = in.readDouble();
        aString = in.readString();
    }

    public static final Creator<BasicTypesParcel> CREATOR = new Creator<BasicTypesParcel>() {
        @Override
        public BasicTypesParcel createFromParcel(Parcel in) {
            return new BasicTypesParcel(in);
        }

        @Override
        public BasicTypesParcel[] newArray(int size) {
            return new BasicTypesParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(anInt);
        parcel.writeLong(aLong);
        parcel.writeByte((byte) (aBoolean ? 1 : 0));
        parcel.writeFloat(aFloat);
        parcel.writeDouble(aDouble);
        parcel.writeString(aString);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("anInt=" + anInt + ",");
        sb.append("aLong=" + aLong + ",");
        sb.append("aBoolean=" + aBoolean + ",");
        sb.append("aFload=" + aFloat + ",");
        sb.append("aDouble=" + aDouble + ",");
        sb.append("aString=" + aString);
        sb.append("}");
        return sb.toString();
    }
}
