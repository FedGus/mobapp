package ru.mospolytech.lab1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.classes.App;
import ru.mospolytech.lab1.classes.Auth;
import ru.mospolytech.lab1.classes.Petition;

public class RegistrationActivity extends AppCompatActivity {
    EditText login;
    EditText password;
    EditText name;
    EditText surname;
    TextView textViewReg;
    CheckBox agree;
    ApiInterface api;
    CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        login = findViewById(R.id.loginField);
        password = findViewById(R.id.passwordField);
        name = findViewById(R.id.nameField);
        surname = findViewById(R.id.surnameField);
        textViewReg = findViewById(R.id.textViewReg);
        agree = findViewById(R.id.checkBox);
    }

    public void onAuth(View view) {
        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        if (!agree.isChecked() || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(surname.getText()) || TextUtils.isEmpty(login.getText()) || TextUtils.isEmpty(password.getText())) {
            textViewReg.setText("Заполните все поля!");
        } else {
            Auth auth = new Auth(login.getText().toString(), password.getText().toString(), 0, name.getText().toString(), surname.getText().toString(), 1);
            Call<Auth> call = api.registration(auth);
            call.enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {
                    if (!response.isSuccessful()) {
                        textViewReg.setText("Code: " + response.code());
                        return;
                    }
                    textViewReg.setText(" ");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Вы успешно зарегистрированы!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    textViewReg.setText("Что-то пошло не так!");
                }
            });
        }
    }
}
