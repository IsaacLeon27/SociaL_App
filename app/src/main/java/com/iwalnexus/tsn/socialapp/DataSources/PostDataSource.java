package com.iwalnexus.tsn.socialapp.DataSources;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.PostApi;
import com.iwalnexus.tsn.socialapp.Entidades.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<Long, Post> {

    public static int PAGE_SIZE = 10;
    public static long FIRST_PAGE = 1;
    private PostApi service;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Post> callback) {

        service = API.api().create(PostApi.class);

        Call<List<Post>> postCall = service.getAllPostPaged(1);
        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response != null){
                    callback.onResult(response.body(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Post> callback) {

        service = API.api().create(PostApi.class);

        Call<List<Post>> postCall = service.getAllPostPaged(params.key.intValue());

        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response != null){

                    long key;

                    if(params.key > 1){
                        key = params.key - 1;

                    }else {
                        key= 0;
                    }
                    callback.onResult(response.body(), key);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Post> callback) {

        service = API.api().create(PostApi.class);

        Call<List<Post>> postCall = service.getAllPostPaged(params.key.intValue());
        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response != null){
                    callback.onResult(response.body(),  params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}
