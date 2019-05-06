package com.studium.xxracso40xx.pi_android.model;

public class Canciones {
    private Long idCancion;
    private String nombreCancion;
    private String urlCancion;
    private String urlImagenCancion;

    public Canciones() {
        this.idCancion = null;
        this.nombreCancion ="";
        this.urlCancion = "";
        this.urlImagenCancion="";
    }

    public Canciones(Long idUsuario, String nombreCancion, String urlCancion, String urlImagenCancion) {
        this.idCancion = idUsuario;
        this.nombreCancion = nombreCancion;
        this.urlCancion = urlCancion;
        this.urlImagenCancion = urlImagenCancion;

    }
    public Canciones(Long idCancion)
    {
        this.idCancion = idCancion;
    }

    public Long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Long idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getUrlCancion() {
        return urlCancion;
    }

    public void setUrlCancion(String urlCancion) {
        this.urlCancion = urlCancion;
    }

    public String getUrlImagenCancion() {
        return urlImagenCancion;
    }

    public void setUrlImagenCancion(String urlImagenCancion) {
        this.urlImagenCancion = urlImagenCancion;
    }

}
