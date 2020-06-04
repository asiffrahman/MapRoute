package com.example.asifj.maproute;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback,
                                                                GoogleMap.OnMapClickListener,
                                                                GoogleMap.OnPolylineClickListener,
                                                                GoogleMap.OnPolygonClickListener {

    private GoogleMap mMap;
    private Polyline currentPolyline;
    private int markerIndex=0;
    private DesMarker des;
    private List<VehMarker> VMList = new ArrayList<VehMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng uta = new LatLng(32.729859, -97.113878);
        mMap.addMarker(new MarkerOptions().position(uta).title("UTA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uta,16));
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (markerIndex==4){
            des = new DesMarker(latLng);
            mMap.addMarker(new MarkerOptions().position(des.getPos()).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            for(int i=0;i<markerIndex;i++) {
                DrawRoute(VMList.get(i), des);
            }

            markerIndex++;
        }

        if (markerIndex<4) {
            VMList.add(new VehMarker(latLng,markerIndex));
            mMap.addMarker(new MarkerOptions().position(VMList.get(markerIndex).getPos()));
            markerIndex++;
            if (markerIndex==4){
                toastForDesMarker();
            }
        }

    }

    public  void toastForDesMarker(){
        Context context = getApplicationContext();
        CharSequence text = "Select The Desitination";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }
    private void DrawRoute(VehMarker origin, DesMarker dest){
        String url = getUrl(origin.getPos(),dest.getPos(),"driving");
        new FetchURL(MapsActivity.this).execute(url,"driving");

    }
    @Override
    public void onTaskDone(Object... values) {
        //if (currentPolyline != null)
          //  currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }



}
