package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
//HttpClientBuilder

import com.studium.xxracso40xx.pi_android.model.CancionObject;
import com.studium.xxracso40xx.pi_android.model.Usuarios;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Canciones extends AppCompatActivity {
    Button buttonCancionesCanciones;
    Button buttonCancionesPerfil;
    Button buttonCancionesInicio;
    Intent intentPrincipal;
    Intent intentPerfil;
    ListView list;
    private String APIserver = "http://8music.ddns.net/webserviceAndroid/";
    private ListAdapter adapter;
    ArrayList<CancionObject> canciones = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        //overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonCancionesPerfil = findViewById(R.id.buttonCancionesPerfil);
        buttonCancionesInicio = findViewById(R.id.buttonCancionesInicio);
        intentPrincipal = new Intent(this, Principal.class);
        intentPerfil = new Intent(this, Perfil.class);
        list = findViewById(R.id.list_view);
        //mostrarList();
        //DE AQUÍ LEEREMOS LOS DATOS PERSONALES DEL USUARIO QUE ESTÁ LOGUEADO PARA PODER SACAR LAS CANCIONES QUE ESTA PERSONA TIENE EN SU BIBLIOTECA.
        //LO MEJOR SERÍA INTRODUCIR EL ID DEL USUARIO QUE ESTA LOGUEADO PARA PODER SACAR ESTAS CANCIONES.
        //COMENTADO HASTA QUE SE HAGA EL ARCHIVO .PHP
        //new Canciones.DB_Apache().execute("get-product.php?idUsuario=" + App.ID_USUARIO);

        buttonCancionesPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentPerfil);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
        buttonCancionesInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentPrincipal);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            }
        });

    }
    /*public void mostrarList()
    {
        //Hacer un for que recibe vaya añadiendo uno a uno todos los elementos que se reciban de la base de datos.
        canciones.add(new CancionObject("Buenas", "url11", "https://www.absaonline.mx/pub/media/catalog/product/cache/1/image/500x608/e9c3970ab036de70892d86c6d221abfe/2/0/20812_TL222.png"));
        canciones.add(new CancionObject("Buenasaasdas", "ur1asdl11", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buer234asnas", "urgergewrgl11", "https://www.dhresource.com/100x100s/f2-albu-g5-M01-54-A1-rBVaJFngIveAC1bUAAI1p63w2TE474.jpg/puntas-de-prueba-de-prueba-universales-de.jpg"));
        adapter = new ListAdapter(this, canciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Canciones.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }
    */
    public void mostrarList()
    {
        //Hacer un for que recibe vaya añadiendo uno a uno todos los elementos que se reciban de la base de datos.
        adapter = new ListAdapter(this, canciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Canciones.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }
    private class DB_Apache extends AsyncTask<String, Void, Boolean> {

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

        protected void onPostExecute(Boolean isOk) {
            try {
                if (isOk) {
                    JSONArray response = new JSONArray(json);

                    canciones = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        canciones.add(new CancionObject(
                                response.getJSONObject(i).getString("nombreCancion"),
                                response.getJSONObject(i).getString("urlCancion"),
                                response.getJSONObject(i).getString("urlImagenCancion")
                               /* , response.getJSONObject(i).getString("nickUsuario")
                                , response.getJSONObject(i).getString("claveUsuario")
                                , response.getJSONObject(i).getInt("tipoUsuario")
                                , response.getJSONObject(i).getInt("algunaSuscripcionUsuario")*/
                        ));
                    }

                    //SE PUEDE HACER CON EL OBJETO DEL MODELO COMO PODEMOS OBSERVAR PREVIAMENTE
                    //PERO TAMBIÉN TENEMOS QUE VER LA POSIBILIDAD DE GUARDAR DIRECTAMENTE LA STRING COMO MOSTRAREMOS
                    //EN EL CÓDIGO A CONTINUACIÓN
                    //String nameJson = response.getJSONObject(1).getString("nombreUsuario");
                    //String contrasenaJson = response.getJSONObject(2).getString("contrasena_usuario");
                    if(!canciones.isEmpty())
                    {
                        mostrarList();
                        //startActivity(intentContrasenaSeguridad);
                        //finish();
                    }
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
    }
}
