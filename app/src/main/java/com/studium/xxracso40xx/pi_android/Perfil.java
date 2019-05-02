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
    Intent intent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //overridePendingTransition(android.R.anim.replace, android.R.anim.replaceto);
        boton1 = findViewById(R.id.buttonPerfilCanciones);
        boton4 = findViewById(R.id.buttonPrincipalInicio);
        intent = new Intent(this, Canciones.class);
        intent3 = new Intent(this, Principal.class);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
        boton4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            }
        });
    }
}