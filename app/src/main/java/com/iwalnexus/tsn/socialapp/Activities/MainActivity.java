package com.iwalnexus.tsn.socialapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Adaptadores.ViewPager2Adapter;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int TABPOST = 0;
    public static final int TABUSER = 1;

    private ViewPager2 viewPager2;
    private ViewPager2Adapter adapter;
    private TabLayout tabLayout;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private SharedPreferences pref;
    private View header;
    private Usuario user;

    public static final String CHANNEL = "Canal1";
    public static final String CHANNELID = "1";

    private UsuarioApi service;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        pref = getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);

        prepareDrawer();

        setToolbar();
         setViewPager();
        setTabLayout();

        fillUserData();
        createChannels();
        saveTokenOnServer();

    }

    private void saveTokenOnServer() {

        service = API.api().create(UsuarioApi.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = task.getResult().getToken();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("token", token);
                editor.apply();

                user.setPushId(token);

                Call<Void> setPushId = service .setPushId(user);
                setPushId.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

               Gson gson = new Gson();
               editor.putString("user", gson.toJson(user));
               editor.apply();
            }
        });
    }

    private void createChannels() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Este es el canal de notificaciones principal");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }

    private void fillUserData() {

       ImageView icon = header.findViewById(R.id.UserIconNav);
       TextView name =  header.findViewById(R.id.userName);
        name.setText(user.getUsuario());

        if(user.getIcon() != null){

            Glide.with(this).load(user.getIcon()).into(icon);

        }

    }

    private void prepareDrawer() {

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

            }

    private void setTabLayout() {
 tabLayout = findViewById(R.id.tabLayout);

 new TabLayoutMediator(tabLayout, viewPager2,
         (tab,position)->{
     switch (position){
         case 0:
             tab.setIcon(R.drawable.post);
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
        adapter = new ViewPager2Adapter(this,2);
        viewPager2.setAdapter(adapter);
    }

    private void setToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case R.id.Perfil:
                Intent i = new Intent(this, PerfilActivity.class);
                startActivity(i);
                break;

            case R.id.Salir:

                SharedPreferences.Editor editor = pref.edit();
                editor.remove("user");
                editor.apply();

                Intent intent = new Intent(MainActivity.this,LogingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
