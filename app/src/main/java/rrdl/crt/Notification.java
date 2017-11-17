package rrdl.crt;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Notification extends Fragment implements OnMapReadyCallback,LocationListener {
    private MapView mapView;
    private GoogleMap map;
    Marker marker;
    private LocationSource.OnLocationChangedListener mListener;
    crtLocation[] crt = new crtLocation[11];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        mapView =  v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //this is important

        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //crt list
        crt[0] = new crtLocation(36.796043,10.176679 ,"Rue de Angleterre, Tunis");
        crt[1] = new crtLocation(36.811240,10.168087 ,"DNSP, Rue du Fort, Tunis");
        crt[2] = new crtLocation(36.768390,10.231351 ,"Centre National de Formation des volontaires");
        crt[3] = new crtLocation(36.765738,10.249805 ,"Comité Local megrine");
        crt[4] = new crtLocation(36.857147,10.188060 ,"Croissant Rouge Tunisien");
        crt[5] = new crtLocation(35.857905,10.598179 ,"comité local Hammam Sousse");
        crt[6] = new crtLocation(34.731843,10.759640,"Rue El Arbi Zarrouk, Sfax");
        crt[7] = new crtLocation(33.504106,11.088150 ,"Mouensa, Zarzis ");
        crt[8] = new crtLocation(36.441899,10.729911 ," Comité Regional De Nabeul");
        crt[9] = new crtLocation( 35.829321,10.638072 ,"Comité Local de Sousse");
        crt[10] = new crtLocation(33.137021,11.220034 ,"Comité Local Benguerdane");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0;i<11;i++) {
             marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(crt[i].getX(), crt[i].getY()))
                    .title("Croissant Rouge Tunisien")
                    .snippet(crt[i].getAdresse()));
            builder.include(marker.getPosition());
        }

        


        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if( mListener != null )
        {
            mListener.onLocationChanged( location );

            //Move the camera to the user's location and zoom in!
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 52.0f));
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
