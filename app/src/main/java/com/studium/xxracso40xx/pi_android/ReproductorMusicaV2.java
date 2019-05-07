package com.studium.xxracso40xx.pi_android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class ReproductorMusicaV2 extends AppCompatActivity
{
    Button botonIniciar;
    SeekBar BarraPosicion;
    TextView tiempoTranscurrido;
    TextView tiempoRestante;
    Intent music;
    int tiempoTotal;
    ImageView imagenCancion;
    TextView nombreCancion;
    TextView autorCancion;
    private boolean mIsBound = false;
    private MusicService mServ;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_musica);
        botonIniciar = findViewById(R.id.botonIniciar);
        tiempoTranscurrido = findViewById(R.id.tiempoTranscurrido);
        tiempoRestante = findViewById(R.id.tiempoRestante);
        BarraPosicion = findViewById(R.id.BarraPosicion);
        imagenCancion = findViewById(R.id.profile_image);
        nombreCancion = findViewById(R.id.textViewNombreCancion);
        autorCancion = findViewById(R.id.textViewArtistaCancion);
        nombreCancion.setText(App.nombreCancionSeleccionada);
        new ReproductorMusicaV2.DownLoadImageTask(imagenCancion).execute(App.urlImagenCancionSeleccionada);
        music = new Intent();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int posicionActual = msg.what;
            // Update BarraPosicion.
            BarraPosicion.setProgress(posicionActual);
            // Update Labels.
            String tiempoTranscurrido = CrearTextViewTiempo(posicionActual);
            ReproductorMusicaV2.this.tiempoTranscurrido.setText(tiempoTranscurrido);
            String tiempoRestante = CrearTextViewTiempo(mServ.posicionFinal() -posicionActual);
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
                            App.SALTADOBARRACANCION=true;
                            App.tiempoActualCancionActual=progeso;
                            mServ.resumeMusic();
                            // mService.getMediaPlayer().seekTo(progeso);
                            BarraPosicion.setProgress(progeso);
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
        if(App.contadorReproductorMusica==0)
        {
            botonIniciar.setBackgroundResource(R.drawable.stop);
            music.setClass(this,MusicService.class);
            startService(music);
            App.contadorReproductorMusica++;
        }
        else if(App.contadorReproductorMusica==1)
        {
            botonIniciar.setBackgroundResource(R.drawable.play);
            mServ.pauseMusic();
            App.contadorReproductorMusica++;
        }
        else if(App.contadorReproductorMusica==2)
        {
            botonIniciar.setBackgroundResource(R.drawable.stop);
            mServ.resumeMusic();
            App.contadorReproductorMusica=1;
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
    }
    @Override
    public void onResume() {
        super.onResume();
        if(App.contadorReproductorMusica==1)
        {
            botonIniciar.setBackgroundResource(R.drawable.stop);
        }
        else if(App.contadorReproductorMusica==2)
        {
            botonIniciar.setBackgroundResource(R.drawable.play);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if(mServ==null)
                        {

                        }
                        else
                        {

                            Message msg = new Message();
                            msg.what = mServ.PosicionActual();
                            handler.sendMessage(msg);

                            //Si la variable "REPETICION" es true, habrá modo repetición. falta añadir el botón para que si el botón está pulsado, se ponga
                            //REPETICION como true
                            if (mServ.PosicionActual() == mServ.posicionFinal() || App.REPETICION == true) {
                                mServ.mPlayer.setLooping(true);
                            }
                            positionBar();
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
    }

}
