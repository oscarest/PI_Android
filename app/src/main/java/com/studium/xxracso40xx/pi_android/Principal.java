package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Principal extends AppCompatActivity
{
    Button boton1;
    Button boton2;
    Button boton3;
    Intent intent;
    Intent intent1;
    Intent intent2;
    public ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        overridePendingTransition(R.anim.replace, R.anim.replaceto);
        boton1 = findViewById(R.id.buttonPerfilCanciones);
        boton3 = findViewById(R.id.buttonPerfilPerfil);
        intent = new Intent(this, Canciones.class);
        intent2 = new Intent(this, Perfil.class);
        listView = findViewById(R.id.card_listView);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             startActivity(intent);
            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
    }
    public void mostrarTodo()
    {
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.activity_list_card);
        /*List prod = new ArrayList<>();
        //prod.add("");
        if (prod == null) {
            listView.setAdapter(null);
        } else {
            for (Lugar p : lstProd) {
                listadoDeCards.add(p);
            }
            */
        listView.setAdapter(listadoDeCards);
    }

    //}
}
