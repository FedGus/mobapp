package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements OnMapReadyCallback{
    TextView newsHeader;
    TextView newsText;
    TextView newsBody;
    TextView productCity;
    TextView countSign;
    ImageView newsImageFull;
    EditText contentComment;
    ApiInterface api;
    private CompositeDisposable disposables;
    RecyclerView recyclerView;
    CommentAdapter adapter;
    List<Comment> listComment;
    List<Images> listimg;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition);
        newsHeader = findViewById(R.id.productHeader);
        newsText = findViewById(R.id.productText);
        newsBody = findViewById(R.id.productBody);
        productCity = findViewById(R.id.productCity);
        newsImageFull = findViewById(R.id.newsImageFull);
        countSign = findViewById(R.id.countSignatures);
        contentComment = findViewById(R.id.content_petition);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listComment = new ArrayList<>();
        adapter = new CommentAdapter(this, listComment);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        if (getIntent().getExtras() != null){
            disposables.add(
                    api.product(getIntent().getIntExtra("id_petition",4))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (product) -> {
                                        listimg = new ArrayList<>();
                                        listimg.clear();
                                        newsHeader.setText(product.all.get(0).title);
                                        newsText.setText("Федор Гусев создал(а) эту петицию, адресованную Префектуре (Префектура Северного административного округа)");
                                        newsBody.setText(product.all.get(0).content);
                                        Glide.with(this).load(product.all.get(0).filename + "").into(newsImageFull);
                                        productCity.setText("Адрес: " + product.all.get(0).address);
                                        // Add a marker and move the camera
                                        LatLng pin = new LatLng(Double.parseDouble(product.all.get(0).latitude), Double.parseDouble(product.all.get(0).longitude));
                                        mMap.addMarker(new MarkerOptions()
                                                .position(pin)
                                                .title("Marker"));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
            disposables.add(api.countSignatures(getIntent().getIntExtra("id_petition",4))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((countSignatures) -> {
                        countSign.setText(countSignatures.countSignatures + " человека подписали.\n Следующая цель: 100");
                    }, (error) -> {
                        Toast.makeText(this, "При загрузке подписей произошла ошибка:\n" + error.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }));

            disposables.add(api.comment(getIntent().getIntExtra("id_petition",4))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((commentsList) -> {
                        findViewById(R.id.list).setVisibility(View.VISIBLE);
                        listComment.clear();
                        listComment.addAll(commentsList.all);
                        adapter.notifyDataSetChanged();
                    }, (error) -> {
                        Toast.makeText(this, "При загрузке комментариев произошла ошибка:\n" + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                        findViewById(R.id.list).setVisibility(View.VISIBLE);

                    }));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void onAddComment(View view) {
        commentNew();
    }

    private void commentNew() {
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        App appState = ((App) this.getApplicationContext());
        if( TextUtils.isEmpty(contentComment.getText())){
            contentComment.setError( "Заполните поле комментария!" );
        }else{
            Comment comment = new Comment(appState.getIdState(), contentComment.getText().toString(), "", getIntent().getIntExtra("id_petition",4));
            listComment.add(comment);
            contentComment.setText("");
            Call<Comment> call = api.commentAdd(comment);
            call.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Что-то пошло не так!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ваш комментарий успешно опубликован!", Toast.LENGTH_SHORT);
                    toast.show();

                }

            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
