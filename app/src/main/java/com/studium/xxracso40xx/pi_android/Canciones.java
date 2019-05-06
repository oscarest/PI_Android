package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.studium.xxracso40xx.pi_android.model.CancionObject;

import java.util.ArrayList;

public class Canciones extends AppCompatActivity {
    Button buttonCancionesCanciones;
    Button buttonCancionesPerfil;
    Button buttonCancionesInicio;
    Intent intentPrincipal;
    Intent intentPerfil;
    ListView list;
    private ListAdapter adapter;
    ArrayList<CancionObject> canciones = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        //overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonCancionesPerfil = findViewById(R.id.buttonCancionesPerfil);
        buttonCancionesInicio = findViewById(R.id.buttonCancionesInicio);
        intentPrincipal = new Intent(this, Principal.class);
        intentPerfil = new Intent(this, Perfil.class);
        list = findViewById(R.id.list_view);
        mostrarList();
        buttonCancionesPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentPerfil);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
        buttonCancionesInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentPrincipal);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            }
        });

    }
    public void mostrarList()
    {
        //Hacer un for que recibe vaya añadiendo uno a uno todos los elementos que se reciban de la base de datos.
        canciones.add(new CancionObject("Buenas", "url11", "https://www.absaonline.mx/pub/media/catalog/product/cache/1/image/500x608/e9c3970ab036de70892d86c6d221abfe/2/0/20812_TL222.png"));
        canciones.add(new CancionObject("Buenasaasdas", "ur1asdl11", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buer234asnas", "urgergewrgl11", "https://www.dhresource.com/100x100s/f2-albu-g5-M01-54-A1-rBVaJFngIveAC1bUAAI1p63w2TE474.jpg/puntas-de-prueba-de-prueba-universales-de.jpg"));
        adapter = new ListAdapter(this, canciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Canciones.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
