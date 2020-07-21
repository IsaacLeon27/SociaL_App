package com.iwalnexus.tsn.socialapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogingActivity extends AppCompatActivity {

    private EditText userED;
    private EditText passED;
    private Button loginBtn;
    private Button regBtn;

    private SharedPreferences pref;

    private UsuarioApi service;
    private Call<Usuario> authCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        userED = findViewById(R.id.logUser);
        passED = findViewById(R.id.logPass);
        loginBtn = findViewById(R.id.login);
        regBtn = findViewById(R.id.registarse);

        pref= getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        service = API.api().create(UsuarioApi.class);

        loginBtn.setOnClickListener(v -> {

            authCall = service.auth(userED.getText().toString(), passED.getText().toString());
            authCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    Usuario userAC = response.body();

                    if(userAC != null){
                        Gson gson = new Gson();
                        String UserJson = gson.toJson(userAC);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("user", UserJson);
                        editor.apply();


                        Intent intent = new Intent(LogingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });


        });

        regBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LogingActivity.this, RegistroActivity.class);
            startActivity(intent);
        });



    }
}
