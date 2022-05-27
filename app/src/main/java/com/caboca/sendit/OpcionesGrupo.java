package com.caboca.sendit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityGruposBinding;
import com.caboca.sendit.databinding.ActivityOpcionesGrupoBinding;

import java.util.ArrayList;

public class OpcionesGrupo extends AppCompatActivity {

    static ActivityOpcionesGrupoBinding binding;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    static Controlador c = InterfazPrincipal.c;
    public static String nombreGrupo;
    public static ArrayList<String> elegirNuevoParticiante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOpcionesGrupoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.participantes.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterParticipantesGrupo();
        binding.participantes.recyclerView.setAdapter(adapter);
        binding.textViewNombreGrupo.setText(nombreGrupo);

        if(c.buscarNombreAdmin(nombreGrupo).equals(c.usuarioLogeado)){
            binding.btnSalirGrupo.setVisibility(View.INVISIBLE);
            binding.btnSalirGrupo.setEnabled(false);
            binding.btnEliminarGrupo.setVisibility(View.VISIBLE);
            binding.btnEliminarGrupo.setEnabled(true);
            binding.btnElegirAgregarParticipante.setVisibility(View.VISIBLE);
            binding.btnElegirAgregarParticipante.setEnabled(true);
            /*binding.btnEliminarParticipante.setVisibility(View.VISIBLE);
            binding.btnEliminarParticipante.setEnabled(true);*/
        } else{
            binding.btnSalirGrupo.setVisibility(View.VISIBLE);
            binding.btnSalirGrupo.setEnabled(true);
            binding.btnEliminarGrupo.setVisibility(View.INVISIBLE);
            binding.btnEliminarGrupo.setEnabled(false);
            binding.btnElegirAgregarParticipante.setVisibility(View.INVISIBLE);
            binding.btnElegirAgregarParticipante.setEnabled(false);
            /*binding.btnEliminarParticipante.setVisibility(View.INVISIBLE);
            binding.btnEliminarParticipante.setEnabled(false);*/
        }

        binding.btnElegirAgregarParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.elegirNuevoParticipante(nombreGrupo);
                if (elegirNuevoParticiante.isEmpty()){
                    Toast.makeText(OpcionesGrupo.this, "TODOS LOS USUARIOS PERTENECEN AL GRUPO", Toast.LENGTH_SHORT).show();
                }else {
                    if (binding.spinnerElegirNuevoParticipante.getVisibility()==View.INVISIBLE){
                        binding.spinnerElegirNuevoParticipante.setVisibility(View.VISIBLE);
                        binding.btnAgregarParticipante.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_personalizado, elegirNuevoParticiante);
                        binding.spinnerElegirNuevoParticipante.setAdapter(spinnerAdapter);
                    }
                }

            }
        });
        binding.btnAgregarParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoParticipante = binding.spinnerElegirNuevoParticipante.getSelectedItem().toString();
                c.agregarParticipanteGrupo(nombreGrupo, nuevoParticipante);
                recargarParticipantes();
            }
        });
        binding.btnEliminarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(OpcionesGrupo.this);
                dialogo1.setTitle("CUIDADO");
                dialogo1.setMessage("¿Seguro que desea eliminar el grupo permanentemente?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.eliminarGrupo(nombreGrupo);
                        c.mostrarGrupos();
                        MensajesGrupo.handler.removeCallbacks(MensajesGrupo.myRunnable);
                        Intent grupos = new Intent(OpcionesGrupo.this, Grupos.class);
                        grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(grupos);
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
        binding.btnSalirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(OpcionesGrupo.this);
                dialogo1.setTitle("CUIDADO");
                dialogo1.setMessage("¿Seguro que desea salir del grupo permanentemente?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.eliminarParticipanteGrupo(nombreGrupo, c.usuarioLogeado);
                        c.mostrarGrupos();
                        MensajesGrupo.handler.removeCallbacks(MensajesGrupo.myRunnable);
                        Intent grupos = new Intent(OpcionesGrupo.this, Grupos.class);
                        grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(grupos);
                        finish();
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
    }

    public static void recargarParticipantes(){
        c.mostrarParticipantesGrupo(nombreGrupo);
        adapter = new RecyclerAdapterParticipantesGrupo();
        binding.participantes.recyclerView.setAdapter(adapter);
        binding.spinnerElegirNuevoParticipante.setVisibility(View.INVISIBLE);
        binding.btnAgregarParticipante.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent mensajesGrupo = new Intent(OpcionesGrupo.this, MensajesGrupo.class);
        mensajesGrupo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mensajesGrupo);    }
}