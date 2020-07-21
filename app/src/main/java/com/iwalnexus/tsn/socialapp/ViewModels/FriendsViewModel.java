package com.iwalnexus.tsn.socialapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.Network.Resource;
import com.iwalnexus.tsn.socialapp.Repositorios.FriendsRepository;
import com.iwalnexus.tsn.socialapp.Repositorios.UserRepository;

import java.util.List;


public class FriendsViewModel extends AndroidViewModel{

    private FriendsRepository friendsRepository;
    private LiveData<Resource<List<Usuario>>> lista;

    public FriendsViewModel(@NonNull Application application) {
        super(application);
        friendsRepository = new FriendsRepository();
    }

    public LiveData<Resource<List<Usuario>>> getFriends(int id){

        lista = friendsRepository.loadFriends(id);

        return lista;
    }
}
