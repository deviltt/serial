package com.tt.entity;

import java.io.Serializable;

public class DestinationData implements Serializable {
    private int hour;
    private int minute;
    private int second;
    private int microsecond;  //*10 ms

    private int longitudeDegree;
    private int longitudeMinute;
    private int longitudeSecond;
    private int longitudeMicrosecond; //*100ms

    private int latitudeDegree;
    private int latitudeMinute;
    private int latitudeSecond;
    private int latitudeMicrosecond;  //*100ms

    @Override
    public String toString() {
        return "DestinationData{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", microsecond=" + microsecond +
                ", longitudeDegree=" + longitudeDegree +
                ", longitudeMinute=" + longitudeMinute +
                ", longitudeSecond=" + longitudeSecond +
                ", longitudeMicrosecond=" + longitudeMicrosecond +
                ", latitudeDegree=" + latitudeDegree +
                ", latitudeMinute=" + latitudeMinute +
                ", latitudeSecond=" + latitudeSecond +
                ", latitudeMicrosecond=" + latitudeMicrosecond +
                '}';
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public double getMicrosecond() {
        return microsecond;
    }

    public void setMicrosecond(int microsecond) {
        this.microsecond = microsecond * 10;
    }

    public int getLongitudeDegree() {
        return longitudeDegree;
    }

    public void setLongitudeDegree(int longitudeDegree) {
        this.longitudeDegree = longitudeDegree;
    }

    public int getLongitudeMinute() {
        return longitudeMinute;
    }

    public void setLongitudeMinute(int longitudeMinute) {
        this.longitudeMinute = longitudeMinute;
    }

    public int getLongitudeSecond() {
        return longitudeSecond;
    }

    public void setLongitudeSecond(int longitudeSecond) {
        this.longitudeSecond = longitudeSecond;
    }

    public double getLongitudeMicrosecond() {
        return longitudeMicrosecond;
    }

    public void setLongitudeMicrosecond(int longitudeMicrosecond) {
        this.longitudeMicrosecond = longitudeMicrosecond * 100;
    }

    public int getLatitudeDegree() {
        return latitudeDegree;
    }

    public void setLatitudeDegree(int latitudeDegree) {
        this.latitudeDegree = latitudeDegree;
    }

    public int getLatitudeMinute() {
        return latitudeMinute;
    }

    public void setLatitudeMinute(int latitudeMinute) {
        this.latitudeMinute = latitudeMinute;
    }

    public int getLatitudeSecond() {
        return latitudeSecond;
    }

    public void setLatitudeSecond(int latitudeSecond) {
        this.latitudeSecond = latitudeSecond;
    }

    public double getLatitudeMicrosecond() {
        return latitudeMicrosecond;
    }

    public void setLatitudeMicrosecond(int latitudeMicrosecond) {
        this.latitudeMicrosecond = latitudeMicrosecond * 100;
    }
}
