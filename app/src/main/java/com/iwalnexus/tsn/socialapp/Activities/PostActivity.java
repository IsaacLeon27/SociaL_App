package com.iwalnexus.tsn.socialapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.PostApi;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;
import com.iwalnexus.tsn.socialapp.Util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gun0912.tedpermission.TedPermissionBase.isGranted;

public class PostActivity extends AppCompatActivity {


    private EditText content;
    private Button publish;
    private ImageView imgBtn;

    private SharedPreferences pref;
    private Gson gson;

    private Usuario user;

    private PostApi service;
    private Call<JsonResponse> callCreatePost;
    PermissionListener permissionlistener;

    List<File> fotos = new ArrayList<>();

    List<MultipartBody.Part> listaMultipart;

    private static final int PICS_REQUEST_CODE = 1;


    Post post = new Post();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        content = findViewById(R.id.content);
        publish = findViewById(R.id.postBtn);
        imgBtn  = findViewById(R.id.btnFotos);

        service = API.api().create(PostApi.class);

        gson = new Gson();

        pref = getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        user = gson.fromJson(pref.getString("user",""), Usuario.class);


        publish.setOnClickListener(v -> {


            post.setContenido(content.getText().toString());
            post.setUsuario(user);

            listaMultipart = new ArrayList<>();

            for(File f : fotos){
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", f.getName(), RequestBody.create(MediaType.parse("image/jpeg"), f));
                listaMultipart.add(filePart);
            }

            //String jsonEntity = gson.toJson(post);

            callCreatePost = service.cretaePostAIO(listaMultipart,post);

            callCreatePost.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                    JsonResponse js = response.body();

                    post = gson.fromJson(js.getJson(),Post.class);

                    Intent postIntent = new Intent();
                    postIntent.putExtra("post", gson.toJson(post));
                    setResult(RESULT_OK, postIntent);
                    finish();
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {

                }
            });

        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isGranted(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(PostActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        PostActivity.this.startActivityForResult(intent, PICS_REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        PostActivity.this.startActivityForResult(intent, PICS_REQUEST_CODE);
                    }


                } else {
                    TedPermission.with(PostActivity.this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Si rechazas no podras subir imagenes")
                            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .check();
                }
            }
        });



        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(PostActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(PostActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICS_REQUEST_CODE && resultCode == RESULT_OK){

            if(data.getClipData() != null){

                for (int i =0; i< data.getClipData().getItemCount(); i++){

                    Uri uri = data.getClipData().getItemAt(i).getUri();

                    String PathHolder = FileUtils.getRealPath(PostActivity.this, uri);

                    File file = new File(PathHolder);
                    fotos.add(file);

                }
            } else {
                Uri uri = data.getData();
                String PathHolder = FileUtils.getRealPath(PostActivity.this, uri);

                File file = new File(PathHolder);
                fotos.add(file);
            }

        }

    }
}
