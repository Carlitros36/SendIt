package com.caboca.sendit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityMensajesPrivadoBinding;

public class MensajesPrivado extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ActivityMensajesPrivadoBinding binding;
    public static String usuarioConversacion;
    public static int id_chat;
    Controlador c = InterfazPrincipal.c;
    final Handler handler= new Handler();
    private final int TIEMPO = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMensajesPrivadoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        binding.contenidoMensajes.recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterMensajesPrivado();
        binding.contenidoMensajes.recyclerView.setAdapter(adapter);

        binding.textViewNombrePrivado.setText(usuarioConversacion);

        c.mostrarMensajesPrivado(id_chat);
        binding.btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = binding.editTextEnviarMensaje.getText().toString().trim();
                if(mensaje.isEmpty()){
                    Toast.makeText(MensajesPrivado.this, "No puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                }else {
                    c.enviarMensajePrivado(id_chat, mensaje);
                    c.mostrarMensajesPrivado(id_chat);
                    adapter = new RecyclerAdapterMensajesPrivado();
                    binding.contenidoMensajes.recyclerView.setAdapter(adapter);
                    int position = RecyclerAdapterMensajesPrivado.mensajes.size() - 1;
                    binding.contenidoMensajes.recyclerView.scrollToPosition(position);
                    binding.editTextEnviarMensaje.setText("");
                }
            }
        });

        binding.btnEliminarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MensajesPrivado.this);
                dialogo1.setTitle("CUIDADO");
                String usuario = binding.textViewNombrePrivado.getText().toString();
                dialogo1.setMessage("¿Seguro que desea eliminar el chat privado con "+ usuario +" permanentemente?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.eliminarChatPrivado(id_chat);
                        c.mostrarPrivados();
                        Intent privados = new Intent(MensajesPrivado.this, Privados.class);
                        privados.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(privados);
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
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                c.mostrarMensajesPrivado(id_chat);
                adapter = new RecyclerAdapterMensajesPrivado();
                binding.contenidoMensajes.recyclerView.setAdapter(adapter);
                int position = RecyclerAdapterMensajesPrivado.mensajes.size()-1;
                binding.contenidoMensajes.recyclerView.scrollToPosition(position);
                //binding.editTextEnviarMensaje.setText("");
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);
    }
}