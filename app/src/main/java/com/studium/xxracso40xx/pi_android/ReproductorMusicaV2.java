package com.studium.xxracso40xx.pi_android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ReproductorMusicaV2 extends AppCompatActivity
{
    Button botonIniciar;
    SeekBar BarraPosicion;
    TextView tiempoTranscurrido;
    TextView tiempoRestante;
    Intent music;
    int contador=0;
    int tiempoTotal;
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
        if(contador==0)
        {
            music.setClass(this,MusicService.class);
            startService(music);
            contador++;
            positionBar();
        }
        else if(contador==1)
        {
            mServ.pauseMusic();
            contador++;
        }
        else if(contador==2)
        {
            mServ.resumeMusic();
            contador=1;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message msg = new Message();
                        msg.what = mServ.PosicionActual();
                        handler.sendMessage(msg);

                        //Si la variable "REPETICION" es true, habrá modo repetición. falta añadir el botón para que si el botón está pulsado, se ponga
                        //REPETICION como true
                        if(mServ.PosicionActual()== mServ.posicionFinal() || App.REPETICION==true)
                        {
                            mServ.mPlayer.setLooping(true);
                        }
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
        //mServ.stopMusic();
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

}