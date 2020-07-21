package com.iwalnexus.tsn.socialapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iwalnexus.tsn.socialapp.Entidades.Post;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> lista;

    private int layout;
    private Context context;
    private OnItemClickListener listener;


     public PostAdapter(List<Post> lista, int layout, Context context, OnItemClickListener listener) {

        this.lista = lista;
        this.layout = layout;
        this.context = context;
        this.listener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(lista.get(position), listener);
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

        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.UserName);
            contenido = itemView.findViewById(R.id.content);
            likesCounter = itemView.findViewById(R.id.likes);

            UserIcon =  itemView.findViewById(R.id.UserIcon);
            BtnLike =  itemView.findViewById(R.id.BtnLike);

        }

        public void bind(final Post obj, final OnItemClickListener listener) {


            this.userName.setText(obj.getUsuario().getUsuario());
            this.contenido.setText(obj.getContenido());

           // this.likesCounter.setText(obj.getUsuario().getUsuario());



            this.BtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(obj, getAdapterPosition());
                }
            });


        }




    }

    public interface OnItemClickListener {
        void onItemClick(Post obj, int position);
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
