package com.iwalnexus.tsn.socialapp.Repositorios;



import androidx.lifecycle.MutableLiveData;

import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    MutableLiveData<List<Usuario>> lista;
    UsuarioApi service;

    public UserRepository() {
        service = API.api().create(UsuarioApi.class);
    }

    public MutableLiveData<List<Usuario>> loadUsers(){

        lista = new MutableLiveData<>();

        Call<List<Usuario>> call =  service.getAllUsers();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.isSuccessful()){
                    lista.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });

        return lista;
    }
}
