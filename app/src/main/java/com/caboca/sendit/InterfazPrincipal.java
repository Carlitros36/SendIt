package com.caboca.sendit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ContentInfoCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityInterfazPrincipalBinding;

import java.util.ArrayList;

public class InterfazPrincipal extends AppCompatActivity {

    static Controlador c = new Controlador();
    ActivityInterfazPrincipalBinding binding;
    public static String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInterfazPrincipalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.textViewBienvenida.setText("Bienvenid@ " + usuario);
        c.usuarioLogeado = usuario;
        binding.btnListaAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.mostrarAmigos();
                Intent amigos = new Intent(InterfazPrincipal.this, Amigos.class);
                startActivity(amigos);
            }
        });
        binding.btnGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.mostrarGrupos();
                Intent grupos = new Intent(InterfazPrincipal.this, Grupos.class);
                startActivity(grupos);
            }
        });
        binding.btnPrivados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.mostrarPrivados();
                Intent privados = new Intent(InterfazPrincipal.this, Privados.class);
                startActivity(privados);
            }
        });
        binding.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(InterfazPrincipal.this);
                dialogo1.setTitle("ATENCIÓN");
                dialogo1.setMessage("¿Seguro que desea cerrar sesión?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        ConexionPSQL.desconectar();
                        Intent login = new Intent(InterfazPrincipal.this, LoginActivity.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //finishAffinity();
                        startActivity(login);
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
        binding.btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(InterfazPrincipal.this);
                dialogo1.setTitle("CUIDADO");
                dialogo1.setMessage("¿Seguro que desea eliminar su cuenta permanentemente?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.eliminarCuenta();
                        Intent login = new Intent(InterfazPrincipal.this, LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}