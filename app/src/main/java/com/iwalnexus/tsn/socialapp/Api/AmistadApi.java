package com.iwalnexus.tsn.socialapp.Api;


import com.iwalnexus.tsn.socialapp.Entidades.Amigos;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Likes;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AmistadApi {


    @GET("amigos/getFriends")
    Call<List<Usuario>> getFriends(@Query("id") int id);

    @POST("amigos")
    Call<Void> createFriend(@Body Amigos like);


}
