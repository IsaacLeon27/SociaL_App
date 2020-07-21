package com.iwalnexus.tsn.socialapp.Fragmentos;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Adaptadores.UserAdapter;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.AmistadApi;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.Amigos;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;
import com.iwalnexus.tsn.socialapp.ViewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private List<Usuario> lista;

    private UsuarioApi serviceUser;
    private Call<List<Usuario>> getUsersCall;

    private RecyclerView rv;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private AmistadApi servicioAmistad;
    private Call<Void> createFriendship;

    private SharedPreferences pref;
    private Usuario user;

    private UserViewModel userViewModel;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        pref = getActivity().getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);
        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);

        rv = view.findViewById(R.id.RvUsers);

        serviceUser = API.api().create(UsuarioApi.class);
        servicioAmistad = API.api().create(AmistadApi.class);

        /*getUsersCall = serviceUser.getAllUsers();

        getUsersCall.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                lista = response.body();

                adapter = new UserAdapter(lista, R.layout.user_item, getContext(), (obj, position) -> {

                    Amigos friendShip = new Amigos();
                    friendShip.setEmisor(user);
                    friendShip.setReceptor(obj);

                    createFriendship = servicioAmistad.createFriend(friendShip);
                    createFriendship.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getContext(),"Ya son amigos", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });*/


        adapter = new UserAdapter(lista, R.layout.user_item, getContext(), (obj, position) -> {

            Amigos friendShip = new Amigos();
            friendShip.setEmisor(user);
            friendShip.setReceptor(obj);

            createFriendship = servicioAmistad.createFriend(friendShip);
            createFriendship.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getContext(),"Ya son amigos", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                lista = usuarios;
                adapter.setData(lista);
            }
        });


        return view;
    }

}
