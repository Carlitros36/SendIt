package com.caboca.sendit;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterMensajesPrivado extends RecyclerView.Adapter<RecyclerAdapterMensajesPrivado.ViewHolder> {


    public static ArrayList<String> usuarios;
    public static ArrayList<String> mensajes;
    Controlador c = InterfazPrincipal.c;
    public RecyclerAdapterMensajesPrivado() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int
            viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mensajes_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.itemDetail.setText(mensajes.get(position));
        String usuario = usuarios.get(position);

        if (usuario.equals(c.usuarioLogeado)) {
            viewHolder.itemTitle.setText("Yo");
            viewHolder.itemTitle.setGravity(Gravity.RIGHT);
            viewHolder.itemDetail.setGravity(Gravity.RIGHT);
        }else{
            viewHolder.itemTitle.setGravity(Gravity.LEFT);
            viewHolder.itemDetail.setGravity(Gravity.LEFT);
            if (c.comprobarAmistad(usuario)){
                viewHolder.itemTitle.setText(usuario);
            }else{
                int id_usuario = c.buscarIDPorNombre(usuario);
                viewHolder.itemTitle.setText(String.valueOf(id_usuario));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDetail;
        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    intent.putExtra("amigos", mensajes.get(position));
                    v.getContext().startActivity(intent);
                }
            });*/
        }
    }
}