package com.caboca.sendit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterPrivados extends RecyclerView.Adapter<RecyclerAdapterPrivados.ViewHolder> {

    static Controlador c = InterfazPrincipal.c;
    public static ArrayList<String> privados;
    public static ArrayList<Integer> id_chats;

    public RecyclerAdapterPrivados() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int
            viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String usuario = privados.get(position);
        if(c.comprobarAmistad(usuario)){
            viewHolder.itemTitle.setText(usuario);
        }else{
            int id_usuario = c.buscarIDPorNombre(usuario);
            viewHolder.itemTitle.setText(String.valueOf(id_usuario));
        }
    }

    @Override
    public int getItemCount() {
        return privados.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDetail;
        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    c.mostrarMensajesPrivado(id_chats.get(position));
                    MensajesPrivado.id_chat = id_chats.get(position);
                    String usuario = privados.get(position);
                    if(c.comprobarAmistad(usuario)){
                        MensajesPrivado.usuarioConversacion = usuario;
                    }else{
                        int id_usuario = c.buscarIDPorNombre(usuario);
                        MensajesPrivado.usuarioConversacion = String.valueOf(id_usuario);
                    }
                    Intent intent = new Intent(v.getContext(), MensajesPrivado.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}