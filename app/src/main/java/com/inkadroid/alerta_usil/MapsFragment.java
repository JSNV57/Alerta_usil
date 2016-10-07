package com.inkadroid.alerta_usil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.Session;
import com.facebook.SessionState;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MapsFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                    .setPermissions(Arrays.asList("public_profile"))
                    .setCallback(statusCallback));
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(-12.0728295,-76.9537873), new LatLng(-12.0728295,-76.9537873));

        // Add a marker in Sydney and move the camera
        //-12.0582358,-77.1095645
        //-12.0582856,-77.1088055
        LatLng sydney = new LatLng(-12.0728295,-76.9537873);
        mMap.addMarker(new MarkerOptions().position(sydney).title("USIL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17));

        mMap.getUiSettings().setZoomGesturesEnabled(true);

    }
}