package com.caboca.sendit;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterAmigos extends RecyclerView.Adapter<RecyclerAdapterAmigos.ViewHolder> {


    public static ArrayList<String> amigos;
    static Controlador c = InterfazPrincipal.c;
    public RecyclerAdapterAmigos() {

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

        viewHolder.itemTitle.setText(amigos.get(position));
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDetail;
        Button btnBorrarAmigo;
        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            btnBorrarAmigo = itemView.findViewById(R.id.btnBorrar);
            btnBorrarAmigo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(v.getContext());
                    dialogo1.setTitle("CUIDADO");
                    dialogo1.setMessage("¿Seguro que desea eliminar a "+ amigos.get(position) +" de su lista de amigos?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            c.eliminarAmigo(amigos.get(position));
                            Amigos.recargarLista();
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.cancel();
                        }
                    });
                    dialogo1.show();
                }
            });
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    intent.putExtra("amigos", amigos.get(position));
                    v.getContext().startActivity(intent);

                }
            });*/
        }
    }
}