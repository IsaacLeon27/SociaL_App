package com.iwalnexus.tsn.socialapp.Api;


import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UsuarioApi {


    @POST("usuario")
    Call<Void> createUser(@Body Usuario user);

    @GET("usuario/auth")
    Call<Usuario> auth(@Query("user") String user,@Query("pass") String pass);

    @GET("usuario")
    Call<List<Usuario>> getAllUsers();

    @PUT("usuario/pushId")
    Call<Void> setPushId(@Body Usuario user);

    @Multipart
    @POST("usuario/uploadIcon")
    Call<JsonResponse> uploadIcon(@Part List<MultipartBody.Part> file, @Part("data") Usuario user);

}
