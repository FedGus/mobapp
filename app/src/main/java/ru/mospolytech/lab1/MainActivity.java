package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  {

    EditText textSearch;
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<ProductDetail> list;
    ApiInterface api;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textSearch = findViewById(R.id.textSearch);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        this.onClick(this.recyclerView);

        disposables.add(api.productlist(textSearch.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productsList) -> {

                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                    list.clear();
                    list.addAll(productsList.all);
                    adapter.notifyDataSetChanged();
                }, (error) -> {
                    Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);

                }));

    }


    public void onClick(View view){
        if (textSearch.getText().toString().isEmpty()){
            list.clear();
        } else {
            disposables.add(api.productlist(textSearch.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((productsList) -> {

                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);
                        list.clear();
                        list.addAll(productsList.all);
                        adapter.notifyDataSetChanged();
                    }, (error) -> {
                        Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);

                    }));
            Toast.makeText(this, "Показаны результаты поиска: " + textSearch.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
