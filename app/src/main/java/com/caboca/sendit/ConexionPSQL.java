package com.caboca.sendit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPSQL extends AppCompatActivity {

    public static Connection conexion = null;
    public static void conectar() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        //String ruta = "jdbc:postgresql://192.168.1.39:5432/mensajeria";
        String ruta = "jdbc:postgresql://database-sendit.c4yqhtoezdjp.eu-west-3.rds.amazonaws.com:5432/senditdb";
        String usuario = "usuario_final";
        String contrasenya = "postgres";
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(ruta,usuario,contrasenya);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            System.err.println(e.getMessage());
        }

    }

    public static void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


}