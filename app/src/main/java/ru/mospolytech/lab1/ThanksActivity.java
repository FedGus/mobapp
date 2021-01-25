package ru.mospolytech.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
    }

    public void onClick(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        this.startActivity(mainIntent);
    }
}
