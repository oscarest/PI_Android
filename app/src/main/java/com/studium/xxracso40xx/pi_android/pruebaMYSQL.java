package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class pruebaMYSQL extends AppCompatActivity {

    //private static final String url = "jdbc:mysql://8music.ddns.net:3306/PI-8MUSIC";
    private static final String url = "jdbc:mysql://192.168.1.196:3306/tiendecita";
    private static final String user = "Oscar";
    private static final String pass = "8music123";
    EditText edit1, edit2;
    Button boton1;
    Intent intent;
    Boolean boo = false;
    int tipoUsuario;
    Button registro;
    Button contrasena;
    Intent intent1;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 = findViewById(R.id.editText1);
        edit2 = findViewById(R.id.editText2);
        boton1 = findViewById(R.id.button1);
        intent = new Intent(this, Principal.class);
        registro = findViewById(R.id.button6);
        contrasena = findViewById(R.id.button7);
        intent1 = new Intent(this, Registro.class);
        intent2 = new Intent(this,Contrasena_Seguridad.class);
       /* boton2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");

                String sentencia = "SELECT * FROM usuarios Where nombreUsuario='" + edit1.getText() +"' and claveUsuario='"+ edit2.getText() +"'";
                System.out.println(edit1.getText());
                //Cargar los controladores para el acceso a la BD
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    //Establecer la conexión con la BD Empresa
                    Connection con = DriverManager.getConnection(url, user, pass);
                    //Crear una sentencia
                    Statement statement = con.createStatement();
                    //Crear un objeto ResultSet para guardar lo obtenido
                    //y ejecutar la sentencia SQL
                    ResultSet rs = statement.executeQuery(sentencia);
                    rs.next();
                    if(rs.getRow()==0)
                    {
                        edit1.setText("HOOOLA");
                    //startActivity(intent);
                    }
                    else
                    {
                    edit1.setText("HEEEEEEEE");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
        */
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent1);
            }
        });
        contrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent2);
            }
        });
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
                if(boo==true)
                {
                    /*
                    if(tipoUsuario==1)
                    {

                    }
                    */
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Datos validados correctamente", Toast.LENGTH_SHORT);
                    toast1.show();
                    startActivity(intent);
                }
                else
                {

                } Toast toast2 = Toast.makeText(getApplicationContext(),"Datos introducidos erróneos", Toast.LENGTH_SHORT);
                toast2.show();
            }
        });
    }


    private class ConnectMySql extends AsyncTask<String, Void, String>
    {
        String res = "";

       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(pruebaMYSQL.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }
        */

        @Override
        protected String doInBackground(String... params) {
            try {
                edit1.getText();
                edit2.getText();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
               // String sentencia = "SELECT * FROM Usuarios Where nickUsuario='" + edit1.getText() +"' and claveUsuario='"+ edit2.getText() +"'";
               //Esta sirve para pruebas local
                String sentencia = "SELECT * FROM usuarios Where nombreUsuario='" + edit1.getText() +"' and claveUsuario='"+ edit2.getText() +"'";
                String sentencia1 = "SELECT * FROM Usuarios";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sentencia);
                //ResultSetMetaData rsmd = rs.getMetaData();
                rs.next();
                if(rs.getRow()==0)
                {
                    boo = true;
                }
                else
                {
                    boo = false;
                }
                /*rs = st.executeQuery(sentencia1);
                rs.next();
                tipoUsuario = rs.getInt("tipoUsuario");
                */
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result)
        {
        }
    }


}
