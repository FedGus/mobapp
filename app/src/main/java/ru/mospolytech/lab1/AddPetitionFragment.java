package ru.mospolytech.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mospolytech.lab1.ui.slideshow.SlideshowViewModel;


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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddPetitionFragment extends Fragment implements OnMapReadyCallback  {

    EditText petitonName;
    EditText petitonContent;
    TextView activity_title;
    ApiInterface api;
    CompositeDisposable disposables;
    GoogleMap mMap = null;
    UiSettings mUiSettings;
    String Lat;
    String Long;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        // Keep the UI Settings state in sync with the checkboxes.
        mUiSettings.setZoomControlsEnabled(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_petition, container, false);

        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        disposables.add(api.productlist("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productsList) -> {
                        LatLng pin = new LatLng(55.805046, 37.514796);
                        mMap.addMarker(new MarkerOptions()
                                .position(pin)
                                .title("list.get(lol).title"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));


                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Creating a marker
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Setting the position for the marker
                            markerOptions.position(latLng);

                            // Setting the title for the marker.
                            // This will be displayed on taping the marker
                            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                            Lat = String.valueOf(latLng.latitude);
                            Long = String.valueOf(latLng.longitude);
                            // Clears the previously touched position
                            mMap.clear();

                            // Animating to the touched position
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            // Placing a marker on the touched position
                            mMap.addMarker(markerOptions);
                        }
                    });
                }, (error) -> {
                    Toast.makeText(getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                            Toast.LENGTH_LONG).show();

                }));

        Spinner categorySpinner = root.findViewById(R.id.spinner_category);
        Spinner objectSpinner = root.findViewById(R.id.spinner_object);
        Spinner recipientObject = root.findViewById(R.id.spinner_recipient);
        Button addPetition = root.findViewById(R.id.buttonAdd);
        petitonName = root.findViewById(R.id.name_petition);
        petitonContent = root.findViewById(R.id.content_petition);
        activity_title = root.findViewById(R.id.activity_title);

        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(new Category("1", "Ремонт и реконструкция"));
        categoryArrayList.add(new Category("2", "Благоустройство"));
        categoryArrayList.add(new Category("3", "Озеленение"));
        categoryArrayList.add(new Category("4", "Освещение"));

        ArrayList<PetitionObject> petitionObjects = new ArrayList<PetitionObject>();
        petitionObjects.add(new PetitionObject("1", "Дворовые территории"));
        petitionObjects.add(new PetitionObject("2", "Многоквартирные дома"));
        petitionObjects.add(new PetitionObject("3", "Дороги"));
        petitionObjects.add(new PetitionObject("4", "Поликлиники"));
        petitionObjects.add(new PetitionObject("5", "Парки"));
        petitionObjects.add(new PetitionObject("6", "Летние кафе"));

        ArrayList<Recipient> recipientArrayList = new ArrayList<Recipient>();
        recipientArrayList.add(new Recipient("1", "Муниципалитет"));
        recipientArrayList.add(new Recipient("2", "Управляющая организация домовладения"));
        recipientArrayList.add(new Recipient("3", "Управа района"));
        recipientArrayList.add(new Recipient("4", "Администрация города"));

        ArrayAdapter<Category> oneAdapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, categoryArrayList);
        ArrayAdapter<PetitionObject> twoAdapter = new ArrayAdapter<PetitionObject>(getContext(), android.R.layout.simple_spinner_dropdown_item, petitionObjects);
        ArrayAdapter<Recipient> threeAdapter = new ArrayAdapter<Recipient>(getContext(), android.R.layout.simple_spinner_dropdown_item, recipientArrayList);

        categorySpinner.setAdapter(oneAdapter);
        objectSpinner.setAdapter(twoAdapter);
        recipientObject.setAdapter(threeAdapter);

        addPetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petitionNew();
            }
        });

        return root;
    }

        private void petitionNew() {
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        Petition petition = new Petition(petitonName.getText().toString(), "", petitonContent.getText().toString(), 1, 1, 1,1,Lat, Long, "Москва, Новопесчаная");
        Call<Petition> call = api.petition(petition);
        call.enqueue(new Callback<Petition>() {
            @Override
            public void onResponse(Call<Petition> call, Response<Petition> response) {
                activity_title.setText("Все хорошо");
            }

            @Override
            public void onFailure(Call<Petition> call, Throwable t) {
                Intent mainIntent = new Intent(getContext(), OkActivity.class);
                getActivity().startActivity(mainIntent);
            }

        });
    }
}




