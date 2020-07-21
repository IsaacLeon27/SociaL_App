package com.iwalnexus.tsn.socialapp.Room;

import androidx.room.Database;

import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract AmigosDAO getFriends();

}
