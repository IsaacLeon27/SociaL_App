package com.iwalnexus.tsn.socialapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.Repositorios.UserRepository;

import java.util.List;


public class UserViewModel extends AndroidViewModel{

    private UserRepository userRepository;
    private LiveData<List<Usuario>> lista;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public LiveData<List<Usuario>> getUsers(){

        lista = userRepository.loadUsers();

        return lista;
    }
}
