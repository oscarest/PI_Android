package com.studium.xxracso40xx.pi_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

public class Registro extends AppCompatActivity {
EditText editTextRegistroFechaDeNacimiento, editTextRegistroContrasena, editTextRegistroNombre, editTextRegistroApellidos, editTextRegistroNombreUsuario, editTextRegistroEmail, editTextRegistroDireccion;
Button buttonCancelar, buttonConfirmar;
Intent intent1;
    private String APIserver = "http://8music.ddns.net/webserviceAndroid/";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //editTextMainUsuario=fecha
        editTextRegistroFechaDeNacimiento = findViewById(R.id.editTextRegistroFechaDeNacimiento);
        editTextRegistroNombre = findViewById(R.id.editTextRegistroNombre);
        editTextRegistroApellidos = findViewById(R.id.editTextRegistroApellidos);
        editTextRegistroNombreUsuario = findViewById(R.id.editTextRegistroNombreUsuario);
        editTextRegistroEmail = findViewById(R.id.editTextRegistroEmail);
        editTextRegistroDireccion = findViewById(R.id.editTextRegistroDireccion);
        editTextRegistroContrasena = findViewById(R.id.editTextRegistroContrasena);
        buttonCancelar = findViewById(R.id.buttonCancelar);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);
        intent1 = new Intent(this, MainActivity.class );
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Poner aquí toda la funcionalidad
                Toast toast = Toast.makeText(getApplicationContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT);
                toast.show();
               //Borrar los datos solo si ha funcionado correctamente el registro
                new Registro.DB_Apache().execute("set-user.php?nick=" + editTextRegistroNombreUsuario.getText().toString() + "&clave=" + editTextRegistroContrasena.getText().toString()
                                                + "&nombreUsuario=" + editTextRegistroNombre.getText().toString() + "&apellidoUsuario=" + editTextRegistroApellidos.getText().toString()
                                                + "&emailUsuario=" + editTextRegistroEmail.getText().toString() + "&direccionUsuario=" + editTextRegistroDireccion.getText().toString()
                                                + "&fechaNacimientoUsuario=" + editTextRegistroFechaDeNacimiento.getText().toString());
                editTextRegistroFechaDeNacimiento.setText("");
                editTextRegistroNombre.setText("");
                editTextRegistroApellidos.setText("");
                editTextRegistroNombreUsuario.setText("");
                editTextRegistroEmail.setText("");
                editTextRegistroDireccion.setText("");
                startActivity(intent1);
                /*
                Toast toast1 = Toast.makeText(getApplicationContext(),"Ha ocurrido un error en el registro", Toast.LENGTH_SHORT);
                toast1.show();
                 */
                //FALTA POR AÑADIR TAMBIÉN EL CASO EN EL QUE ALGÚN ELEMENTO SE QUEDE VACÍO
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editTextRegistroFechaDeNacimiento.setText("");
                editTextRegistroNombre.setText("");
                editTextRegistroApellidos.setText("");
                editTextRegistroNombreUsuario.setText("");
                editTextRegistroEmail.setText("");
                editTextRegistroDireccion.setText("");
                startActivity(intent1);
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
                client.execute(request);
            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean isOk) {


        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTextRegistroFechaDeNacimiento:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                editTextRegistroFechaDeNacimiento.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }



}
