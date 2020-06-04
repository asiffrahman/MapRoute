package com.example.asifj.maproute;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by asifj on 8/26/2019.
 */

public class VehMarker {

    LatLng pos;
    int index;
    String Address;
    Double Distance;
    Double Time;

    public VehMarker(LatLng position, int index) {
        this.pos = position;
        this.index = index;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public Double getTime() {
        return Time;
    }

    public void setTime(Double time) {
        Time = time;
    }
}
