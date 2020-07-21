package com.iwalnexus.tsn.socialapp.Api;


import com.iwalnexus.tsn.socialapp.Entidades.Fotos;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Likes;
import com.iwalnexus.tsn.socialapp.Entidades.Post;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LikesApi {


    @GET("likes/getLikes")
    Call<JsonResponse> getLikes(@Query("post") int post);

    @POST("likes")
    Call<Void> createLike(@Body Likes like);


}
