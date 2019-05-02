package com.studium.xxracso40xx.pi_android;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class ReproductorMusica extends AppCompatActivity {
    Button botonIniciar;
    SeekBar BarraPosicion;
    TextView tiempoTranscurrido;
    TextView tiempoRestante;
    MediaPlayer reproductorMusica;
    int tiempoTotal;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_musica);
        botonIniciar =findViewById(R.id.botonIniciar);
        tiempoTranscurrido = findViewById(R.id.tiempoTranscurrido);
        tiempoRestante =findViewById(R.id.tiempoRestante);
        BarraPosicion = findViewById(R.id.BarraPosicion);
        //BarraVolumen =findViewById(R.id.BarraVolumen);


        // Media Player
        reproductorMusica = new MediaPlayer();
        try {
            reproductorMusica.setDataSource("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3");
            reproductorMusica.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        reproductorMusica.setLooping(true);
        reproductorMusica.seekTo(0);
        reproductorMusica.setVolume(0.5f, 0.5f);
        tiempoTotal = reproductorMusica.getDuration();
        positionBar();
        //VolumeBar();
        // Thread (Update BarraPosicion & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (reproductorMusica != null) {
                    try {
                        Message msg = new Message();
                        msg.what = reproductorMusica.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int posicionActual = msg.what;
            // Update BarraPosicion.
            BarraPosicion.setProgress(posicionActual);

            // Update Labels.
            String tiempoTranscurrido = CrearTextViewTiempo(posicionActual);
            ReproductorMusica.this.tiempoTranscurrido.setText(tiempoTranscurrido);
            String tiempoRestante = CrearTextViewTiempo(tiempoTotal -posicionActual);
            ReproductorMusica.this.tiempoRestante.setText("- " + tiempoRestante);
        }
    };

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

        if (!reproductorMusica.isPlaying()) {
            // Stopping
            reproductorMusica.start();
            botonIniciar.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            reproductorMusica.pause();
            botonIniciar.setBackgroundResource(R.drawable.play);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void positionBar()
    {
        // Position Bar
        BarraPosicion.setMax(tiempoTotal);
       //ESTAS DOS LÍNEAS NECESITAN MÍNIMO LA API JELLY BEAN PARA FUNCIONAR
        BarraPosicion.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        BarraPosicion.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        BarraPosicion.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progeso, boolean fromUser) {
                        if (fromUser) {
                            reproductorMusica.seekTo(progeso);
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
    /*public void VolumeBar()
    {
        // Volume Bar
        BarraVolumen.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progeso, boolean fromUser) {
                        float volumeNum = progeso / 100f;
                        reproductorMusica.setVolume(volumeNum, volumeNum);
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
    */

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }
    private void killMediaPlayer() {
        if(reproductorMusica!=null) {
            try {
                reproductorMusica.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
}