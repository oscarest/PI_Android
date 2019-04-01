package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Canciones extends AppCompatActivity {
    Button buttonCancionesCanciones;
    Button buttonCancionesPerfil;
    Button buttonCancionesInicio;
    Intent intentPrincipal;
    Intent intentPerfil;
    Intent intentCanciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonCancionesCanciones = findViewById(R.id.buttonCancionesCanciones);
        buttonCancionesPerfil = findViewById(R.id.buttonCancionesPerfil);
        buttonCancionesInicio = findViewById(R.id.buttonCancionesInicio);
        intentPrincipal = new Intent(this, Canciones.class);
        intentPerfil = new Intent(this, Perfil.class);
        intentCanciones = new Intent(this, Principal.class);
        buttonCancionesCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intentCanciones);
            }
        });
        buttonCancionesPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentPerfil);
            }
        });
        buttonCancionesInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentPrincipal);
            }
        });
    }






    }
