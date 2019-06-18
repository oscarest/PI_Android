package com.studium.xxracso40xx.pi_android;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//HttpClientBuilder

import com.squareup.picasso.Picasso;
import com.studium.xxracso40xx.pi_android.model.CancionObject;
import com.studium.xxracso40xx.pi_android.model.Usuarios;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Canciones extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button buttonCancionesCanciones;
    Button buttonCancionesPerfil;
    Button buttonCancionesInicio;
    Intent intentCancionesGuardadas;
    Intent intentPrincipal;
    Intent intentPerfil;
    Button playMiniReproductor;
    String textoBusqueda;
    DrawerLayout drawerLayout;
    Intent intentReproductorMusica;
    Intent intentInicio;
    ListView list;
    TextView cancionNombre, cancionAutor;
    ImageView imagenCancion;
    Intent music;
    EditText editTextBusqueda;
    ImageView imagenBusqueda;
    LinearLayout layoutTouch;
    LinearLayout toolbar_layout;
    DownloadManager downloadManager;
    private boolean mIsBound = false;
    private MusicService mServ;
    private String APIserver = "http://8music.ddns.net/webserviceAndroid/";
    private ListAdapter adapter;
    ArrayList<CancionObject> canciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        setNavigationViewListener();
        //overridePendingTransition(R.anim.replace, R.anim.replaceto);
        buttonCancionesPerfil = findViewById(R.id.buttonCancionesPerfil);
        buttonCancionesInicio = findViewById(R.id.buttonCancionesInicio);
        intentPrincipal = new Intent(this, Principal.class);
        intentPerfil = new Intent(this, Perfil.class);
        intentReproductorMusica = new Intent(this, ReproductorMusicaV2.class);
        intentInicio = new Intent(this, MainActivity.class);

        editTextBusqueda = findViewById(R.id.editTextBusqueda);
        imagenBusqueda = findViewById(R.id.imageViewBusqueda);
        layoutTouch = findViewById(R.id.layoutTouch_canciones);
        toolbar_layout = findViewById(R.id.toolbar_layout_canciones);
        cancionNombre = findViewById(R.id.songs_title_canciones);
        cancionAutor = findViewById(R.id.songs_artist_name_canciones);
        imagenCancion = findViewById(R.id.songs_cover_one_canciones);
        list = findViewById(R.id.list_view);
        playMiniReproductor = findViewById(R.id.play_button_canciones);
        drawerLayout = findViewById(R.id.drawer_layout);
        music = new Intent();
        //quitar este mostrarList cuando se vaya a cambiar a funcionalidad real con servidor.
        //mostrarList();
        //DE AQUÍ LEEREMOS LOS DATOS PERSONALES DEL USUARIO QUE ESTÁ LOGUEADO PARA PODER SACAR LAS CANCIONES QUE ESTA PERSONA TIENE EN SU BIBLIOTECA.
        //LO MEJOR SERÍA INTRODUCIR EL ID DEL USUARIO QUE ESTA LOGUEADO PARA PODER SACAR ESTAS CANCIONES.
        //COMENTADO HASTA QUE SE HAGA EL ARCHIVO .PHP
        //new Canciones.DB_Apache().execute("get-product.php?idUsuario=" + App.ID_USUARIO);
        if (App.imageRap == true){

            new Canciones.DB_Apache().execute("get-cancionesBusqueda.php?stringBusqueda=Rap");
            App.imageRap = false;

        } else if (App.imageAnime == true){


            new Canciones.DB_Apache().execute("get-cancionesBusqueda.php?stringBusqueda=Anime");
            App.imageAnime = false;


        } else if(App.imageKpop == true){


            new Canciones.DB_Apache().execute("get-cancionesBusqueda.php?stringBusqueda=KPop");
            App.imageKpop = false;


        } else if(App.imagePop == true){


            new Canciones.DB_Apache().execute("get-cancionesBusqueda.php?stringBusqueda=Pop");
            App.imagePop = false;


        } else {

            new Canciones.DB_Apache().execute("get-canciones.php?");

        }

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
        layoutTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.urlCancionActual!=null)
                {
                    startActivity(new Intent(Canciones.this, ReproductorMusicaV2.class));
                }
                else
                {
                    Toast.makeText(Canciones.this, "Reproduzca una canción primero", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imagenBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Falta por crear el archivo php, pero esto debería funcionar correctamente de esta forma
                canciones= new ArrayList<>();
                textoBusqueda = editTextBusqueda.getText().toString().replace(" ", "+");
                new Canciones.DB_Apache().execute("get-cancionesBusqueda.php?stringBusqueda="+ textoBusqueda);


            }
        });

    }

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
        if(!editTextBusqueda.getText().toString().isEmpty() || App.imageRap == false || App.imageAnime == false || App.imageKpop == false || App.imagePop == false){

            canciones= new ArrayList<>();
            new Canciones.DB_Apache().execute("get-canciones.php?");

        } else {
            editTextBusqueda.setText("");
            finish();
        }

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(Canciones.this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.op1:
                canciones= new ArrayList<>();
                //new Canciones.DB_Apache().execute("get-canciones.php?nombreCancion="+editTextBusqueda.getText().toString());
                new Canciones.DB_Apache().execute("get-cancionesGuardadas.php?idUsuarioFK=" + App.ID_USUARIO);
                break;
            case R.id.op2:
              //  startActivity;
                break;

            case R.id.op3:
                startActivity(intentInicio);

                App.ID_USUARIO = 0;
                App.nickUsuario = "";
                App.nombreUsuario = "";
                App.apellidosUsuario = "";
                App.emailUsuario = "";
                App.direccionUsuario = "";
                App.fechaNacimientoUsuario = "";

        }
        Log.i("datos", "funcionaPulso");
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    /*public void mostrarList()
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
    */

    public void mostrarList()
    {
        //CABE DESTACAR QUE FUNCIONA CORRECTAMENTE LOS DATOS SI SE INTRODUCEN DE FORMA MANUAL
        //TAMBIÉN CABE DESTACAR QUE SE DEBE OPTIMIZAR LA DESCARGA DE FOTOS E INTRODUCIR LAS MISMAS EN EL CACHE
        //Hacer un for que recibe vaya añadiendo uno a uno todos los elementos que se reciban de la base de datos.

       /* canciones.add(new CancionObject("Buenas", "asd","https://ccrma.stanford.edu/~jos/mp3/viola.mp3", "https://www.absaonline.mx/pub/media/catalog/product/cache/1/image/500x608/e9c3970ab036de70892d86c6d221abfe/2/0/20812_TL222.png"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        canciones.add(new CancionObject("Buenasaasdas", "asd", "https://ccrma.stanford.edu/~jos/mp3/gtr-wah.mp3", "https://image.made-in-china.com/3f2j10wdqTSaMRhGri/Accurate-One-Step-Pregnancy-Te.jpg"));
        */
        adapter = new ListAdapter(this, canciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

               /* Button botonCancion = view.findViewById(R.id.buttonDescargaLista);
                botonCancion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(App.urlCancionActual!=null)
                        {
                            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(App.urlCancionActual);
                            DownloadManager.Request request =  new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long reference = downloadManager.enqueue(request);
                        }
                    }
                });
                */
                App.nombreCancionSeleccionada = canciones.get(position).getNombreCancion();
                App.urlCancionSeleccionada = canciones.get(position).getUrlCancion();
                App.urlImagenCancionSeleccionada = canciones.get(position).getUrlImagenCancion();
                App.artistaCancionSeleccionada = canciones.get(position).getAutorCancion();
                App.posicionListaCanciones = position;
                App.listaCanciones = canciones;
                if(App.urlCancionSeleccionada!=App.urlCancionActual && App.urlCancionActual!=null)
                {
                    App.contadorReproductorMusica=2;
                    App.resetearCancion=true;
                }
                else
                    {
                    startActivity(intentReproductorMusica);
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



    private class DB_Apache extends AsyncTask<String, Void, Boolean> {

        private String json;

        protected Boolean doInBackground(String... urls) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(APIserver + urls[0]));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
                return false;
            }
            return true;
        }


        protected void onPostExecute(Boolean isOk) {
            try {
                if (isOk) {
                    JSONArray response = new JSONArray(json);
                    canciones = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        canciones.add(new CancionObject
                                (
                                response.getJSONObject(i).getString("nombreCancion"),
                                response.getJSONObject(i).getString("autorCancion"),
                                response.getJSONObject(i).getString("urlCancion"),
                                response.getJSONObject(i).getString("urlImagenCancion")

                               /* , response.getJSONObject(i).getString("nickUsuario")
                                , response.getJSONObject(i).getString("claveUsuario")
                                , response.getJSONObject(i).getInt("tipoUsuario")
                                , response.getJSONObject(i).getInt("algunaSuscripcionUsuario")*/

                        ));
                    }

                    //SE PUEDE HACER CON EL OBJETO DEL MODELO COMO PODEMOS OBSERVAR PREVIAMENTE
                    //PERO TAMBIÉN TENEMOS QUE VER LA POSIBILIDAD DE GUARDAR DIRECTAMENTE LA STRING COMO MOSTRAREMOS
                    //EN EL CÓDIGO A CONTINUACIÓN
                    //String nameJson = response.getJSONObject(1).getString("nombreUsuario");
                    //String contrasenaJson = response.getJSONObject(2).getString("contrasena_usuario");
                    if(!canciones.isEmpty())
                    {
                        mostrarList();
                    }
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

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
                    .with(Canciones.this)
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
                    .with(Canciones.this)
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
                    .with(Canciones.this)
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
                                                .with(Canciones.this)
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
    public void onDestroy() {

        super.onDestroy();
        doUnbindService();
    }

}
