package com.studium.xxracso40xx.pi_android;

import com.studium.xxracso40xx.pi_android.model.CancionObject;

import java.util.ArrayList;

public class App
{
    public static String urlCancionActual=null;
    public static int tiempoActualCancionActual=0;
    public static Boolean REPETICION = true;
    public static int ID_USUARIO = 1;
    public static int contadorReproductorMusica=2;
    public static boolean imageRap = false;

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
    public static Boolean resetearCancion=false;

    public static Boolean pulsarBotonRepetir=false;

    public static Boolean saltarBotonCancion = false;
    public static ArrayList<CancionObject> listaCanciones = new ArrayList<>();
    public static int posicionListaCanciones;
    public static Boolean contadorcontador1=true;
    public static Boolean cancionTerminada=false;

    public static int contadorRepetir=0;
    public static Boolean cambiarInterfaz=false;
    public static Boolean cambiarBotonInterfaz=false;
    public static Boolean cambiarReproductorMini=false;
    public static ArrayList<CancionObject> listaCancionesPrincipal= new ArrayList<>();;

    public static String urlCancionActualMini =null;
    //CREAR OBJETO DEBAJO PARA LAS CANCIONES DE LAS CUALES PODAMOS REVISIR TODOS LOS ELEMTOS TIPO DURACIÓN, NOMBRE, ETC.

}
