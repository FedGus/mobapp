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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        textViewLogin  = findViewById(R.id.textViewLogin);
        login  = findViewById(R.id.login);
        password  = findViewById(R.id.password);
    }

    public void onClick(View view) {
        authLogin();
    }

    private void authLogin() {
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        findViewById(R.id.progressAuth).setVisibility(View.VISIBLE);
        findViewById(R.id.button).setVisibility(View.GONE);

        Auth auth = new Auth(login.getText().toString(), password.getText().toString(), 0, "", "");
        Call<Auth> call = api.auth(auth);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(!response.isSuccessful()) {
                    textViewLogin.setText("Code: " + response.code());
                    return;
                }
                textViewLogin.setText(" ");
                App appState = ((App)getApplicationContext());
                appState.setState(response.body().getId_user(), response.body().getName() , response.body().getSurname(), response.body().getLogin());
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                textViewLogin.setText("Логин или пароль введены неверно");
                findViewById(R.id.progressAuth).setVisibility(View.GONE);
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
        });
    }
}
