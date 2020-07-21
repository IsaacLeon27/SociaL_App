package com.iwalnexus.tsn.socialapp.Fragmentos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.iwalnexus.tsn.socialapp.Activities.PostActivity;
import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.UsuarioApi;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;
import com.iwalnexus.tsn.socialapp.Util.FileUtils;

import org.w3c.dom.Text;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private SharedPreferences pref;
    private Usuario user;

    private UsuarioApi serviceUser;
    private Call<JsonResponse> uploadIcon;

    PermissionListener permissionlistener;
    List<File> fotos = new ArrayList<>();

    List<MultipartBody.Part> listaMultipart;

    private static final int PICS_REQUEST_CODE = 1;

    private ImageView icon;
    private TextView name;

    private Button upload;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        pref = getActivity().getSharedPreferences("SocialAppPref", Context.MODE_PRIVATE);

        String userString = pref.getString("user", "");
        Gson gson = new Gson();

        user = gson.fromJson(userString, Usuario.class);

        icon = view.findViewById(R.id.Icon);
        name = view.findViewById(R.id.name);
        upload = view.findViewById(R.id.ChangeIcon);


        name.setText(user.getUsuario());

        if(user.getIcon() != null){
            Glide.with(getContext()).load(user.getIcon()).into(icon);
        }


        serviceUser = API.api().create(UsuarioApi.class);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isGranted(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                        startActivityForResult(intent, PICS_REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                       startActivityForResult(intent, PICS_REQUEST_CODE);
                    }


                } else {
                    TedPermission.with(getContext())
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
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICS_REQUEST_CODE && resultCode == getActivity().RESULT_OK){

            if(data.getClipData() != null){

                for (int i =0; i< data.getClipData().getItemCount(); i++){

                    Uri uri = data.getClipData().getItemAt(i).getUri();

                    String PathHolder = FileUtils.getRealPath(getContext(), uri);

                    File file = new File(PathHolder);
                    fotos.add(file);

                    saveIcon();

                }
            } else {
                Uri uri = data.getData();
                String PathHolder = FileUtils.getRealPath(getContext(), uri);

                File file = new File(PathHolder);
                fotos.add(file);

                saveIcon();
            }

        }

    }

    private void saveIcon() {

        listaMultipart = new ArrayList<>();

        for(File f : fotos){
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", f.getName(), RequestBody.create(MediaType.parse("image/jpeg"), f));
            listaMultipart.add(filePart);
        }

        uploadIcon = serviceUser.uploadIcon(listaMultipart, user);

        uploadIcon.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                JsonResponse jr = response.body();
                Gson gson = new Gson();

                if(jr != null){

                    Usuario updatedUser = gson.fromJson(jr.getJson(), Usuario.class);
                    String UserJson = gson.toJson(updatedUser);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user", UserJson);
                    editor.apply();

                    if(updatedUser.getIcon() != null){
                        Glide.with(getContext()).load(updatedUser.getIcon()).into(icon);
                    }
                }



            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });

    }

}
