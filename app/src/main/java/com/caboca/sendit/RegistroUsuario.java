package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.caboca.sendit.databinding.ActivityLoginBinding;
import com.caboca.sendit.databinding.ActivityRegistroUsuarioBinding;

public class RegistroUsuario extends AppCompatActivity {

    ActivityRegistroUsuarioBinding binding;
    Controlador c = new Controlador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registro_usuario);

        binding = ActivityRegistroUsuarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextUsuario.getText().toString().trim().isEmpty()||binding.editTextConfirmPassword.getText().toString().trim().isEmpty()||binding.editTextPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistroUsuario.this, "Debe rellenar correctamente todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    String user = binding.editTextUsuario.getText().toString();
                    String pass = binding.editTextPassword.getText().toString();
                    String pass2 = binding.editTextConfirmPassword.getText().toString();
                    if(pass.equals(pass2)){
                        String passEncript = c.encriptarPassword(pass);
                        if (!c.crearCuenta(user, passEncript)) {
                            Toast.makeText(RegistroUsuario.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistroUsuario.this, "No se puede crear un usuario ya existente", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistroUsuario.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });
    }

}