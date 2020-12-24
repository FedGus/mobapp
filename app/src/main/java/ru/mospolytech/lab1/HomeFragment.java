package ru.mospolytech.lab1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements OnMapReadyCallback  {

    EditText textSearch;
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<ProductDetail> list;
    ApiInterface api;
    CompositeDisposable disposables;
    GoogleMap mMap = null;
    UiSettings mUiSettings;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        // Keep the UI Settings state in sync with the checkboxes.
        mUiSettings.setZoomControlsEnabled(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        list = new ArrayList<>();
        adapter = new ListAdapter(getContext(), list);
        recyclerView = root.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textSearch = root.findViewById(R.id.textSearch);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        disposables.add(api.productlist(textSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productsList) -> {

                    root.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    root.findViewById(R.id.list).setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(productsList.all);
                    adapter.notifyDataSetChanged();
                    for (int lol = 0; lol < list.size(); lol++) {
                        LatLng pin = new LatLng(Double.parseDouble(list.get(lol).latitude), Double.parseDouble(list.get(lol).longitude));
                        mMap.addMarker(new MarkerOptions()
                                .position(pin)
                                .title(list.get(lol).title));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));
                    }
                }, (error) -> {
                    Toast.makeText(getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    root.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    root.findViewById(R.id.list).setVisibility(View.VISIBLE);

                }));

               textSearch.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if (textSearch.getText().toString().isEmpty()) {
                           list.clear();
                       } else {
                           disposables.add(api.productlist(textSearch.getText().toString())
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe((productsList) -> {
                                       root.findViewById(R.id.progressBar).setVisibility(View.GONE);
                                       root.findViewById(R.id.list).setVisibility(View.VISIBLE);
                                       list.clear();
                                       list.addAll(productsList.all);
                                       adapter.notifyDataSetChanged();
                                   }, (error) -> {
                                       Toast.makeText(getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                                               Toast.LENGTH_LONG).show();
                                       root.findViewById(R.id.progressBar).setVisibility(View.GONE);
                                       root.findViewById(R.id.list).setVisibility(View.VISIBLE);

                                   }));
                           Toast.makeText(getContext(), "Показаны результаты поиска: " + textSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });

        return root;
    }
}
