package com.iwalnexus.tsn.socialapp.Fragmentos;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Activities.PostActivity;
import com.iwalnexus.tsn.socialapp.Adaptadores.PostAdapter;
import com.iwalnexus.tsn.socialapp.Adaptadores.PostAdapterMultiple;
import com.iwalnexus.tsn.socialapp.Adaptadores.PostAdapterMultiplePaged;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.LikesApi;
import com.iwalnexus.tsn.socialapp.Api.NotificationApi;
import com.iwalnexus.tsn.socialapp.Api.PostApi;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Likes;
import com.iwalnexus.tsn.socialapp.Entidades.NotificationMessage;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;
import com.iwalnexus.tsn.socialapp.ViewModels.PostViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    private List<Post> lista;

    private PostApi service;
    private Call<List<Post>> getPostCall;

    private RecyclerView rv;
    private PostAdapterMultiplePaged adapter;
    private RecyclerView.LayoutManager manager;

    private FloatingActionButton fab;

    private static final int POST_CODE = 1;

    private SharedPreferences pref;
    private Usuario user;

    private LikesApi serviceLike;
    private Call<Void> doLike;
    private Call<JsonResponse> getLikes;

    private PostViewModel postViewModel;
    private Observer mainObserver;

    private NotificationApi serviceNotification;
    private Call<Void> notify;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        pref = getActivity().getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);
        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);


        rv = view.findViewById(R.id.rvPost);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), PostActivity.class);
            startActivityForResult(intent, POST_CODE);
        });

        service = API.api().create(PostApi.class);
        serviceLike = API.api().create(LikesApi.class);
        serviceNotification = API.api().create(NotificationApi.class);

        /*getPostCall = service.getPostList();

        getPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                lista = response.body();

                adapter = new PostAdapterMultiple(lista, R.layout.post_no_foto,R.layout.post_foto, getContext(), (obj, view , position) -> {

                    TextView counter = (TextView) view;
                    Likes like= new Likes();
                    like.setPost(obj);
                    like.setUsuario(user);

                    doLike = serviceLike.createLike(like);
                    doLike.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            getLikes = serviceLike.getLikes(obj.getIdpost());
                            getLikes.enqueue(new Callback<JsonResponse>() {
                                @Override
                                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                                    JsonResponse jr = response.body();

                                    if(jr != null){

                                        counter.setText(jr.getJson());
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonResponse> call, Throwable t) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                });

                manager = new LinearLayoutManager(getContext());
                rv.setLayoutManager(manager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });*/

        adapter = new PostAdapterMultiplePaged();

        adapter.setThings(R.layout.post_no_foto, R.layout.post_foto, getContext(), new PostAdapterMultiplePaged.OnItemClickListener() {
            @Override
            public void onItemClick(Post obj, View btn, int position) {
                TextView counter = (TextView) btn;
                Likes like= new Likes();
                like.setPost(obj);
                like.setUsuario(user);

                doLike = serviceLike.createLike(like);
                doLike.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        getLikes = serviceLike.getLikes(obj.getIdpost());
                        getLikes.enqueue(new Callback<JsonResponse>() {
                            @Override
                            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                                JsonResponse jr = response.body();

                                if(jr != null){

                                    counter.setText(jr.getJson());
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonResponse> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                String titulo = "Nuevo like de " + user.getUsuario();
                String body = "En post " + obj.getContenido();

                sendNotification(obj.getUsuario().getPushId(), titulo, body, String.valueOf(obj.getIdpost()+user.getIdusuario()));
            }

        }) ;




        manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);



        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        mainObserver = (Observer<PagedList<Post>>) post -> adapter.submitList(post);

        postViewModel.postPagedList.observe(getViewLifecycleOwner(), mainObserver);
        rv.setAdapter(adapter);


        return view;
    }

    private void sendNotification(String pushId, String titulo, String body, String i) {

        NotificationMessage msg = new NotificationMessage(pushId, titulo, body, i);

        notify = serviceNotification.notify(msg);
        notify.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "No hay servicio", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == POST_CODE && resultCode == RESULT_OK){

            Gson gson = new Gson();
            String jsonPost = data.getStringExtra("post");

            Post post = gson.fromJson(jsonPost, Post.class);
            //lista.add(post);

            refresh();

        }

    }

    private void refresh(){

        if(postViewModel.postPagedList.hasObservers()){
            postViewModel.postPagedList.removeObserver(mainObserver);
        }

        mainObserver = (Observer<PagedList<Post>>) post -> adapter.submitList(post);

        postViewModel.postPagedList.observe(this, mainObserver);

    }
}
