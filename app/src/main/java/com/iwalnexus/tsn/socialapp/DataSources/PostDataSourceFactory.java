package com.iwalnexus.tsn.socialapp.DataSources;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.iwalnexus.tsn.socialapp.Entidades.Post;

import java.util.List;

public class PostDataSourceFactory extends DataSource.Factory<Long, Post> {

    public MutableLiveData<PostDataSource> lista = new MutableLiveData<>();

    @Override
    public DataSource<Long, Post> create() {
        PostDataSource postDataSource = new PostDataSource();
        lista.postValue(postDataSource);
        return postDataSource;
    }
}
