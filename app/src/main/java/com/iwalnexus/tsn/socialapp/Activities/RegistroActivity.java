package com.iwalnexus.tsn.socialapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button reg;
    private Call<Void> createUserCall;
    private UsuarioApi serviceUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        user = findViewById(R.id.regName);
        pass = findViewById(R.id.regPass);
        reg = findViewById(R.id.Registrarse);

        serviceUsuario = API.api().create(UsuarioApi.class);

        reg.setOnClickListener(v -> {


            Usuario userAC = new Usuario();
            userAC.setUsuario(user.getText().toString());
            userAC.setPassword(pass.getText().toString());

            createUserCall = serviceUsuario.createUser(userAC);

            createUserCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast t= Toast.makeText(RegistroActivity.this,"Registrado",Toast.LENGTH_LONG);
                    t.show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });


        });
    }
}
