package com.iwalnexus.tsn.socialapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences pref;
    private Usuario user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, LogingActivity.class);
        Intent intentMain = new Intent(this, MainActivity.class);

        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);

        if(user != null){
            startActivity(intentMain);
        } else {
            startActivity(intentLogin);
        }


    }
}
