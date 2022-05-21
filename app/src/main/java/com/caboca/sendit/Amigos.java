package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityAmigosBinding;

import java.util.ArrayList;

public class Amigos extends AppCompatActivity {

    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    static ActivityAmigosBinding binding;
    public static ArrayList<String> elegirAmigoAgregar;
    static Controlador c = InterfazPrincipal.c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAmigosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.contenidoAmigos.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterAmigos();
        binding.contenidoAmigos.recyclerView.setAdapter(adapter);

        binding.btnAgregarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.elegirNuevoAmigo();
                if (elegirAmigoAgregar.isEmpty()) {
                    Toast.makeText(Amigos.this, "TODOS SON TUS AMIGOS!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.spinnerElegirAmigoAgregar.getVisibility() == View.INVISIBLE) {
                        binding.spinnerElegirAmigoAgregar.setVisibility(View.VISIBLE);
                        binding.btnAgregar.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, elegirAmigoAgregar);
                        binding.spinnerElegirAmigoAgregar.setAdapter(spinnerAdapter);
                    } else {
                        binding.spinnerElegirAmigoAgregar.setVisibility(View.INVISIBLE);
                        binding.btnAgregar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        /*binding.btnEliminarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.elegirAmigoEliminar();
                if (elegirAmigoBorrar.isEmpty()) {
                    Toast.makeText(Amigos.this, "NO TIENES NINGÚN AMIGO EN TU LISTA", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.spinnerElegirAmigoBorrar.getVisibility() == View.INVISIBLE) {
                        binding.spinnerElegirAmigoAgregar.setVisibility(View.INVISIBLE);
                        binding.btnAgregar.setVisibility(View.INVISIBLE);
                        binding.spinnerElegirAmigoBorrar.setVisibility(View.VISIBLE);
                        binding.btnBorrar.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, elegirAmigoBorrar);
                        binding.spinnerElegirAmigoBorrar.setAdapter(spinnerAdapter);

                    } else {
                        binding.spinnerElegirAmigoAgregar.setVisibility(View.INVISIBLE);
                        binding.btnAgregar.setVisibility(View.INVISIBLE);
                        binding.spinnerElegirAmigoBorrar.setVisibility(View.INVISIBLE);
                        binding.btnBorrar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });*/

        binding.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoAmigo = binding.spinnerElegirAmigoAgregar.getSelectedItem().toString();
                c.agregarAmigo(nuevoAmigo);
                recargarLista();
                /*c.mostrarAmigos();
                adapter = new RecyclerAdapterAmigos();
                binding.contenidoAmigos.recyclerView.setAdapter(adapter);
                c.elegirNuevoAmigo();
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, elegirAmigoAgregar);
                binding.spinnerElegirAmigoAgregar.setAdapter(spinnerAdapter);
                binding.spinnerElegirAmigoAgregar.setVisibility(View.INVISIBLE);
                binding.btnAgregar.setVisibility(View.INVISIBLE);*/
            }
        });

        /*binding.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elegirAmigoBorrar.isEmpty()) {
                    Toast.makeText(Amigos.this, "NO TIENES NINGÚN AMIGO EN TU LISTA", Toast.LENGTH_SHORT).show();
                } else {
                    String amigoBorrar = binding.spinnerElegirAmigoBorrar.getSelectedItem().toString();
                    c.eliminarAmigo(amigoBorrar);
                    c.mostrarAmigos();
                    adapter = new RecyclerAdapterAmigos();
                    binding.contenidoAmigos.recyclerView.setAdapter(adapter);
                    c.elegirAmigoEliminar();
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, elegirAmigoBorrar);
                    binding.spinnerElegirAmigoBorrar.setAdapter(spinnerAdapter);
                }
            }
        });*/
    }

    public static void recargarLista() {
        c.mostrarAmigos();
        adapter = new RecyclerAdapterAmigos();
        binding.contenidoAmigos.recyclerView.setAdapter(adapter);
        binding.spinnerElegirAmigoAgregar.setVisibility(View.INVISIBLE);
        binding.btnAgregar.setVisibility(View.INVISIBLE);
    }

}