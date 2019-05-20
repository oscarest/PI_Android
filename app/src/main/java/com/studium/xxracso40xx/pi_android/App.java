package com.studium.xxracso40xx.pi_android;

public class App
{
    public static String urlCancionActual=null;
    public static int tiempoActualCancionActual=0;
    public static Boolean REPETICION = true;
    public static int ID_USUARIO;
    public static int contadorReproductorMusica=2;

    //ATRIBUTOS PARA SELECCIONAR CANCION EN LISTA DE CANCIONES
    public static String nombreCancionSeleccionada=null;
    public static String urlCancionSeleccionada=null;
    public static String urlImagenCancionSeleccionada=null;
    public static String artistaCancionSeleccionada=null;

    //ATRIBUTOS PARA CAMBIOS DE CONTRASEÑA
    public static String correoUsuario="";
    public static String respuestaContrasena="";

    //ATRIBUTOS DEL USUARIO
    public static String nombreUsuario="";
    public static String apellidosUsuario="";
    public static String nickUsuario="";
    public static String emailUsuario="";
    public static String fechaNacimientoUsuario="";
    public static String direccionUsuario="";

    public static int pararCancion=0;
    public static Boolean reproductorPausa=false;

    //CREAR OBJETO DEBAJO PARA LAS CANCIONES DE LAS CUALES PODAMOS REVISIR TODOS LOS ELEMTOS TIPO DURACIÓN, NOMBRE, ETC.

}
