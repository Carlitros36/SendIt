package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityMensajesGrupoBinding;

public class MensajesGrupo extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ActivityMensajesGrupoBinding binding;
    public static String nombreGrupo;
    Controlador c = InterfazPrincipal.c;
    final Handler handler= new Handler();
    private final int TIEMPO = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMensajesGrupoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.contenidoMensajes.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterMensajesGrupo();
        binding.contenidoMensajes.recyclerView.setAdapter(adapter);
        binding.btnGrupoActual.setText(nombreGrupo);
        int position = RecyclerAdapterMensajesGrupo.mensajes.size()-1;
        binding.contenidoMensajes.recyclerView.scrollToPosition(position);

        c.mostrarMensajesGrupo(nombreGrupo);

        binding.btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = binding.editTextEnviarMensaje.getText().toString().trim();
                if(mensaje.isEmpty()){
                    Toast.makeText(MensajesGrupo.this, "No puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                } else{
                    c.enviarMensajeGrupo(nombreGrupo, mensaje);
                    c.mostrarMensajesGrupo(nombreGrupo);
                    adapter = new RecyclerAdapterMensajesGrupo();
                    binding.contenidoMensajes.recyclerView.setAdapter(adapter);
                    int position = RecyclerAdapterMensajesGrupo.mensajes.size()-1;
                    binding.contenidoMensajes.recyclerView.scrollToPosition(position);
                    binding.editTextEnviarMensaje.setText("");
                }
            }
        });
        binding.btnGrupoActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpcionesGrupo.nombreGrupo = nombreGrupo;
                c.mostrarParticipantesGrupo(nombreGrupo);
                Intent opcionesGrupo = new Intent(MensajesGrupo.this, OpcionesGrupo.class);
                startActivity(opcionesGrupo);
            }
        });
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                c.mostrarMensajesGrupo(nombreGrupo);
                adapter = new RecyclerAdapterMensajesGrupo();
                binding.contenidoMensajes.recyclerView.setAdapter(adapter);
                int position = RecyclerAdapterMensajesGrupo.mensajes.size()-1;
                binding.contenidoMensajes.recyclerView.scrollToPosition(position);
                //binding.editTextEnviarMensaje.setText("");
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);
    }
}