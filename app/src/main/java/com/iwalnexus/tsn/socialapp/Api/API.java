package com.iwalnexus.tsn.socialapp.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BASE_URL = "http://10.0.2.2:8080/AppSocial/webresources/";

    private static Retrofit retrofit = null;

    public static Retrofit api(){

        if(retrofit == null){

            Interceptor i = new Interceptor("");

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(i)
                    .build();

           retrofit = new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(okHttpClient)
                   .build();

        }

        return retrofit;

    }
}
