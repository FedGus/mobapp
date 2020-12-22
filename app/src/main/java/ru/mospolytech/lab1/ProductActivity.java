package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductActivity extends AppCompatActivity implements OnMapReadyCallback{
    TextView newsHeader;
    TextView newsText;
    TextView newsBody;
    TextView productCity;
    ImageView newsImageFull;
    ApiInterface api;
    private CompositeDisposable disposables;

    List<Images> listimg;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition);
        newsHeader = findViewById(R.id.productHeader);
        newsText = findViewById(R.id.productText);
        newsBody = findViewById(R.id.productBody);
        productCity = findViewById(R.id.productCity);
        newsImageFull = findViewById(R.id.newsImageFull);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        if (getIntent().getExtras() != null){
            disposables.add(
                    api.product(getIntent().getStringExtra("id"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (product) -> {
                                        newsHeader.setText(product.all.title);
//                                        newsText.setText(product.all.price/100 + " ₽");
                                        newsBody.setText(product.all.content);
                                        listimg = new ArrayList<>();
                                        listimg.clear();

//                                        Log.d(TAG, "onBindViewHolder: " + listimg.addAll(product.all.image));
                                        Glide.with(this).load(product.all.image + "").into(newsImageFull);

                                        //productCity.setText("Город: " + product.all.location.description);



                                        // Add a marker in Sydney and move the camera
//                                        LatLng sydney = new LatLng(product.all.location.latitude, product.all.location.longitude);
//                                        mMap.addMarker(new MarkerOptions()
//                                                .position(sydney)
//                                                .title("Marker in Sydney"));
//                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
        }
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
