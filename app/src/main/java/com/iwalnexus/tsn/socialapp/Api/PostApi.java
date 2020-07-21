package com.iwalnexus.tsn.socialapp.Api;


import com.iwalnexus.tsn.socialapp.Entidades.Fotos;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PostApi {


    @GET("post/getAllPost")
    Call<List<Post>> getPostList();

    @GET("post/getAllPostPaged")
    Call<List<Post>> getAllPostPaged(@Query("page") int page);

    @POST("post")
    Call<Void> cretaePost(@Body Post post);

    @Multipart
    @POST("post/publicar")
    Call<JsonResponse> cretaePostAIO(@Part List<MultipartBody.Part> file, @Part("data") Post entity);

    @POST("fotos/upFotos")
    Call<JsonResponse> upFotos(@Body List<Fotos> fotos);
}
