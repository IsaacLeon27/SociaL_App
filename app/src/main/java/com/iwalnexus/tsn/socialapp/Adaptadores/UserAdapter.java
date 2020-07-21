package com.iwalnexus.tsn.socialapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import java.util.ArrayList;
import java.util.List;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<Usuario> lista;

    private int layout;
    private Context context;
    private OnItemClickListener listener;


     public UserAdapter(List<Usuario> lista, int layout, Context context, OnItemClickListener listener) {

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

         if(lista != null){
             return lista.size();
         } else {
             return  0;
         }

    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageViewUserIcon;
        public Button friends;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.UserName);
            imageViewUserIcon =  itemView.findViewById(R.id.UserIcon);
            friends =  itemView.findViewById(R.id.FriendRequest);

        }

        public void bind(final Usuario user, final OnItemClickListener listener) {


            this.textViewName.setText(user.getUsuario());

            this.friends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(user, getAdapterPosition());
                }
            });


        }




    }

    public interface OnItemClickListener {
        void onItemClick(Usuario obj, int position);
    }

    public void updateList(List<Usuario> newLista){
        lista = new ArrayList<>();
        lista.addAll(newLista);
        notifyDataSetChanged();
    }

    public void setData(List<Usuario> data) {
        this.lista = data;
        notifyDataSetChanged();
    }
}
