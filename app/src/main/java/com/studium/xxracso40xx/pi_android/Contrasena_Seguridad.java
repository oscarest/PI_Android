package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class Contrasena_Seguridad extends AppCompatActivity {

    TextView editTextContrasenaSeguridad;
    Button buttonContrasenaSeguridadAceptar, buttonContrasenaSeguridadCancelar;
    Intent intent;
    private String APIserver = "http://8music.ddns.net/webserviceAndroid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena__seguridad);

        editTextContrasenaSeguridad = findViewById(R.id.editTextContrasenaSeguridad);
        buttonContrasenaSeguridadAceptar = findViewById(R.id.buttonContrasenaSeguridadAceptar);
        buttonContrasenaSeguridadCancelar = findViewById(R.id.buttonContrasenaSeguridadCancelar);
        intent = new Intent(this, Contrasena_Cambio.class);

        buttonContrasenaSeguridadAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.respuestaContrasena = editTextContrasenaSeguridad.getText().toString();

                new Contrasena_Seguridad.DB_Apache().execute("get-recuperarContrasena.php?correo=" + App.correoUsuario + "&clave=" + App.respuestaContrasena);


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

                    List<Usuarios> lst = new ArrayList<Usuarios>();
                    for (int i = 0; i < response.length(); i++) {
                        lst.add(new Usuarios(
                                response.getJSONObject(i).getLong("idUsuario")
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
                    if (!lst.isEmpty()) {
                        //FALTA ENVIAR EL ID A "APP" PARA PODER UTLIZAR ESE ID PARA RECOGER TODAS LAS CANCIONES QUE ESTE USUARIO TIENE GUARDADAS
                        //JUNTO CON OTROS DATOS QUE DESEEMOS DEL USUARIO.
                        int i = 0;

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
