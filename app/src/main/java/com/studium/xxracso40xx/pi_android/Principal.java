package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity
{
    Button boton1;
    Button boton2;
    Button boton3;
    Intent intent;
    Intent intent1;
    Intent intent2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        boton1 = findViewById(R.id.button3);
        boton2 = findViewById(R.id.button4);
        boton3 = findViewById(R.id.button5);
        intent = new Intent(this, Canciones.class);
        intent1 = new Intent(this, Nosotros.class);
        intent2 = new Intent(this, Perfil.class);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             startActivity(intent);
            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);

            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
    }
}
