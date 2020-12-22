package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "A";
    EditText textSearch;
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<ProductDetail> list;
    ApiInterface api;
    private CompositeDisposable disposables;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textSearch = findViewById(R.id.textSearch);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        this.onClick(this.recyclerView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        disposables.add(api.productlist(textSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productsList) -> {

                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(productsList.all);
                    adapter.notifyDataSetChanged();
                    for (int lol = 0 ; lol < list.size(); lol++) {
                        LatLng pin = new LatLng(Double.parseDouble(list.get(lol).latitude), Double.parseDouble(list.get(0).longitude));
                        mMap.addMarker(new MarkerOptions()
                                .position(pin)
                                .title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));
                    }
                }, (error) -> {
                    Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);

                }));



    }

    public void onClick(View view){
        if (textSearch.getText().toString().isEmpty()){
            list.clear();
        } else {
            disposables.add(api.productlist(textSearch.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((productsList) -> {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);
                        list.clear();
                        list.addAll(productsList.all);
                        adapter.notifyDataSetChanged();
                    }, (error) -> {
                        Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);

                    }));
            Toast.makeText(this, "Показаны результаты поиска: " + textSearch.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        this.startActivity(intent);
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
