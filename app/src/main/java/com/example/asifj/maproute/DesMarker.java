package com.example.asifj.maproute;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by asifj on 8/26/2019.
 */

public class DesMarker {

    LatLng pos;

    String Address;


    public DesMarker(LatLng position) {
        this.pos = position;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }



    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


}
