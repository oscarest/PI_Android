package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Principal extends AppCompatActivity
{
    Button buttonPrincipalCanciones;
    Button buttonPrincipalPerfil;
    Intent intentCanciones;
    Intent intentPerfil;
    public ListView listviewPrincipal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonPrincipalCanciones = findViewById(R.id.buttonPrincipalCanciones);
        buttonPrincipalPerfil = findViewById(R.id.buttonPrincipalPerfil);
        intentCanciones = new Intent(this, Canciones.class);
        intentPerfil = new Intent(this, Perfil.class);
        listviewPrincipal = findViewById(R.id.listviewPrincipal);

        buttonPrincipalCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             startActivity(intentCanciones);
            }
        });


        buttonPrincipalPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentPerfil);
            }
        });
    }
    //CAMBIAR EL CÓDIGO DE ABAJO PARA QUE SE PUEDA HACER MEDIANTE LISTVIEW, HORIZONTAL SCROLL O HACERLO MEDIANTE DOS SCROLLS
    /*
    public void mostrarTodo()
    {
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.activity_list_card);
        List prod = new ArrayList<>();
        //prod.add("");
        if (prod == null) {
            listviewPrincipal.setAdapter(null);
        } else {
            for (Lugar p : lstProd) {
                listadoDeCards.add(p);
            }

        listviewPrincipal.setAdapter(listadoDeCards);
    }

    //}
    */
    }

