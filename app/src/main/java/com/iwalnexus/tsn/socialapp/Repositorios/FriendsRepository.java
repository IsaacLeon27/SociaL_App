package com.iwalnexus.tsn.socialapp.Repositorios;



import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.AmistadApi;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.AplicationClass.AplicationClass;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.Network.NetworkBoundResource;
import com.iwalnexus.tsn.socialapp.Network.Resource;
import com.iwalnexus.tsn.socialapp.Room.AmigosDAO;
import com.iwalnexus.tsn.socialapp.Room.RoomDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsRepository {


    AmistadApi service;
    private AmigosDAO amigosDAO;

    public FriendsRepository() {

        service = API.api().create(AmistadApi.class);

        RoomDatabase roomDB = Room.databaseBuilder(
                AplicationClass.getContext(),
                RoomDatabase.class,
                "SA_DB"
        ).fallbackToDestructiveMigration().build();

        amigosDAO = roomDB.getFriends();
    }

    public LiveData<Resource<List<Usuario>>> loadFriends(int id){


        return new NetworkBoundResource<List<Usuario>, List<Usuario>>(){

            @Override
            protected void saveCallResult(@NonNull List<Usuario> item) {
                amigosDAO.saveFriends(item);
            }

            @NonNull
            @Override
            protected LiveData<List<Usuario>> loadFromDb() {
                return amigosDAO.getFriends();
            }

            @NonNull
            @Override
            protected Call<List<Usuario>> createCall() {
                return service.getFriends(id);
            }
        }.getAsLiveData();
    }
}
