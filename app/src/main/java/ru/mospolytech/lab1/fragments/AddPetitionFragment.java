package ru.mospolytech.lab1.fragments;

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


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.activities.ThanksActivity;
import ru.mospolytech.lab1.classes.App;
import ru.mospolytech.lab1.classes.Category;
import ru.mospolytech.lab1.classes.Petition;
import ru.mospolytech.lab1.classes.PetitionObject;
import ru.mospolytech.lab1.classes.Recipient;

public class AddPetitionFragment extends Fragment implements OnMapReadyCallback  {

    EditText petitonName;
    EditText petitonContent;
    TextView activity_title;
    ApiInterface api;
    CompositeDisposable disposables;
    GoogleMap mMap = null;
    UiSettings mUiSettings; //для карты
    String Lat = "52.03124688643826";
    String Long = "29.21152171202293";

    @Override
	// Создает фрагмент для отображения карты 
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
        disposables = new CompositeDisposable(); // парсер json

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map); // Для работы с картой
        mapFragment.getMapAsync(this);

        disposables.add(api.petitionList("") //надо создать объект
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // надо принять данные
                .subscribe((productsList) -> { // получаем объект
                        LatLng pin = new LatLng(52.03124688643826, 29.21152171202293); // создаем указатель на карте (пин)
                        mMap.addMarker(new MarkerOptions() 
                                .position(pin)
                                .title(pin.toString())); // Можем видеть значения пина (указателя на карте)
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));


                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
						// Метод при нажатии на карту
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

		// Список категорий
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(new Category("1", "Ремонт и реконструкция"));
        categoryArrayList.add(new Category("2", "Благоустройство"));
        categoryArrayList.add(new Category("3", "Озеленение"));
        categoryArrayList.add(new Category("4", "Освещение"));
        
		// Список объектов
        ArrayList<PetitionObject> petitionObjects = new ArrayList<PetitionObject>();
        petitionObjects.add(new PetitionObject("1", "Дворовые территории"));
        petitionObjects.add(new PetitionObject("2", "Многоквартирные дома"));
        petitionObjects.add(new PetitionObject("3", "Дороги"));
        petitionObjects.add(new PetitionObject("4", "Поликлиники"));
        petitionObjects.add(new PetitionObject("5", "Парки"));
        petitionObjects.add(new PetitionObject("6", "Летние кафе"));
		
        // Список получателей петиции
        ArrayList<Recipient> recipientArrayList = new ArrayList<Recipient>();
        recipientArrayList.add(new Recipient("1", "Муниципалитет"));
        recipientArrayList.add(new Recipient("2", "Управляющая организация домовладения"));
        recipientArrayList.add(new Recipient("3", "Управа района"));
        recipientArrayList.add(new Recipient("4", "Администрация города"));
      
	  
	    //Объявляем спинеры
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
        disposables = new CompositeDisposable(); // парсер json
            App appState = ((App) getContext().getApplicationContext()); //appState хранит данные о пользователе
            int id_user = appState.getIdState();
        Petition petition = new Petition(petitonName.getText().toString(), "", petitonContent.getText().toString(), 1, 1, 1,id_user,Lat, Long, "Мозырь, бул. Юности"); //В переменную передаем данные петиции
        Call<Petition> call = api.petition(petition); 
        call.enqueue(new Callback<Petition>() { // получение ответа асинхронно
            @Override
			// Вызывается при получении ответа
            public void onResponse(Call<Petition> call, Response<Petition> response) {
                activity_title.setText("Все хорошо");
            }

            @Override
			// Вызывается, если не получили ответа
            public void onFailure(Call<Petition> call, Throwable t) {
                Intent mainIntent = new Intent(getContext(), ThanksActivity.class);
                getActivity().startActivity(mainIntent);
            }

        });
    }
}

