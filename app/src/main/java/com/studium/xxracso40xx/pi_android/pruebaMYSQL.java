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

    private static final String url = "jdbc:mysql://192.168.1.196:3306/tiendecita";
    private static final String user = "oscar";
    private static final String pass = "Studium";
    EditText edit1, edit2;
    Button boton1, boton2;
    Intent intent;
    Boolean boo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 = findViewById(R.id.editText1);
        edit2 = findViewById(R.id.editText2);
        boton1 = findViewById(R.id.button1);
        boton2 = findViewById(R.id.button2);
        intent = new Intent(this, Principal.class);
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

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
                if(boo=true)
                {
                    startActivity(intent);
                }
                else
                {

                }
            }
        });
    }


    private class ConnectMySql extends AsyncTask<String, Void, String>
    {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(pruebaMYSQL.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                edit1.getText();
                edit2.getText();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");
                String sentencia = "SELECT * FROM usuarios Where nombreUsuario='" + edit1.getText() +"' and claveUsuario='"+ edit2.getText() +"'";
                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sentencia);
                //ResultSetMetaData rsmd = rs.getMetaData();
                rs.next();
                if(rs.getRow()==0)
                {
                    boo = true;
                    //startActivity(intent);

                }
                else
                {
                    boo = false;
                    //edit1.setText("HEEEEEEEE");
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result)
        {
            edit1.setText(result);
        }
    }


}
