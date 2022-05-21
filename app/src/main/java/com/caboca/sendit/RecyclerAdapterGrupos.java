package com.caboca.sendit;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterGrupos extends RecyclerView.Adapter<RecyclerAdapterGrupos.ViewHolder> {

    static Controlador c = InterfazPrincipal.c;
    public static ArrayList<String> grupos;

    public RecyclerAdapterGrupos() {

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

        viewHolder.itemTitle.setText(grupos.get(position));
    }

    @Override
    public int getItemCount() {
        return grupos.size();
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
                    c.mostrarMensajesGrupo(grupos.get(position));
                    MensajesGrupo.nombreGrupo=grupos.get(position);
                    Intent intent = new Intent(v.getContext(), MensajesGrupo.class);
                    //intent.putExtra("grupos", grupos.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}