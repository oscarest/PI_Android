package com.studium.xxracso40xx.pi_android;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class ReproductorMusicaV2 extends AppCompatActivity
{
    Button botonIniciar, botonDescarga, botonBack, botonForward, botonRepetir;
    ImageButton imageButtonLike;
    SeekBar BarraPosicion;
    TextView tiempoTranscurrido;
    TextView tiempoRestante;
    Intent music;
    int posicionFinal;
    int tiempoTotal;
    Boolean dobleClickForward=false;
    ImageView imagenCancion;
    TextView nombreCancion;
    TextView autorCancion;
    int posicionActual;
    DownloadManager downloadManager;
    private boolean mIsBound = false;
    private MusicService mServ;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_musica);
        imageButtonLike = findViewById(R.id.imageButtonLike);
        botonIniciar = findViewById(R.id.botonIniciar);
        tiempoTranscurrido = findViewById(R.id.tiempoTranscurrido);
        tiempoRestante = findViewById(R.id.tiempoRestante);
        BarraPosicion = findViewById(R.id.BarraPosicion);
        imagenCancion = findViewById(R.id.profile_image);
        nombreCancion = findViewById(R.id.textViewNombreCancion);
        autorCancion = findViewById(R.id.textViewArtistaCancion);
        botonDescarga = findViewById(R.id.buttonDescaga);
        nombreCancion.setText(App.nombreCancionSeleccionada);
        autorCancion = findViewById(R.id.textViewArtistaCancion);
        autorCancion.setText(App.artistaCancionSeleccionada);
        botonBack=findViewById(R.id.buttonBack);
        botonForward = findViewById(R.id.buttonForward);
        botonRepetir = findViewById(R.id.buttonRepetir);
        //new ReproductorMusicaV2.DownLoadImageTask(imagenCancion).execute(App.urlImagenCancionSeleccionada);
        Picasso
                .with(this)
                .load(App.urlImagenCancionSeleccionada)
                .into(imagenCancion);

        music = new Intent();
        botonRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.contadorRepetir==0)
                {
                    //REPETIR
                    App.pulsarBotonRepetir=true;
                    botonRepetir.setBackgroundResource(R.drawable.botonrepetirpulsado);
                    App.contadorRepetir=1;
                }
                else
                {
                    //NO REPETIR
                    App.pulsarBotonRepetir=false;
                    botonRepetir.setBackgroundResource(R.drawable.repeat);
                    App.contadorRepetir=0;
                }
            }
        });
        imageButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageButtonLike.setBackgroundResource(R.drawable.tick);

            }
        });

        botonDescarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.urlCancionActual!=null)
                {
                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(App.urlCancionActual);
                DownloadManager.Request request =  new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                }
            }
        });
        botonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonBack.setEnabled(false);
                if(App.posicionListaCanciones!=0)
               {
                   posicionActual = App.posicionListaCanciones-1;
               }
               if(posicionActual!=-1) {
                   App.cambiarReproductorMini=true;
                   App.posicionListaCanciones = posicionActual;
                   Log.i("posicion", "" + posicionActual);
                   App.nombreCancionSeleccionada = App.listaCanciones.get(posicionActual).getNombreCancion();
                   App.artistaCancionSeleccionada = App.listaCanciones.get(posicionActual).getAutorCancion();
                   App.urlImagenCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlImagenCancion();
                   App.urlCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlCancion();
                   autorCancion.setText(App.artistaCancionSeleccionada);
                   Picasso
                           .with(ReproductorMusicaV2.this)
                           .load(App.urlImagenCancionSeleccionada)
                           .into(imagenCancion);
                   nombreCancion.setText(App.nombreCancionSeleccionada);
                   App.saltarBotonCancion = true;
                   App.resetearCancion = true;
               }
               else if(posicionActual==-1)
               {
                   App.posicionListaCanciones=0;
               }
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        botonBack.setEnabled(true);
                    }
                },2000);// set time as per your requirement

            }
        });
        botonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonForward.setEnabled(false);
                posicionActual = App.posicionListaCanciones+1;
                if(posicionActual<App.listaCanciones.size())
                {
                        App.posicionListaCanciones = posicionActual;
                        Log.i("posicion", "" + posicionActual);
                        App.nombreCancionSeleccionada = App.listaCanciones.get(posicionActual).getNombreCancion();
                        App.artistaCancionSeleccionada = App.listaCanciones.get(posicionActual).getAutorCancion();
                        App.urlImagenCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlImagenCancion();
                        App.urlCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlCancion();
                        autorCancion.setText(App.artistaCancionSeleccionada);
                        Picasso
                                .with(ReproductorMusicaV2.this)
                                .load(App.urlImagenCancionSeleccionada)
                                .into(imagenCancion);
                        nombreCancion.setText(App.nombreCancionSeleccionada);
                        App.saltarBotonCancion = true;
                        App.resetearCancion = true;

                }
                else if(App.listaCanciones.size()==posicionActual)
                {
                    //App.posicionListaCanciones = -1;
                    App.posicionListaCanciones=0;
                    posicionActual = App.posicionListaCanciones;
                    App.nombreCancionSeleccionada = App.listaCanciones.get(posicionActual).getNombreCancion();
                    App.artistaCancionSeleccionada = App.listaCanciones.get(posicionActual).getAutorCancion();
                    App.urlImagenCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlImagenCancion();
                    App.urlCancionSeleccionada = App.listaCanciones.get(posicionActual).getUrlCancion();
                    autorCancion.setText(App.artistaCancionSeleccionada);
                    Picasso
                            .with(ReproductorMusicaV2.this)
                            .load(App.urlImagenCancionSeleccionada)
                            .into(imagenCancion);
                    nombreCancion.setText(App.nombreCancionSeleccionada);
                    App.saltarBotonCancion = true;
                    App.resetearCancion = true;
                }
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        botonForward.setEnabled(true);
                    }
                },2000);// set time as per your requirement
            }
        });
        //
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(App.resetearCancion==false)
            {
                posicionFinal=  mServ.posicionFinal();
            }
            int posicionActual = msg.what;
            // Update BarraPosicion.
            BarraPosicion.setProgress(posicionActual);
            // Update Labels.
            String tiempoTranscurrido = CrearTextViewTiempo(posicionActual);
            ReproductorMusicaV2.this.tiempoTranscurrido.setText(tiempoTranscurrido);
            String tiempoRestante = CrearTextViewTiempo(posicionFinal-posicionActual);
            ReproductorMusicaV2.this.tiempoRestante.setText("- " + tiempoRestante);
        }
    };
    public void positionBar()
    {
        // Position Bar
        BarraPosicion.setMax(mServ.posicionFinal());
        //ESTAS DOS LÍNEAS NECESITAN MÍNIMO LA API JELLY BEAN PARA FUNCIONAR
        BarraPosicion.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        //LA LÍNEA DE ABAJO ES PARA CAMBIAR LOS COLORES DE LA BARRA DE PROGESO. HAY QUE LIMITAR LA API PARA ELLO POR EOS ESTÁ COMENTADA POR
        //EL MOMENTO LA LÍNEA DE CÓDIGO.
        //BarraPosicion.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        BarraPosicion.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progeso, boolean fromUser) {
                        if (fromUser) {
                            App.tiempoActualCancionActual=progeso;
                            mServ.resumeMusic();
                            // mService.getMediaPlayer().seekTo(progeso);
                            BarraPosicion.setProgress(App.tiempoActualCancionActual);
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }
    public String CrearTextViewTiempo(int tiempo) {
        String tiempoTextView = "";
        int min = tiempo / 1000 / 60;
        int seg = tiempo / 1000 % 60;

        tiempoTextView = min + ":";
        if (seg < 10) tiempoTextView += "0";
        tiempoTextView += seg;

        return tiempoTextView;
    }
    public void BotonIniciar(View view) {
        if(App.contadorReproductorMusica==1)
        {
          //  music.setClass(this,MusicService.class);

                //mServ.mPlayer.release();
                //stopService(music);
                botonIniciar.setBackgroundResource(R.drawable.play);
                App.contadorReproductorMusica=2;

        }
        else if(App.contadorReproductorMusica==2)
        {
            /*music.setClass(this,MusicService.class);
            startService(music);
            botonIniciar.setBackgroundResource(R.drawable.stop);
            mServ.mPlayer.start();

            App.contadorReproductorMusica=1;
            */
            App.cancionTerminada=false;
            App.contadorcontador1=true;
            App.contadorReproductorMusica=1;
            botonIniciar.setBackgroundResource(R.drawable.stop);
        }

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
    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
    @Override
    public void onPause() {
        super.onPause();
       //mServ.mPlayer.reset();
        //mServ.mPlayer.release();
    }
   /* @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        App.urlCancionActual=null;
        App.urlImagenCancionSeleccionada=null;
        App.nombreCancionSeleccionada=null;
    }
    */
    @Override
    public void onResume() {
        super.onResume();
        if(App.contadorReproductorMusica==1)
        {
            botonIniciar.setBackgroundResource(R.drawable.stop);
            BarraPosicion.setProgress(App.tiempoActualCancionActual);

        }
        else if(App.contadorReproductorMusica==2)
        {
            botonIniciar.setBackgroundResource(R.drawable.play);
        }
        if(App.contadorRepetir==1)
        {
        botonRepetir.setBackgroundResource(R.drawable.botonrepetirpulsado);
        }
        else if(App.contadorRepetir==2)
        {
            botonRepetir.setBackgroundResource(R.drawable.botonrepetir);
        }
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if(mServ==null)
                    {

                    }
                    else if(App.urlCancionSeleccionada!=App.urlCancionActual && App.urlCancionSeleccionada!=null && App.urlCancionActual!=null)
                    {
                        mServ.posicionFinal();
                        App.urlCancionActual=App.urlCancionSeleccionada;
                       /* try {
                            mServ.mPlayer = MediaPlayer.create(ReproductorMusicaV2.this, Uri.parse(App.urlCancionActual));
                            mServ.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp)
                                {
                                    mServ.mPlayer.start();
                                    mServ.mPlayer.seekTo(0);
                                }
                            });
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }

                    }
                }}
        }).start();
*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if(mServ==null)
                        {
                        }
                        else if(mServ.mPlayer!=null)
                        {

                            int posicionActual = mServ.PosicionActual();
                            Message msg = new Message();
                            msg.what = posicionActual;
                            handler.sendMessage(msg);
                            if(App.cancionTerminada==true)
                            {
                                botonIniciar.setBackgroundResource(R.drawable.play);
                            }
                            //MÉTODO SIN REPETICIÓN
                            /*if(App.REPETICION==false && mServ.mPlayer.isPlaying()==false && App.contadorReproductorMusica==1)
                            {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        botonIniciar.setBackgroundResource(R.drawable.play);
                                    }
                                });
                                mServ.mPlayer.pause();
                                mServ.mPlayer.seekTo(0);
                                BarraPosicion.setProgress(0);
                                App.contadorReproductorMusica++;
                            }
                            */
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if(App.cambiarInterfaz==true)
                                    {
                                        autorCancion.setText(App.artistaCancionSeleccionada);
                                        nombreCancion.setText(App.nombreCancionSeleccionada);
                                        Picasso
                                                .with(ReproductorMusicaV2.this)
                                                .load(App.urlImagenCancionSeleccionada)
                                                .into(imagenCancion);
                                        App.cambiarInterfaz=false;

                                    }
                                    if(App.cambiarBotonInterfaz==true)
                                    {
                                        botonIniciar.setBackgroundResource(R.drawable.stop);
                                        App.cambiarBotonInterfaz=false;
                                    }

                                    // Stuff that updates the UI
                                    positionBar();

                                }
                            });
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

    }
}
