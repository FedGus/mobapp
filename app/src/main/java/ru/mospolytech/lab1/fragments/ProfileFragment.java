package ru.mospolytech.lab1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.classes.App;
import ru.mospolytech.lab1.views.AdapterPetition;
import ru.mospolytech.lab1.views.SerializerPetitionDetail;

public class ProfileFragment extends Fragment {
    TextView name_surname;
    TextView loginProfile;
    TextView textInfo;
    EditText textSearch;
    RecyclerView recyclerView;
    AdapterPetition adapter;
    List<SerializerPetitionDetail> list;
    ApiInterface api;
    CompositeDisposable disposables;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name_surname = root.findViewById(R.id.name_surname);
        loginProfile = root.findViewById(R.id.loginProfile);
        textInfo = root.findViewById(R.id.textInfo);

        App appState = ((App) getContext().getApplicationContext()); //appState хранит данные о пользователе
        String surnameState = appState.getSurnameState(); // достаем фамилию
        String nameState = appState.getNameState();// достаем имя
        String login = appState.getLoginState(); // достаем логин
        int id_user = appState.getIdState();
        name_surname.setText(nameState + " " + surnameState);
        loginProfile.setText(login);

        list = new ArrayList<>();
        adapter = new AdapterPetition(getContext(), list);
        recyclerView = root.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textSearch = root.findViewById(R.id.textSearch);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        disposables.add(api.petitionOneAuthor(id_user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productsList) -> {

                    root.findViewById(R.id.progress).setVisibility(View.GONE);
                    root.findViewById(R.id.list).setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(productsList.all);
                    adapter.notifyDataSetChanged();
                    if (list.isEmpty()) {
                        textInfo.setVisibility(View.VISIBLE);
                    }
                }, (error) -> {
                    Toast.makeText(getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    root.findViewById(R.id.progress).setVisibility(View.GONE);
                    root.findViewById(R.id.list).setVisibility(View.VISIBLE);

                }));

        return root;
    }

}
