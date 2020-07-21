package com.iwalnexus.tsn.socialapp.Api;


import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.NotificationMessage;
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

public interface NotificationApi {


    @POST("NotificationService/notify")
    Call<Void> notify(@Body NotificationMessage notificationMessage);



}
