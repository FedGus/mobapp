package ru.mospolytech.lab1;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/petitions")
    Observable<ProductsList> productlist(@Query("search") String search);

    @GET("api/petitions/{id_petition}")
    Observable<ProductsList> product(@Path("id_petition") int id_petition);

    @POST("api/login")
    Call<Auth> auth(@Body Auth auth);
}
