package com.studium.xxracso40xx.pi_android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studium.xxracso40xx.pi_android.model.CancionObject;

import java.util.ArrayList;


public class Principal extends AppCompatActivity
{
    Button buttonPrincipalCanciones;
    Button buttonPrincipalPerfil;
    Button playMiniReproductor;
    Intent intentCanciones;
    Intent intentPerfil;
    Intent music;
    TextView cancionNombre, cancionAutor;
    ImageView imageRap;
    ImageView imageAnime;
    ImageView imageKpop;
    ImageView imagePop;
    ImageView imagenCancion;
    LinearLayout layoutTouch;
    LinearLayout toolbar_layout;
    private boolean mIsBound = false;
    private MusicService mServ;
    public ListView listviewPrincipal;
    float x1,x2,y1,y2;

    ArrayList<CancionObject> canciones = new ArrayList<>();


    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonPrincipalCanciones = findViewById(R.id.buttonPrincipalCanciones);

        imageRap = findViewById(R.id.imageRap);
        imageAnime = findViewById(R.id.imageAnime);
        imageKpop = findViewById(R.id.imageKpop);
        imagePop = findViewById(R.id.imagePop);

        buttonPrincipalPerfil = findViewById(R.id.buttonPrincipalPerfil);
        playMiniReproductor = findViewById(R.id.play_button);
        layoutTouch = findViewById(R.id.layoutTouch);
        intentCanciones = new Intent(this, Canciones.class);
        intentPerfil = new Intent(this, Perfil.class);
        //listviewPrincipal = findViewById(R.id.listviewPrincipal);
        cancionNombre = findViewById(R.id.songs_title);
        cancionAutor = findViewById(R.id.songs_artist_name);
        imagenCancion = findViewById(R.id.songs_cover_one);
        toolbar_layout = findViewById(R.id.toolbar_layout);

        buttonPrincipalCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(intentCanciones);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        imageRap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.imageRap = true;
                startActivity(intentCanciones);

            }
        });

        imageAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.imageAnime = true;
                startActivity(intentCanciones);

            }
        });

        imageKpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.imageKpop = true;
                startActivity(intentCanciones);

            }
        });

        imagePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                App.imagePop = true;
                startActivity(intentCanciones);

            }
        });


        buttonPrincipalPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentPerfil);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
        layoutTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.urlCancionActual!=null)
                {
                    startActivity(new Intent(Principal.this, ReproductorMusicaV2.class));
                }
                else
                {
                    Toast.makeText(Principal.this, "Reproduzca una canción primero", Toast.LENGTH_SHORT).show();
                }
            }
        });
        playMiniReproductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music = new Intent();
                if(App.contadorReproductorMusica==1)
                {
                    playMiniReproductor.setBackgroundResource(R.drawable.play);
                    App.contadorReproductorMusica=2;
                }
                else if(App.contadorReproductorMusica==2)
                {
                    /*music.setClass(Principal.this,MusicService.class);
                    startService(music);
                    */
                    App.cancionTerminada=false;
                    App.contadorcontador1=true;
                    playMiniReproductor.setBackgroundResource(R.drawable.stop);
                    App.contadorReproductorMusica=1;
                }
            }
        });




    }
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    public void onResume() {
        if(App.cancionTerminada==true)
        {
            playMiniReproductor.setBackgroundResource(R.drawable.play);
            App.urlCancionActualMini=App.urlCancionActual;

            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionAutor.setText(App.artistaCancionSeleccionada);
            Picasso
                    .with(Principal.this)
                    .load(App.urlImagenCancionSeleccionada)
                    .into(imagenCancion);
        }
        else if(App.contadorReproductorMusica==1)
        {
            playMiniReproductor.setBackgroundResource(R.drawable.stop);
            App.urlCancionActualMini=App.urlCancionActual;

            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionAutor.setText(App.artistaCancionSeleccionada);
            Picasso
                    .with(Principal.this)
                    .load(App.urlImagenCancionSeleccionada)
                    .into(imagenCancion);
        }
        else if(App.contadorReproductorMusica==2)
        {
            playMiniReproductor.setBackgroundResource(R.drawable.play);
            App.urlCancionActualMini=App.urlCancionActual;

            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionAutor.setText(App.artistaCancionSeleccionada);
            Picasso
                    .with(Principal.this)
                    .load(App.urlImagenCancionSeleccionada)
                    .into(imagenCancion);
        }
        if(App.urlCancionActual!=null)
        {
            doBindService();
        }
        /*if(App.urlCancionActual!=null)
        {
            App.urlCancionActualMini = App.urlCancionActual;
            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionAutor.setText(App.artistaCancionSeleccionada);
            Picasso
                    .with(Principal.this)
                    .load(App.urlImagenCancionSeleccionada)
                    .into(imagenCancion);

        }
        */
        super.onResume();
        App.listaCancionesPrincipal = canciones;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        if(App.urlCancionActual!=null)
                        {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run()
                                {
                                    if(App.urlCancionActualMini!=App.urlCancionActual)
                                    {
                                        App.urlCancionActualMini=App.urlCancionActual;

                                        cancionNombre.setText(App.nombreCancionSeleccionada);
                                        cancionNombre.setText(App.nombreCancionSeleccionada);
                                        cancionAutor.setText(App.artistaCancionSeleccionada);
                                        Picasso
                                                .with(Principal.this)
                                                .load(App.urlImagenCancionSeleccionada)
                                                .into(imagenCancion);
                                    }
                                    if(toolbar_layout.getVisibility()== View.INVISIBLE)
                                    {
                                        toolbar_layout.setVisibility(View.VISIBLE);
                                    }
                                    if(App.cancionTerminada==true)
                                    {
                                        playMiniReproductor.setBackgroundResource(R.drawable.play);
                                    }
                                }
                            });
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        doUnbindService();

    }

}

