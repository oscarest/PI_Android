package com.studium.xxracso40xx.pi_android;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import com.studium.xxracso40xx.pi_android.App;

import java.io.IOException;
import java.util.Random;

public class ServicioMusica extends Service {
    private MediaPlayer player;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        ServicioMusica getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServicioMusica.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
   @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
       player = new MediaPlayer();
       try {
            player.setDataSource(App.urlCancionActual);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.seekTo(App.tiempoActualCancionActual*1000);
        player.setLooping(false);
        player.setVolume(0.5f, 0.5f);
        App.tiempoTotalCancionActual = player.getDuration();
        player.start();
       new Thread(new Runnable() {
            @Override
            public void run() {
                while (player != null) {
                    try {
                       /* if(App.tiempoActualCancionActual== player.getCurrentPosition()-1)
                        {
                            App.tiempoActualCancionActual = player.getCurrentPosition();
                        }
                        //Ha ocurrido un salto en el ReproductorMusica, cambiando la variable en App
                        else
                        {
                            //player.seekTo(App.tiempoActualCancionActual*1000);
                        }
                        */
                        App.tiempoActualCancionActual = player.getCurrentPosition();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        //return START_STICKY;
        return START_NOT_STICKY;
    }


    public int IniciarCancion(){
        //this.player.start();
        return 0;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
    }
    public MediaPlayer getMediaPlayer(){
        return player;
    }
    public int getTiempoTotal(){
        return player.getDuration();
    }

}
