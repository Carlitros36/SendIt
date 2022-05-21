package com.caboca.sendit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterParticipantesGrupo extends RecyclerView.Adapter<RecyclerAdapterParticipantesGrupo.ViewHolder> {


    public static ArrayList<String> participantes;
    static Controlador c = InterfazPrincipal.c;

    public RecyclerAdapterParticipantesGrupo() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int
            viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.persona_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        String participante = participantes.get(position);
        viewHolder.itemTitle.setText(participante);
        String admin = c.buscarNombreAdmin(OpcionesGrupo.nombreGrupo);
        if(participante.equals(admin)){
            viewHolder.btnEliminarParticipante.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return participantes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDetail;
        Button btnEliminarParticipante;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            btnEliminarParticipante = itemView.findViewById(R.id.btnBorrar);
            String admin = c.buscarNombreAdmin(OpcionesGrupo.nombreGrupo);
            if (!admin.equals(c.usuarioLogeado)) {
                btnEliminarParticipante.setVisibility(View.INVISIBLE);
            } else {
                btnEliminarParticipante.setVisibility(View.VISIBLE);
            }
            btnEliminarParticipante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String grupo = OpcionesGrupo.nombreGrupo;
                    int position = getAdapterPosition();
                    String participante = participantes.get(position);
                    if(!participante.equals(admin)){
                        c.eliminarParticipanteGrupo(grupo, participante);
                        OpcionesGrupo.recargarParticipantes();
                    }else{
                        OpcionesGrupo.recargarParticipantes();
                    }
                }
            });
        }
    }
}