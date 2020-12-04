package ru.mospolytech.lab1;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/v1/products")
    Observable<ProductsList> productlist(@Query("search") String search);

    @GET("api/v1/product/{id}")
    Observable<ProductObject> product(@Path("id") String id);
}
