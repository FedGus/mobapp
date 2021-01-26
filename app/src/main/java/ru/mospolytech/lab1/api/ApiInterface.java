package ru.mospolytech.lab1.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mospolytech.lab1.views.SerializerComment;
import ru.mospolytech.lab1.views.SerializerAuthor;
import ru.mospolytech.lab1.views.SerializerCommentsList;
import ru.mospolytech.lab1.views.SerializerPetitionList;
import ru.mospolytech.lab1.views.SerializerSignatures;
import ru.mospolytech.lab1.classes.Auth;
import ru.mospolytech.lab1.classes.Petition;

public interface ApiInterface {

    @GET("api/petitions")
    Observable<SerializerPetitionList> productlist(@Query("search") String search);

    @GET("api/petitions/{id_petition}")
    Observable<SerializerPetitionList> product(@Path("id_petition") int id_petition);

    @POST("api/login")
    Call<Auth> auth(@Body Auth auth);

    @POST("api/registration")
    Call<Auth> registration(@Body Auth registration);

    @POST("api/add-petition")
    Call<Petition> petition(@Body Petition petition);

    @GET("api/getPetitionComment/{id_petition}")
    Observable<SerializerCommentsList> comment(@Path("id_petition") int id_petition);

    @GET("api/getAuthorCommentName/{id_comment}")
    Observable<SerializerAuthor> commentAuthor(@Path("id_comment") String id_comment);

    @GET("api/getSignatures/{id_petition}")
    Observable<SerializerSignatures> countSignatures(@Path("id_petition") int id_petition);

    @GET("api/getUserSignature/{id_petition}/{id_user}")
    Observable<SerializerSignatures> getSignature(@Path("id_petition") int id_petition, @Path("id_user") int id_user);

    @POST("api/addSignature")
    Call<SerializerSignatures> signatureAdd(@Body SerializerSignatures signature);

    @POST("api/addComment")
    Call<SerializerComment> commentAdd(@Body SerializerComment serializerComment);

}
