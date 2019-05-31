package com.studium.xxracso40xx.pi_android.model;

public class Usuarios {

        private Long idUsuario;
        private String nickUsuario;
        private String nombre;
        private Integer tipoUsuario;
        private Integer algunaSuscripcion;

        public Usuarios() {
            this.idUsuario = null;
            this.nickUsuario="";
            this.nombre = "";
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