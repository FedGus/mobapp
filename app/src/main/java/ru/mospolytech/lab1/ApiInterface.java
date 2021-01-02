package ru.mospolytech.lab1;

import java.security.Signature;

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

    @POST("api/add-petition")
    Call<Petition> petition(@Body Petition petition);

    @GET("api/getPetitionComment/{id_petition}")
    Observable<CommentsList> comment(@Path("id_petition") int id_petition);

    @GET("api/getAuthorCommentName/{id_comment}")
    Observable<CommentAuthor> commentAuthor(@Path("id_comment") String id_comment);

    @GET("api/getSignatures/{id_petition}")
    Observable<Signatures> countSignatures(@Path("id_petition") int id_petition);
}
