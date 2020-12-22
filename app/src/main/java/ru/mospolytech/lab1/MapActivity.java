package ru.mospolytech.lab1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import io.reactivex.disposables.CompositeDisposable;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ApiInterface api;
    private CompositeDisposable disposables;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
