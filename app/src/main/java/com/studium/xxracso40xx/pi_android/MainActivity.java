package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.studium.xxracso40xx.pi_android.model.Usuarios;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String APIserver = "http://8music.ddns.net/webserviceAndroid/";
    //private static final String url = "jdbc:mysql://8music.ddns.net:3306/PI-8MUSIC";
    // private static final String user = "Oscar";
    //private static final String pass = "8music123";
    //private static final String url = "jdbc:mysql://8music.ddns.net:3306/PI-8MUSIC";
    //private static final String user = "Oscar";
    //private static final String pass = "8music123";
    EditText editTextMainUsuario, editTextMainContraseña;
    Button buttonMainIniciarSesion;
    Intent intent;
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
        intent2 = new Intent(this, Contrasena_info.class);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        contrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        buttonMainIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NECESITAMOS CREAR OTRO LUGAR EN EL QUE PODER ESCRIBIR LA CONTRASEÑA
                //NECESITAMOS MODIFICAR EL MODELO
                //Recordar pedir contraseña en el archivo php
                //EN EL CASO DE QUE NO HAYA NINGUNO SE CONTROLA CON Y SI ES 0 LO RECIBIDO O ALGO ASÍ
                //HACER QUE EN EL PHP SE DEVUELVA EL USUARIO WHERE USUARIO Y CONTRASEÑA SON LOS QUE HA INTRODUCIDO EL USUARIO
                new DB_Apache().execute("get-product.php?nick=" + editTextMainUsuario.getText().toString() + "&clave=" + editTextMainContraseña.getText().toString());


            }
        });
    }

    public class DB_Apache extends AsyncTask<String, Void, Boolean> {

        private String json;

        protected Boolean doInBackground(String... urls) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(APIserver + urls[0]));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
                return false;
            }
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(Boolean isOk) {
            try {
                if (isOk) {
                    JSONArray response = new JSONArray(json);

                    List<Usuarios> lst = new ArrayList<Usuarios>();
                    for (int i = 0; i < response.length(); i++) {
                        lst.add(new Usuarios(

                                response.getJSONObject(i).getLong("idUsuario")
                                , response.getJSONObject(i).getString("nickUsuario")
                                , response.getJSONObject(i).getString("nombreUsuario")
                                , response.getJSONObject(i).getString("apellidoUsuario")
                                , response.getJSONObject(i).getString("emailUsuario")
                                , response.getJSONObject(i).getString("direccionUsuario")
                                , response.getJSONObject(i).getString("fechaNacimientoUsuario")
                        ));
                    }


                    //SE PUEDE HACER CON EL OBJETO DEL MODELO COMO PODEMOS OBSERVAR PREVIAMENTE
                    //PERO TAMBIÉN TENEMOS QUE VER LA POSIBILIDAD DE GUARDAR DIRECTAMENTE LA STRING COMO MOSTRAREMOS
                    //EN EL CÓDIGO A CONTINUACIÓN
                        //String nameJson = response.getJSONObject(1).getString("nombreUsuario");
                        //String contrasenaJson = response.getJSONObject(2).getString("contrasena_usuario");
                    if(!lst.isEmpty())
                    {
                        //FALTA ENVIAR EL ID A "APP" PARA PODER UTLIZAR ESE ID PARA RECOGER TODAS LAS CANCIONES QUE ESTE USUARIO TIENE GUARDADAS
                        //JUNTO CON OTROS DATOS QUE DESEEMOS DEL USUARIO.
                        int i=0;
                        //long l = lst.get(i).getIdUsuario();
                        //App.ID_USUARIO= (int) l  ;

                        App.ID_USUARIO = response.getJSONObject(i).getInt("idUsuario");
                        App.nickUsuario = response.getJSONObject(i).getString("nickUsuario");
                        App.nombreUsuario = response.getJSONObject(i).getString("nombreUsuario");
                        App.apellidosUsuario = response.getJSONObject(i).getString("apellidoUsuario");
                        App.emailUsuario = response.getJSONObject(i).getString("emailUsuario");
                        App.direccionUsuario = response.getJSONObject(i).getString("direccionUsuario");
                        App.fechaNacimientoUsuario = response.getJSONObject(i).getString("fechaNacimientoUsuario");


                        startActivity(intent);
                        finish();
                    }
                    }

                } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
        }
    }

