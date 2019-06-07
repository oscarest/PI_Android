package com.studium.xxracso40xx.pi_android.model;

public class Usuarios {

    public Long idUsuario;
    public String nickUsuario;
    public String nombre;
    public String ApellidoUsuario;
    public String emailUsuario;
    public String fechaNacimientoUsuario;
    public String direccionUsuario;
    public Integer tipoUsuario;
    public Integer algunaSuscripcion;




    public Usuarios() {
            this.idUsuario = null;
            this.nickUsuario="";
            this.nombre = "";
            this.ApellidoUsuario = "";
            this.emailUsuario = "";
            this.fechaNacimientoUsuario = "";
            this.direccionUsuario = "";
            this.tipoUsuario = 0;
            this.algunaSuscripcion = null;
        }

        public Usuarios(Long idUsuario, String nickUsuario, String nombre, Integer tipoUsuario, Integer algunaSuscripcion) {
            this.idUsuario = idUsuario;
            this.nickUsuario = nickUsuario;
            this.nombre = nombre;
            this.tipoUsuario = tipoUsuario;
            this.algunaSuscripcion = algunaSuscripcion;
        }
        public Usuarios(Long idUsuario, String nickUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nickUsuario = nickUsuario;
        this.nombre = nombre;
        }

        public Usuarios(Long idUsuario, String nickUsuario, String nombre, String apellidoUsuario, String emailUsuario, String fechaNacimientoUsuario, String direccionUsuario) {
            this.idUsuario = idUsuario;
            this.nickUsuario = nickUsuario;
            this.nombre = nombre;
            this.ApellidoUsuario = apellidoUsuario;
            this.emailUsuario = emailUsuario ;
            this.fechaNacimientoUsuario = fechaNacimientoUsuario;
            this.direccionUsuario = direccionUsuario;
        }

        public Usuarios(Long idUsuario)
        {
        this.idUsuario = idUsuario;
        }

        public Long getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
        }

        public String getNickUsuario() {
            return nickUsuario;
        }

        public void setNickUsuario(String nickUsuario) {
            this.nickUsuario = nickUsuario;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }


        public Integer getTipoUsuario() {
            return tipoUsuario;
        }

        public void setTipoUsuario(Integer tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
        }

        public Integer getAlgunaSuscripcion() {
            return algunaSuscripcion;
        }

        public void setAlgunaSuscripcion(Integer algunaSuscripcion) {
            this.algunaSuscripcion = algunaSuscripcion;
        }
    public String getApellidoUsuario() {
        return ApellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        ApellidoUsuario = apellidoUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(String fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

        @Override
        public String toString() {
            return "Usuarios {" +
                    "idUsuario=" + idUsuario +
                    ", nickUsuario='" + nickUsuario + '\''  +
                    ", nombre='" + nombre +
                    ", cantidad=" + tipoUsuario + '\'' +
                    ", idseccion='" + algunaSuscripcion +
                    '}';
        }
}