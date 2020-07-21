package com.iwalnexus.tsn.socialapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Adaptadores.ViewPager2Adapter;
import com.iwalnexus.tsn.socialapp.Adaptadores.ViewPager2PerfilAdapter;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;

import com.iwalnexus.tsn.socialapp.R;

public class PerfilActivity extends AppCompatActivity {


    public static final int TABPERFIL = 0;
    public static final int TABFRIENDS = 1;

    private ViewPager2 viewPager2;
    private ViewPager2PerfilAdapter adapter;
    private TabLayout tabLayout;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private SharedPreferences pref;
    private View header;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        pref = getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);



        setToolbar();
        setViewPager();
        setTabLayout();

    }




    private void setTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab,position)->{
                    switch (position){
                        case 0:
                            tab.setIcon(R.drawable.ic_user);
                            break;
                        case  1:
                            tab.setIcon(R.drawable.users);
                            break;
                    }
                }
        ).attach();
    }

    private void setViewPager() {

        viewPager2 = findViewById(R.id.viewPager2);
        adapter = new ViewPager2PerfilAdapter(this,2);
        viewPager2.setAdapter(adapter);
    }

    private void setToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


}