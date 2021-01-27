package ru.mospolytech.lab1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.classes.App;

public class ProfileFragment extends Fragment {
    TextView name_surname;
    TextView loginProfile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name_surname = root.findViewById(R.id.name_surname);
        loginProfile = root.findViewById(R.id.loginProfile);

        App appState = ((App) getContext().getApplicationContext()); //appState хранит данные о пользователе
        String surnameState = appState.getSurnameState(); // достаем фамилию
        String nameState = appState.getNameState();// достаем имя
        String login = appState.getLoginState(); // достаем логин
        name_surname.setText(nameState + " " + surnameState);
        loginProfile.setText(login);

        return root;
    }

}
