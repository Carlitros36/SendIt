package com.caboca.sendit;

import static com.caboca.sendit.ConexionPSQL.conexion;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controlador {

    public static String usuarioLogeado;

    public boolean crearCuenta(String usuario, String contrasenya) {

        ConexionPSQL.conectar();
        ResultSet rs = null;
        boolean existente = false;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.crear_usuario(?,?)");
            pst.setString(1, contrasenya);
            pst.setString(2, usuario);
            pst.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            existente = true;
        }

        ConexionPSQL.desconectar();
        return existente;
    }

    public String encriptarPassword(String password) {
        String passwordEncriptada = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            passwordEncriptada = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordEncriptada;
    }


    public boolean comprobarLogin(String usuario, String password) {

        ConexionPSQL.conectar();
        ResultSet rs = null;
        boolean login = false;
        try {

            PreparedStatement pst = conexion.prepareStatement("SELECT alias,contrasenya FROM usuario WHERE alias=?");

            pst.setString(1, usuario);
            rs = pst.executeQuery();

            if (rs.next()) {
                String passwd = rs.getString("contrasenya");
                if (password.equals(passwd)) {
                    InterfazPrincipal.usuario = usuario;
                    login = true;

                }
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return login;
    }

    /**
     * Método para eliminar el usuario
     */
    public void eliminarCuenta() {

        ResultSet rs = null;

        //Busco el id del usuario conectado
        int id_usuario = buscarIDPorNombre(usuarioLogeado);
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.eliminar_usuario(?)");
            pst.setInt(1, id_usuario);
            pst.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ConexionPSQL.desconectar();
    }

    public int buscarIDPorNombre(String usuario) {

        int id = 0;
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT id_usuario FROM usuario WHERE alias=?");
            pst.setString(1, usuario);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_usuario");
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return id;
    }


    public String buscarNombrePorID(int id) {

        String usuario = "";
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                usuario = rs.getString("alias");
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return usuario;
    }


    public void mostrarAmigos() {

        RecyclerAdapterAmigos.amigos = new ArrayList<>();
        int id = buscarIDPorNombre(usuarioLogeado);
        ResultSet rs = null;
        try {

            PreparedStatement pst = conexion.prepareStatement("SELECT id_usuario2 FROM usuario_amigo WHERE id_usuario1=?");
            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {

                int idusuario = rs.getInt("id_usuario2");
                String alias = buscarNombrePorID(idusuario);
                System.out.println(alias);
                RecyclerAdapterAmigos.amigos.add(alias);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void elegirNuevoAmigo() {

        //RecyclerAdapterAgregarAmigos.noAmigos = new ArrayList<>();
        Amigos.elegirAmigoAgregar = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario NOT IN(SELECT id_usuario2 FROM usuario_amigo, usuario WHERE id_usuario1=id_usuario and usuario.alias=?) and alias<>?");
            pst.setString(1, usuarioLogeado);
            pst.setString(2, usuarioLogeado);
            rs = pst.executeQuery();

            while (rs.next()) {
                String noAmigo = rs.getString("alias");
                Amigos.elegirAmigoAgregar.add(noAmigo);
                //RecyclerAdapterAgregarAmigos.noAmigos.add(noAmigo);
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void agregarAmigo(String usu2) {

        int id1 = buscarIDPorNombre(usuarioLogeado);
        int id2 = buscarIDPorNombre(usu2);


        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO public.usuario_amigo(id_usuario1, id_usuario2)VALUES (?, ?);");
            pst.setInt(1, id1);
            pst.setInt(2, id2);
            pst.executeUpdate();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /*public void elegirAmigoEliminar() {

        //RecyclerAdapterEliminarAmigos.amigos = new ArrayList<>();
        Amigos.elegirAmigoBorrar = new ArrayList<>();
        ResultSet rs = null;
        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario IN(SELECT id_usuario2 FROM usuario_amigo, usuario WHERE id_usuario1=id_usuario and usuario.alias=?) and alias<>?");
            pst.setString(1, usuarioLogeado);
            pst.setString(2, usuarioLogeado);
            rs=pst.executeQuery();


            while(rs.next()) {

                String amigo = rs.getString("alias");
                //RecyclerAdapterEliminarAmigos.amigos.add(amigo);
                Amigos.elegirAmigoBorrar.add(amigo);

            }


        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }*/

    public void eliminarAmigo(String usu2) {

        int id1 = buscarIDPorNombre(usuarioLogeado);
        int id2 = buscarIDPorNombre(usu2);


        try {
            PreparedStatement pst = conexion.prepareStatement("DELETE FROM usuario_amigo WHERE id_usuario1=? and id_usuario2=?;");
            pst.setInt(1, id1);
            pst.setInt(2, id2);
            pst.executeUpdate();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void mostrarGrupos() {

        RecyclerAdapterGrupos.grupos = new ArrayList<>();
        int id = buscarIDPorNombre(usuarioLogeado);
        ResultSet rs = null;

        try {

            PreparedStatement pst = conexion.prepareStatement("SELECT nombre FROM grupo where id_chat IN (SELECT id_chat FROM usuario_grupo WHERE id_usuario=?)");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                RecyclerAdapterGrupos.grupos.add(nombre);

            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void crearGrupo(String nombre, Context contexto) {

        ResultSet rs = null;
        int usuario = buscarIDPorNombre(usuarioLogeado);
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.crear_grupo(?,?)");
            pst.setInt(1, usuario);
            pst.setString(2, nombre);
            pst.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Toast.makeText(contexto, "No se puede crear un grupo ya existente", Toast.LENGTH_SHORT).show();
        }
    }

    public int buscarIDGrupoPorNombre(String nombre) {

        int id = 0;
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT id_chat FROM grupo WHERE nombre=?");
            pst.setString(1, nombre);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id_chat");
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return id;
    }

    public String buscarNombreAdmin(String nombreGrupo) {

        String admin = "";
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT u.alias FROM usuario u, grupo g WHERE u.id_usuario=g.id_usuario_creador and g.nombre=?");
            pst.setString(1, nombreGrupo);
            rs = pst.executeQuery();

            if (rs.next()) {
                admin = rs.getString("alias");
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return admin;
    }

    public void mostrarParticipantesGrupo(String nombre_grupo) {

        int grupo = buscarIDGrupoPorNombre(nombre_grupo);
        RecyclerAdapterParticipantesGrupo.participantes = new ArrayList<>();
        String admin = buscarNombreAdmin(nombre_grupo);
        ResultSet rs = null;
        try {

            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario IN(SELECT id_usuario FROM usuario_grupo WHERE id_chat=?) ORDER BY alias");
            pst.setInt(1, grupo);

            rs = pst.executeQuery();
            while (rs.next()) {
                String alias = rs.getString("alias");
                RecyclerAdapterParticipantesGrupo.participantes.add(alias);
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void mostrarMensajesGrupo(String grupo) {

        RecyclerAdapterMensajesGrupo.usuarios = new ArrayList<>();
        RecyclerAdapterMensajesGrupo.mensajes = new ArrayList<>();

        int id = buscarIDGrupoPorNombre(grupo);
        ResultSet rs = null;
        try {

            PreparedStatement pst = conexion.prepareStatement("SELECT m.contenido, u.alias FROM mensaje m, usuario u WHERE id_chat=? and m.id_usuario=u.id_usuario order by id_mensaje");
            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {

                String mensaje = rs.getString("contenido");
                String usuario = rs.getString("alias");
                RecyclerAdapterMensajesGrupo.usuarios.add(usuario);
                RecyclerAdapterMensajesGrupo.mensajes.add(mensaje);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void enviarMensajeGrupo(String nombreGrupo, String mensaje) {

        int id1 = buscarIDGrupoPorNombre(nombreGrupo);
        int id2 = buscarIDPorNombre(usuarioLogeado);


        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.enviar_mensaje(?, ?, ?)");
            pst.setString(1, mensaje);
            pst.setInt(2, id1);
            pst.setInt(3, id2);
            pst.executeQuery();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void elegirNuevoParticipante(String nombre_grupo) {

        /*RecyclerAdapterAgregarParticipantesGrupo.noParticipantes = new ArrayList<>();
        RecyclerAdapterAgregarParticipantesGrupo.nomGrupo = nombre_grupo;*/
        OpcionesGrupo.elegirNuevoParticiante = new ArrayList<>();


        int id = buscarIDGrupoPorNombre(nombre_grupo);
        ResultSet rs = null;
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario NOT IN(SELECT id_usuario FROM usuario_grupo WHERE id_chat=?)");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                String noParticipante = rs.getString("alias");
                OpcionesGrupo.elegirNuevoParticiante.add(noParticipante);
                //RecyclerAdapterAgregarParticipantesGrupo.noParticipantes.add(noParticipante);
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void agregarParticipanteGrupo(String grupo, String participante) {

        int idchat = buscarIDGrupoPorNombre(grupo);
        int idparticipante = buscarIDPorNombre(participante);


        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.agregar_miembros_grupo(?,?)");
            pst.setInt(1, idchat);
            pst.setInt(2, idparticipante);
            pst.executeQuery();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /*public void elegirParticipanteEliminar(String nombre_grupo) {

        RecyclerAdapterEliminarParticipantesGrupo.participantes = new ArrayList<>();
        RecyclerAdapterEliminarParticipantesGrupo.nomGrupo = nombre_grupo;
        int id = buscarIDGrupoPorNombre(nombre_grupo);
        ResultSet rs = null;
        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT alias FROM usuario WHERE id_usuario IN(SELECT id_usuario FROM usuario_grupo WHERE id_chat=?) and alias<>?");
            pst.setInt(1, id);
            pst.setString(2, usuarioLogeado);
            rs=pst.executeQuery();

            while(rs.next()) {
                String participante = rs.getString("alias");
                RecyclerAdapterEliminarParticipantesGrupo.participantes.add(participante);
            }

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }*/

    public void eliminarParticipanteGrupo(String grupo, String participante) {

        int idchat = buscarIDGrupoPorNombre(grupo);
        int idparticipante = buscarIDPorNombre(participante);


        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.sacar_miembros_grupo(?,?)");
            pst.setInt(1, idchat);
            pst.setInt(2, idparticipante);
            pst.executeQuery();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void eliminarGrupo(String chat) {

        int idchat = buscarIDGrupoPorNombre(chat);


        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.borrar_chat(?)");
            pst.setInt(1, idchat);
            pst.executeQuery();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void mostrarPrivados() {

        RecyclerAdapterPrivados.privados = new ArrayList<>();
        RecyclerAdapterPrivados.id_chats = new ArrayList<>();
        int id = buscarIDPorNombre(usuarioLogeado);
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {


            PreparedStatement pst = conexion.prepareStatement("SELECT id_chat, id_usuario2 FROM privado where id_usuario1=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario2");
                String usuario = buscarNombrePorID(id_usuario);
                RecyclerAdapterPrivados.privados.add(usuario);
                int id_chat = rs.getInt("id_chat");
                RecyclerAdapterPrivados.id_chats.add(id_chat);
            }

            PreparedStatement pst2 = conexion.prepareStatement("SELECT id_chat, id_usuario1 FROM privado where id_usuario2=?");
            pst2.setInt(1, id);
            rs2 = pst2.executeQuery();

            while (rs2.next()) {
                int id_usuario = rs2.getInt("id_usuario1");
                String usuario = buscarNombrePorID(id_usuario);
                RecyclerAdapterPrivados.privados.add(usuario);
                int chat = rs2.getInt("id_chat");
                RecyclerAdapterPrivados.id_chats.add(chat);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void elegirAmigoCrearPrivado() {
        ResultSet rs = null;
        int idusu = buscarIDPorNombre(usuarioLogeado);
        Privados.elegirAmigoNuevoPrivado = new ArrayList<>();
        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT id_usuario, alias	FROM usuario WHERE id_usuario IN(SELECT id_usuario2 FROM usuario_amigo WHERE id_usuario1=?) and id_usuario NOT IN(SELECT id_usuario2 FROM privado WHERE id_usuario1=?) and id_usuario NOT IN(SELECT id_usuario1 FROM privado WHERE id_usuario2=?)");
            pst.setInt(1, idusu);
            pst.setInt(2, idusu);
            pst.setInt(3, idusu);
            rs=pst.executeQuery();

            while(rs.next()) {
                String nuevoPrivado = rs.getString("alias");
                Privados.elegirAmigoNuevoPrivado.add(nuevoPrivado);
            }


        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * Método para crear una conversación privada
     * @param usu2 usuario con el que crear la conversación privada
     */
    public void crearPrivado(String usu2) {

        ResultSet rs = null;
        int usuario1 = buscarIDPorNombre(usuarioLogeado);
        int usuario2 = buscarIDPorNombre(usu2);
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT public.crear_privado(?,?)");
            pst.setInt(1, usuario1);
            pst.setInt(2, usuario2);
            pst.execute();

        } catch (SQLException e) {
        }

        mostrarPrivados();
    }

    public boolean comprobarAmistad(String usu2) {

        ResultSet rs = null;
        boolean amigo = false;
        int idusu = buscarIDPorNombre(usuarioLogeado);
        int idusu2 = buscarIDPorNombre(usu2);
        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT id_usuario, u.alias FROM usuario u, usuario_amigo a WHERE a.id_usuario1=? and u.id_usuario=a.id_usuario2 and a.id_usuario2=?");
            pst.setInt(1, idusu);
            pst.setInt(2, idusu2);

            rs=pst.executeQuery();

            if(rs.next()) {
                amigo = true;
            }

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return amigo;
    }

    /**
     * Método para mostrar los mensajes de una conversación privada
     * @param chat id de la conversación
     */
    public void mostrarMensajesPrivado(int chat) {

        RecyclerAdapterMensajesPrivado.usuarios = new ArrayList<>();
        RecyclerAdapterMensajesPrivado.mensajes = new ArrayList<>();

        ResultSet rs = null;
        try{

            PreparedStatement pst = conexion.prepareStatement("SELECT m.contenido, u.alias FROM mensaje m, usuario u WHERE id_chat=? and m.id_usuario=u.id_usuario order by id_mensaje");
            pst.setInt(1, chat);

            rs=pst.executeQuery();

            while(rs.next()) {

                String alias = rs.getString("alias");
                RecyclerAdapterMensajesPrivado.usuarios.add(alias);
                String mensaje = rs.getString("contenido");
                RecyclerAdapterMensajesPrivado.mensajes.add(mensaje);
            }

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * Método para enviar un mensaje en una conversación privada
     * @param chat id de la conversación
     * @param mensaje contenido del mensaje
     */
    public void enviarMensajePrivado(int chat, String mensaje) {

        int id2=buscarIDPorNombre(usuarioLogeado);


        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT public.enviar_mensaje(?, ?, ?)");
            pst.setString(1, mensaje);
            pst.setInt(2, chat);
            pst.setInt(3, id2);
            pst.executeQuery();

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * Método para eliminar una conversación privada
     * @param chat id de la conversación a eliminar
     */
    public void eliminarChatPrivado(int chat) {

        try{
            PreparedStatement pst = conexion.prepareStatement("SELECT public.borrar_chat(?)");
            pst.setInt(1, chat);
            pst.executeQuery();

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    /*public int buscarIdMensajePorContenido(String contenido){
        int id_mensaje;
        ResultSet rs = null;
        try{

            PreparedStatement pst = conexion.prepareStatement("SELECT u.alias FROM usuario u, mensaje m WHERE u.id_usuario=m.id_usuario and m.id_mensaje=?");
            pst.setString(1, contenido);

            rs=pst.executeQuery();

            if(rs.next()) {
                id_mensaje = rs.getInt("id_mensaje");
            }

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }*/


    /*public String buscarUsuarioPorIdMensaje(int id_mensaje){
        String usuario = "";

        ResultSet rs = null;
        try{

            PreparedStatement pst = conexion.prepareStatement("SELECT u.alias FROM usuario u, mensaje m WHERE u.id_usuario=m.id_usuario and m.id_mensaje=?");
            pst.setInt(1, id_mensaje);

            rs=pst.executeQuery();

            if(rs.next()) {
                usuario = rs.getString("alias");
            }

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return usuario;
    }*/
}
