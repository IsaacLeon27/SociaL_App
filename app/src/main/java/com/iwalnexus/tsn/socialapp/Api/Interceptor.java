package com.iwalnexus.tsn.socialapp.Api;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class Interceptor implements okhttp3.Interceptor {

    private String token;

    public Interceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .addHeader("X-Auth-Token", token);

        Request request = builder.build();

        return chain.proceed(request);
    }
}
