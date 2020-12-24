package ru.mospolytech.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        Auth auth = new Auth(login.getText().toString(), password.getText().toString());
        Call<Auth> call = api.auth(auth);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(!response.isSuccessful()) {
                    textViewLogin.setText("Code: " + response.code());
                    return;
                }
                textViewLogin.setText(" ");
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                textViewLogin.setText("Логин или пароль введены неверно");
            }
        });
    }
}
