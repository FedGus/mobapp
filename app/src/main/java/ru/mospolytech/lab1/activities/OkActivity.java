package ru.mospolytech.lab1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.activities.MainActivity;

// Для возвращения в главное меню после нажатия на клавишу "Ок"
public class OkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok);
    }

    public void onClick(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        this.startActivity(mainIntent);
    }
}
