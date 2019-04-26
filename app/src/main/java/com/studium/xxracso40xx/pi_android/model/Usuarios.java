package com.studium.xxracso40xx.pi_android.model;

public class Usuarios {

        private Long idUsuario;
        private String nickUsuario;
        private String nombre;
        private Integer cantidad;
        private Long idSeccion;

        public Usuarios() {
            this.idUsuario = null;
            this.nickUsuario="";
            this.nombre = "";
            this.cantidad = 0;
            this.idSeccion = null;
        }

        public Usuarios(Long idUsuario, String nickUsuario, String nombre, Integer cantidad, Long idSeccion) {
            this.idUsuario = idUsuario;
            this.nickUsuario = nickUsuario;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.idSeccion = idSeccion;
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


        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Long getIdSeccion() {
            return idSeccion;
        }

        public void setIdSeccion(Long idSeccion) {
            this.idSeccion = idSeccion;
        }

        @Override
        public String toString() {
            return "Usuarios {" +
                    "idUsuario=" + idUsuario +
                    ", nickUsuario='" + nickUsuario + '\''  +
                    ", nombre='" + nombre +
                    ", cantidad=" + cantidad + '\'' +
                    ", idseccion='" + idSeccion +
                    '}';
        }
}