//public class AddPetitionFragment extends Fragment implements OnMapReadyCallback {
//
//    ApiInterface api;
//    CompositeDisposable disposables;
//    EditText petitonName;
//    EditText petitonContent;
//    TextView activity_title;
//    private GoogleMap mMap;
//    UiSettings mUiSettings;
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mUiSettings = mMap.getUiSettings();
//
//        // Keep the UI Settings state in sync with the checkboxes.
//        mUiSettings.setZoomControlsEnabled(true);
//    }
//
//    public void onMapClick(LatLng latLng) {
//        // Creating a marker
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        // Setting the position for the marker
//        markerOptions.position(latLng);
//
//        // Setting the title for the marker.
//        // This will be displayed on taping the marker
//        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//        // Clears the previously touched position
//        mMap.clear();
//
//        // Animating to the touched position
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        // Placing a marker on the touched position
//        mMap.addMarker(markerOptions);
//    }
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_add_petition, container, false);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        Spinner categorySpinner = root.findViewById(R.id.spinner_category);
//        Spinner objectSpinner = root.findViewById(R.id.spinner_object);
//        Spinner recipientObject = root.findViewById(R.id.spinner_recipient);
//        Button addPetition = root.findViewById(R.id.buttonAdd);
//        petitonName = root.findViewById(R.id.name_petition);
//        petitonContent = root.findViewById(R.id.content_petition);
//        activity_title = root.findViewById(R.id.activity_title);
//
//        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
//        categoryArrayList.add(new Category("1", "Ремонт и реконструкция"));
//        categoryArrayList.add(new Category("2", "Благоустройство"));
//        categoryArrayList.add(new Category("3", "Озеленение"));
//        categoryArrayList.add(new Category("4", "Освещение"));
//
//        ArrayList<PetitionObject> petitionObjects = new ArrayList<PetitionObject>();
//        petitionObjects.add(new PetitionObject("1", "Дворовые территории"));
//        petitionObjects.add(new PetitionObject("2", "Многоквартирные дома"));
//        petitionObjects.add(new PetitionObject("3", "Дороги"));
//        petitionObjects.add(new PetitionObject("4", "Поликлиники"));
//        petitionObjects.add(new PetitionObject("5", "Парки"));
//        petitionObjects.add(new PetitionObject("6", "Летние кафе"));
//
//        ArrayList<Recipient> recipientArrayList = new ArrayList<Recipient>();
//        recipientArrayList.add(new Recipient("2552", "Муниципалитет"));
//        recipientArrayList.add(new Recipient("2427", "Управляющая организация домовладения"));
//        recipientArrayList.add(new Recipient("2715", "Управа района"));
//        recipientArrayList.add(new Recipient("1731", "Администрация города"));
//
//        ArrayAdapter<Category> oneAdapter = new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, categoryArrayList);
//        ArrayAdapter<PetitionObject> twoAdapter = new ArrayAdapter<PetitionObject>(getContext(), android.R.layout.simple_spinner_dropdown_item, petitionObjects);
//        ArrayAdapter<Recipient> threeAdapter = new ArrayAdapter<Recipient>(getContext(), android.R.layout.simple_spinner_dropdown_item, recipientArrayList);
//
//        categorySpinner.setAdapter(oneAdapter);
//        objectSpinner.setAdapter(twoAdapter);
//        recipientObject.setAdapter(threeAdapter);
//
//        addPetition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                petitionNew();
//            }
//        });
//
//        return root;
//    }
//
//    private void petitionNew() {
//        api = ApiConfiguration.getApi();
//        disposables = new CompositeDisposable();
//        Petition petition = new Petition(petitonName.getText().toString(), "", petitonContent.getText().toString(), 1, 1, 1,1,"55.800825", "37.515259", "Москва, Новопесчаная");
//        Call<Petition> call = api.petition(petition);
//        call.enqueue(new Callback<Petition>() {
//            @Override
//            public void onResponse(Call<Petition> call, Response<Petition> response) {
//                activity_title.setText("Все хорошо");
//            }
//
//            @Override
//            public void onFailure(Call<Petition> call, Throwable t) {
//                activity_title.setText("Все плохо");
//            }
//
//        });
//    }
//}
