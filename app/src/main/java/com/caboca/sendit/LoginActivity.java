package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityInterfazPrincipalBinding;
import com.caboca.sendit.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    ActivityInterfazPrincipalBinding bindingPrincipal;
    static Controlador c = new Controlador();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextUser.getText().toString().trim().isEmpty()||binding.editTextPasswd.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Debe rellenar los datos para iniciar sesión correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    String user = binding.editTextUser.getText().toString();
                    String pass = binding.editTextPasswd.getText().toString();
                    String passEncript = c.encriptarPassword(pass);
                    if (c.comprobarLogin(user, passEncript) == true) {
                        Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                        Intent interfazPrincipal = new Intent(LoginActivity.this, InterfazPrincipal.class);
                        startActivity(interfazPrincipal);
                        //finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro= new Intent(LoginActivity.this, RegistroUsuario.class);
                startActivity(registro);
            }
        });

    }





}