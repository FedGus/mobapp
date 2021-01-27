package ru.mospolytech.lab1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import ru.mospolytech.lab1.R;

// Заглушка при открытии приложения
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; // Задержка в 3сек
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plug_app);

        new Handler().postDelayed(new Runnable() {
            @Override
			// Функция, которые говорит, загрузилось ли приложение
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, AuthActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish(); // Закрываем заглушку
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
	
    public void onBackPressed() {
        super.onBackPressed(); // Закрываем приложение

    }
}
