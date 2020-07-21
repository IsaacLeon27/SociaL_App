package com.iwalnexus.tsn.socialapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iwalnexus.tsn.socialapp.Entidades.Fotos;
import com.iwalnexus.tsn.socialapp.Entidades.Usuario;
import com.iwalnexus.tsn.socialapp.R;

import java.util.ArrayList;
import java.util.List;


public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {

    private List<Fotos> lista;

    private int layout;
    private Context context;
    private OnItemClickListener listener;


     public FotoAdapter(List<Fotos> lista, int layout, Context context, OnItemClickListener listener) {

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


        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView =  itemView.findViewById(R.id.fotoItem);


        }

        public void bind(final Fotos obj, final OnItemClickListener listener) {


            Glide.with(context).load(obj.getUrl()).into(imageView);


        }




    }

    public interface OnItemClickListener {
        void onItemClick(Fotos obj, int position);
    }

    public void updateList(List<Fotos> newLista){
        lista = new ArrayList<>();
        lista.addAll(newLista);
        notifyDataSetChanged();
    }

    public void setData(List<Fotos> data) {
        this.lista = data;
        notifyDataSetChanged();
    }
}
