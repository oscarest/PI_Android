package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Perfil extends AppCompatActivity {
    Button boton1;
    Button boton3;
    Button boton4;
    Intent intent;
    Intent intent2;
    Intent intent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        //overridePendingTransition(android.R.anim.replace, android.R.anim.replaceto);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        boton1 = findViewById(R.id.buttonCancionesCanciones);
        boton3 = findViewById(R.id.buttonCancionesPerfil);
        boton4 = findViewById(R.id.buttonPrincipalInicio);
        intent = new Intent(this, Canciones.class);
        intent2 = new Intent(this, Perfil.class);
        intent3 = new Intent(this, Principal.class);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });
        boton3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        boton4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }
}