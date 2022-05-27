package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityAmigosBinding;
import com.caboca.sendit.databinding.ActivityPrivadosBinding;

import java.util.ArrayList;

public class Privados extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    static ActivityPrivadosBinding binding;
    public static ArrayList<String> elegirAmigoNuevoPrivado;
    static Controlador c = InterfazPrincipal.c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrivadosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.contenidoPrivados.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterPrivados();
        binding.contenidoPrivados.recyclerView.setAdapter(adapter);

        binding.btnElegirNuevoPrivado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.elegirAmigoCrearPrivado();
                if (elegirAmigoNuevoPrivado.isEmpty()) {
                    Toast.makeText(Privados.this, "YA TIENES UN CHAT PRIVADO CON TODOS TUS AMIGOS", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.spinnerElegirNuevoPrivado.getVisibility() == View.INVISIBLE) {
                        binding.spinnerElegirNuevoPrivado.setVisibility(View.VISIBLE);
                        binding.btnCrear.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_personalizado, elegirAmigoNuevoPrivado);
                        binding.spinnerElegirNuevoPrivado.setAdapter(spinnerAdapter);
                    } else {
                        binding.spinnerElegirNuevoPrivado.setVisibility(View.INVISIBLE);
                        binding.btnCrear.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        binding.btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoPrivado = binding.spinnerElegirNuevoPrivado.getSelectedItem().toString();
                c.crearPrivado(nuevoPrivado);
                recargarLista();
            }
        });
    }
    public static void recargarLista() {
        c.mostrarAmigos();
        adapter = new RecyclerAdapterPrivados();
        binding.contenidoPrivados.recyclerView.setAdapter(adapter);
        binding.spinnerElegirNuevoPrivado.setVisibility(View.INVISIBLE);
        binding.btnCrear.setVisibility(View.INVISIBLE);
    }
    @Override public void onBackPressed() {
        finish();
    }
}