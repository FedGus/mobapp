package ru.mospolytech.lab1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.classes.App;
import ru.mospolytech.lab1.classes.Auth;


public class AuthActivity extends AppCompatActivity {
    TextView textViewLogin;
    TextView login;
    TextView password;
    ApiInterface api;
    private CompositeDisposable disposables;

    @Override
	// Метод, который вызывается при загрузке документа первым
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        textViewLogin  = findViewById(R.id.textViewLogin);
        login  = findViewById(R.id.nameField);
        password  = findViewById(R.id.passwordField);
    }

    public void onClick(View view) {
        authLogin(); 
    }
    public void onRegistration(View view) {
        Intent intent = new Intent(AuthActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void authLogin() {
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable(); //парсер json
        findViewById(R.id.progressAuth).setVisibility(View.VISIBLE); //отображает колесико
        findViewById(R.id.button).setVisibility(View.GONE); // скрывает кнопку

        Auth auth = new Auth(login.getText().toString(), password.getText().toString(), 0, "", "", 1); //переменная для класса Auth
        Call<Auth> call = api.auth(auth); // вызываем api через ApiInterface
        call.enqueue(new Callback<Auth>() { // асинхронный вызов 
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) { // Метод вызывается, когда приходит ответ
                if(!response.isSuccessful()) { // Если неудачно
                    textViewLogin.setText("Code: " + response.code()); 
                    return;
                }
                textViewLogin.setText(" ");
                App appState = ((App)getApplicationContext()); 
                appState.setState(response.body().getId_user(), response.body().getName() , response.body().getSurname(), response.body().getLogin()); // Сохраняем данные в переменной appState
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent); // Запускаем главную страницу
            }

            @Override
			// Функция, если ответ не пришел (какие-то ошибки)
            public void onFailure(Call<Auth> call, Throwable t) {
                textViewLogin.setText("Логин или пароль введены неверно");
                findViewById(R.id.progressAuth).setVisibility(View.GONE); // Скрываем progressAuth
                findViewById(R.id.button).setVisibility(View.VISIBLE); // отображаем button
            }
        });
    }
}
