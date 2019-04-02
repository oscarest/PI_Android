package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private static final String url = "jdbc:mysql://8music.ddns.net:3306/PI-8MUSIC";
    // private static final String user = "Oscar";
    //private static final String pass = "8music123";
    //private static final String url = "jdbc:mysql://8music.ddns.net:3306/PI-8MUSIC";
    //private static final String user = "Oscar";
    //private static final String pass = "8music123";
    EditText editTextMainUsuario, editTextMainContraseña;
    Button buttonMainIniciarSesion;
    Intent intent;
    Boolean boo = true;
    //int tipoUsuario;
    Button registro;
    Button contrasena;
    Intent intent1;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMainUsuario = findViewById(R.id.editTextMainUsuario);
        editTextMainContraseña = findViewById(R.id.editTextMainContraseña);
        buttonMainIniciarSesion = findViewById(R.id.buttonMainIniciarSesion);
        intent = new Intent(this, Principal.class);
        registro = findViewById(R.id.buttonMainRegistro);
        contrasena = findViewById(R.id.buttonMainContraseñaOlvidada);
        intent1 = new Intent(this, Registro.class);
        intent2 = new Intent(this,Contrasena_Seguridad.class);

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
        buttonMainIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(boo==true)
                {


                    startActivity(intent);
                }
                else
                {

                    editTextMainContraseña.setText("");
                }
            }

        });
    }


   /* private class ConnectMySql extends AsyncTask<String, Void, String>
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
                editTextMainUsuario.getText();
                editTextMainContraseña.getText();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
               String sentencia = "SELECT * FROM Usuarios Where nickUsuario='" + editTextMainUsuario.getText() +"' and claveUsuario='"+ editTextMainContraseña.getText() +"'";
               //Esta sirve para pruebas local
                //String sentencia = "SELECT * FROM usuarios Where nombreUsuario='" + editTextMainUsuario.getText() +"' and claveUsuario='"+ editTextMainContraseña.getText() +"'";
               //String sentencia1 = "SELECT * FROM Usuarios";
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
               // rs = st.executeQuery(sentencia1);
                //rs.next();
                //tipoUsuario = rs.getInt("tipoUsuario");
                //editTextMainUsuario.setText(tipoUsuario);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result)
        {
        }
        */
}
