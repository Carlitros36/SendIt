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
    private static RecyclerView.Adapter adapter;
    static ActivityMensajesGrupoBinding binding;
    public static String nombreGrupo;
    static Controlador c = InterfazPrincipal.c;
    static final Handler handler= new Handler();
    static Runnable myRunnable = new Runnable() {
        public void run() {

            c.mostrarMensajesGrupo(nombreGrupo);
            adapter = new RecyclerAdapterMensajesGrupo();
            binding.contenidoMensajes.recyclerView.setAdapter(adapter);
            int position = RecyclerAdapterMensajesGrupo.mensajes.size()-1;
            binding.contenidoMensajes.recyclerView.scrollToPosition(position);
            handler.postDelayed(this, TIEMPO);
        }
    };
    private final static int TIEMPO = 1000;


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
        handler.postDelayed(myRunnable, TIEMPO);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(myRunnable);
        Intent grupos = new Intent(MensajesGrupo.this, Grupos.class);
        grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(grupos);
    }
}