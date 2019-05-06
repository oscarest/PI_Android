package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Contrasena_info extends AppCompatActivity {

    TextView editTextContrasenaInfo;
    Button buttonContrasenaInfoAceptar, buttonContrasenaInfoCancelar;
    Intent intentContrasenaSeguridad, intentMain;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_info);


        editTextContrasenaInfo = findViewById(R.id.editTextContrasenaInfo);
        buttonContrasenaInfoAceptar = findViewById(R.id.buttonContrasenaInfoAceptar);
        buttonContrasenaInfoCancelar = findViewById(R.id.buttonContrasenaInfoCancelar);
        intentContrasenaSeguridad = new Intent(this, Contrasena_Seguridad.class );
        intentMain = new Intent(this, MainActivity.class);

        buttonContrasenaInfoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.correoUsuario = editTextContrasenaInfo.getText().toString();
                startActivity(intentContrasenaSeguridad);

            }
        });

        buttonContrasenaInfoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentMain);
            }
        });

    }
}
