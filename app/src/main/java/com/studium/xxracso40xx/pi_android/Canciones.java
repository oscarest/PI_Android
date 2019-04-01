package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Canciones extends AppCompatActivity {
    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    Intent intent;
    Intent intent1;
    Intent intent2;
    Intent intent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        boton1 = findViewById(R.id.buttonPerfilCanciones);
        boton3 = findViewById(R.id.buttonPerfilPerfil);
        boton4 = findViewById(R.id.buttonPerfilInicio);
        intent = new Intent(this, Canciones.class);
        intent2 = new Intent(this, Perfil.class);
        intent3 = new Intent(this, Principal.class);
        /*List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);
        listadoDeCards.add("hola");
        listView.setAdapter(listadoDeCards);
        */
        // ViewPager viewPager = findViewById(R.id.view_pager);
        //viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent2);
            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }






    }
