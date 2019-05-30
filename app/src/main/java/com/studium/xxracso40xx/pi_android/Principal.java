package com.studium.xxracso40xx.pi_android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studium.xxracso40xx.pi_android.model.CancionObject;
import com.studium.xxracso40xx.pi_android.model.SectionDataModel;

import java.util.ArrayList;
import java.util.List;


public class Principal extends AppCompatActivity
{
    Button buttonPrincipalCanciones;
    Button buttonPrincipalPerfil;
    Button playMiniReproductor;
    Intent intentCanciones;
    Intent intentPerfil;
    Intent music;
    TextView cancionNombre, cancionAutor;
    ImageView imagenCancion;
    LinearLayout layoutTouch;
    private boolean mIsBound = false;
    private MusicService mServ;
    public ListView listviewPrincipal;
    float x1,x2,y1,y2;
    ArrayList<SectionDataModel> allSampleData;
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
        buttonPrincipalPerfil = findViewById(R.id.buttonPrincipalPerfil);
        playMiniReproductor = findViewById(R.id.play_button);
        layoutTouch = findViewById(R.id.layoutTouch);
        intentCanciones = new Intent(this, Canciones.class);
        intentPerfil = new Intent(this, Perfil.class);
        //listviewPrincipal = findViewById(R.id.listviewPrincipal);
        cancionNombre = findViewById(R.id.songs_title);
        cancionAutor = findViewById(R.id.songs_artist_name);
        imagenCancion = findViewById(R.id.songs_cover_one);
        allSampleData = new ArrayList<SectionDataModel>();
        buttonPrincipalCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(intentCanciones);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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

        //TODA ESTA PARTE ES PRUEBA DE RECYCLEVIEW
        createDummyData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


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
        }
        else if(App.contadorReproductorMusica==1)
        {
            playMiniReproductor.setBackgroundResource(R.drawable.stop);
        }
        else if(App.contadorReproductorMusica==2)
        {
            playMiniReproductor.setBackgroundResource(R.drawable.play);
        }
        if(App.urlCancionActual!=null)
        {
            doBindService();
        }
        if(App.urlCancionActual!=null)
        {
            App.urlCancionActualMini = App.urlCancionActual;
            cancionNombre.setText(App.nombreCancionSeleccionada);
            cancionAutor.setText(App.artistaCancionSeleccionada);
            Picasso
                    .with(Principal.this)
                    .load(App.urlImagenCancionSeleccionada)
                    .into(imagenCancion);
        }
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
                         App.urlCancionActualMini=App.urlCancionActual;
                        }
                        else if(App.urlCancionActualMini!=null && App.urlCancionActual!=null)
                        {
                            App.urlCancionActualMini=App.urlCancionActual;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run()
                                {
                                    cancionNombre.setText(App.nombreCancionSeleccionada);
                                    cancionNombre.setText(App.nombreCancionSeleccionada);
                                    cancionAutor.setText(App.artistaCancionSeleccionada);
                                    Picasso
                                            .with(Principal.this)
                                            .load(App.urlImagenCancionSeleccionada)
                                            .into(imagenCancion);
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
    public void createDummyData() {
        //Cambiar esto para crear tantas secciones como se hayan recogido de la base de datos.
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            //Cambiar este header por el nombre del genero(recibido del servidor)
            dm.setHeaderTitle("Género " + i);
            ArrayList<CancionObject> singleItem = new ArrayList<CancionObject>();
            //  Aquí se introducirá las diferentes canciones que haya en cada genero.
            //  Cabe destacar que la lista variará dependiendo de los que haya en cada genero.
            //  También es posible recibir muchos y limitarlo al número de elementos que deseemos.
            for (int j = 0; j <= 3; j++) {
                canciones.add(new CancionObject("Buenas", "Adolf", "https://ccrma.stanford.edu/~jos/mp3/trumpet.mp3", "https://www.dhresource.com/100x100s/f2-albu-g5-M01-54-A1-rBVaJFngIveAC1bUAAI1p63w2TE474.jpg/puntas-de-prueba-de-prueba-universales-de.jpg"));
                canciones.add(new CancionObject("asdfBuenas", "xdAcvxvdsadfa", "http://www.hochmuth.com/mp3/Tchaikovsky_Nocturne__orch.mp3 ", "https://timedotcom.files.wordpress.com/2014/12/spotify.jpg"));

            }

            dm.setAllItemsInSection(canciones);
            allSampleData.add(dm);
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        doUnbindService();

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

