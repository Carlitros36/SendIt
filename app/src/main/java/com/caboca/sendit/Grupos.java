package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.controls.Control;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityAmigosBinding;
import com.caboca.sendit.databinding.ActivityGruposBinding;

public class Grupos extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ActivityGruposBinding binding;
    static Controlador c = InterfazPrincipal.c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGruposBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.contenidoGrupos.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterGrupos();
        binding.contenidoGrupos.recyclerView.setAdapter(adapter);
        binding.editTextNuevoGrupo.setVisibility(View.INVISIBLE);
        binding.btnCrearGrupo.setVisibility(View.INVISIBLE);



        binding.btnOpcionesCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.btnOpcionesCrearGrupo.getText().equals("MOSTRAR OPCIONES CREAR GRUPO")){
                    binding.btnOpcionesCrearGrupo.setText("OCULTAR OPCIONES CREAR GRUPO");
                }else{
                    binding.btnOpcionesCrearGrupo.setText("MOSTRAR OPCIONES CREAR GRUPO");
                }
                if (binding.editTextNuevoGrupo.getVisibility()==View.VISIBLE){
                    binding.editTextNuevoGrupo.setVisibility(View.INVISIBLE);
                    binding.btnCrearGrupo.setVisibility(View.INVISIBLE);
                }else {
                    binding.editTextNuevoGrupo.setVisibility(View.VISIBLE);
                    binding.btnCrearGrupo.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.btnCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoGrupo = binding.editTextNuevoGrupo.getText().toString().trim();
                if (nuevoGrupo.isEmpty()){
                    Toast.makeText(Grupos.this, "Debe introducir un nombre para el nuevo grupo", Toast.LENGTH_SHORT).show();
                }else{
                    c.crearGrupo(nuevoGrupo, getApplicationContext());
                    c.mostrarGrupos();
                    adapter = new RecyclerAdapterGrupos();
                    binding.contenidoGrupos.recyclerView.setAdapter(adapter);
                    binding.editTextNuevoGrupo.setText("");
                    binding.editTextNuevoGrupo.setVisibility(View.INVISIBLE);
                    binding.btnCrearGrupo.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override public void onBackPressed() {
        finish();
    }
}