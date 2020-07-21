package com.iwalnexus.tsn.socialapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.iwalnexus.tsn.socialapp.DataSources.PostDataSource;
import com.iwalnexus.tsn.socialapp.DataSources.PostDataSourceFactory;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.Repositorios.UserRepository;

import java.util.List;


public class PostViewModel extends ViewModel {

    public LiveData<PostDataSource> liveDataSouce;
    public LiveData<PagedList<Post>> postPagedList;


    public PostViewModel() {
        init();
    }

    private void init() {

        PostDataSourceFactory postDataSourceFactory = new PostDataSourceFactory();
        liveDataSouce = postDataSourceFactory.lista;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PostDataSource.PAGE_SIZE)
                .build();

        postPagedList = new LivePagedListBuilder<>(postDataSourceFactory, config).build();

    }


}
