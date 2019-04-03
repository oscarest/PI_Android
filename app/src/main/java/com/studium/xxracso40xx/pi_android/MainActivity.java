package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    String URL= "http://8music.ddns.net/modelo/login.php";
    JSONParser jsonParser=new JSONParser();

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

                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(editTextMainUsuario.getText().toString(),editTextMainContraseña.getText().toString());
            }
        });


    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[1];
            String password = args[0];


            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
