package com.iwalnexus.tsn.socialapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iwalnexus.tsn.socialapp.Api.API;
import com.iwalnexus.tsn.socialapp.Api.LikesApi;
import com.iwalnexus.tsn.socialapp.Entidades.Fotos;
import com.iwalnexus.tsn.socialapp.Entidades.JsonResponse;
import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostAdapterMultiple extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> lista;

    private int layout;
    private int layoutFotos;
    private Context context;
    private OnItemClickListener listener;


     public PostAdapterMultiple(List<Post> lista, int layout,int layoutFotos, Context context, OnItemClickListener listener) {

        this.lista = lista;
        this.layout = layout;
        this.layoutFotos = layoutFotos;
        this.context = context;
        this.listener = listener;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;

        switch (viewType){
            case 0:
                v= LayoutInflater.from(context).inflate(layout, parent, false);
                return new ViewHolder(v);
            case 1:
                v= LayoutInflater.from(context).inflate(layoutFotos, parent, false);
                return new ViewHolderFotos(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

         Post p = lista.get(position);

         if(p.getFotosList().isEmpty()){
             ((ViewHolder) holder).bind(lista.get(position), listener);
         }else {
             ((ViewHolderFotos) holder).bind(lista.get(position), listener);
         }

    }

    @Override
    public int getItemViewType(int position) {

        Post p = lista.get(position);

        if(p.getFotosList().isEmpty()){
           return 0;
        }else {
           return 1;
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public TextView contenido;
        public TextView likesCounter;

        public ImageView UserIcon;
        public ImageView BtnLike;
        private Call<JsonResponse> getLikes;
        private LikesApi serviceLike;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.UserName);
            contenido = itemView.findViewById(R.id.content);
            likesCounter = itemView.findViewById(R.id.likes);

            UserIcon =  itemView.findViewById(R.id.UserIcon);
            BtnLike =  itemView.findViewById(R.id.BtnLike);
            serviceLike = API.api().create(LikesApi.class);
        }

        public void bind(final Post obj, final OnItemClickListener listener) {


            getLikes = serviceLike.getLikes(obj.getIdpost());
            getLikes.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                    JsonResponse jr = response.body();

                    if(jr != null){

                        likesCounter.setText(jr.getJson());
                    }
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {

                }
            });

            this.userName.setText(obj.getUsuario().getUsuario());
            this.contenido.setText(obj.getContenido());

           // this.likesCounter.setText(obj.getUsuario().getUsuario());



            this.BtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(obj, likesCounter, getAdapterPosition());
                }
            });


        }




    }

    public class ViewHolderFotos extends RecyclerView.ViewHolder{

        public TextView userName;
        public TextView contenido;
        public TextView likesCounter;

        public ImageView UserIcon;
        public ImageView BtnLike;

        public RecyclerView rv;
        public FotoAdapter fotoAdapter;
        public RecyclerView.LayoutManager manager;
        private Call<JsonResponse> getLikes;
        private LikesApi serviceLike;

        public ViewHolderFotos(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.UserName);
            contenido = itemView.findViewById(R.id.content);
            likesCounter = itemView.findViewById(R.id.likes);
            rv = itemView.findViewById(R.id.rv_fotos);

            UserIcon =  itemView.findViewById(R.id.UserIcon);
            BtnLike =  itemView.findViewById(R.id.BtnLike);

            serviceLike = API.api().create(LikesApi.class);


        }

        public void bind(final Post obj, final OnItemClickListener listener) {


            getLikes = serviceLike.getLikes(obj.getIdpost());
            getLikes.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                    JsonResponse jr = response.body();

                    if(jr != null){

                        likesCounter.setText(jr.getJson());
                    }
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {

                }
            });


            this.userName.setText(obj.getUsuario().getUsuario());
            this.contenido.setText(obj.getContenido());

            fotoAdapter = new FotoAdapter(obj.getFotosList(), R.layout.foto, context, new FotoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Fotos obj, int position) {

                }
            });

            manager = new GridLayoutManager(context,2);
            rv.setLayoutManager(manager);
            rv.setAdapter(fotoAdapter);


            // this.likesCounter.setText(obj.getUsuario().getUsuario());



            this.BtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(obj, likesCounter, getAdapterPosition());
                }
            });


        }




    }

    public interface OnItemClickListener {
        void onItemClick(Post obj, View btn, int position);
    }

    public void updateList(List<Post> newLista){
        lista = new ArrayList<>();
        lista.addAll(newLista);
        notifyDataSetChanged();
    }

    public void setData(List<Post> data) {
        this.lista = data;
        notifyDataSetChanged();
    }
}
