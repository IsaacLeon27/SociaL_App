package com.iwalnexus.tsn.socialapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

import java.util.List;

@Dao
public interface AmigosDAO {

    @Query("SELECT * FROM Usuario")
    LiveData<List<Usuario>> getFriends();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveFriends(List<Usuario> lista);
}
