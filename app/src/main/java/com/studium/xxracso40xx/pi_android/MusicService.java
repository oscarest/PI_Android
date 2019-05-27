package com.studium.xxracso40xx.pi_android;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service  implements MediaPlayer.OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    Boolean esperarResetearCancion=false;

    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public void crearCancion()
    {
        App.urlCancionActual=App.urlCancionSeleccionada;

        try {
            mPlayer = MediaPlayer.create(this, Uri.parse(App.urlCancionActual));
           // mPlayer.seekTo(App.tiempoActualCancionActual);
            mPlayer.setLooping(false);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(App.pulsarBotonRepetir==false)
                    {
                        //PONER AQUÍ LO QUE SE DESEE REALIZAR CUANDO SE ACABE LA CANCIÓN SIN REPETICIÓN
                        mPlayer.seekTo(0);
                        App.cancionTerminada=true;
                        App.contadorReproductorMusica=2;
                    }
                    else
                    {
                        mPlayer.seekTo(0);
                        mPlayer.start();
                    }

                }
            });
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp)
            {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            /*if(App.REPETICION==true)
                            {
                                mPlayer.setLooping(true);
                            }
                            if(App.pararCancion==2)
                            {
                                if(!mPlayer.isPlaying())
                                {
                                    mPlayer.start();
                                }
                            }
                            else if(App.pararCancion==1)
                            {
                                if(mPlayer.isPlaying()) {
                                    mPlayer.pause();
                                }
                            }
                            */
                            /*if(App.pulsarBotonRepetir==true)
                            {
                                if(App.repeticCancion==true)
                                {
                                    mPlayer.setLooping(true);
                                }
                                else
                                {
                                    mPlayer.setLooping(false);
                                }
                            }
                            */
                            if (App.contadorReproductorMusica == 1)
                            {
                                if(App.contadorcontador1==true)
                                {
                                    mPlayer.start();
                                    App.contadorcontador1=false;
                                }
                                    /*if (!mPlayer.isPlaying())
                                    {
                                            mPlayer.start();
                                    }
                                    if(mPlayer.isPlaying())
                                    {
                                        if(App.repeticCancion==2)
                                        {
                                        mPlayer.pause();
                                        }
                                    }
                                    */
                                }

                             else if (App.contadorReproductorMusica == 2) {
                                if (mPlayer.isPlaying()) {
                                    App.tiempoActualCancionActual = mPlayer.getCurrentPosition();
                                    mPlayer.pause();
                                }
                            }
                             if(App.resetearCancion==true)
                             {
                                 esperarResetearCancion=true;
                                 App.urlCancionActual=App.urlCancionSeleccionada;
                                /* mPlayer.release();
                                 mPlayer = MediaPlayer.create(MusicService.this, Uri.parse(App.urlCancionActual));
                                */
                                App.resetearCancion=false;
                                mPlayer.reset();
                                mPlayer = MediaPlayer.create(MusicService.this, Uri.parse(App.urlCancionActual));
                                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                     @Override
                                     public void onPrepared(final MediaPlayer mp)
                                     {
                                         if(App.saltarBotonCancion==true)
                                         {
                                             App.saltarBotonCancion=false;
                                         }
                                         else
                                             {

                                             startActivity(new Intent(MusicService.this, ReproductorMusicaV2.class));
                                             }
                                         esperarResetearCancion=false;
                                     }});
                             }
                        }
                    }

                }
                ).start();
            }
        });
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    } catch (IllegalStateException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        crearCancion();
        /*mPlayer.create(this, Uri.parse(App.urlCancionActual));
        mPlayer.prepare();
        mPlayer.setOnErrorListener(this);
        if (mPlayer != null) {
            mPlayer.setLooping(false);
            mPlayer.setVolume(100, 100);
        }*/
        mPlayer.setOnErrorListener(new OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra) {

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            length = mPlayer.getCurrentPosition();
            mPlayer.pause();
        }
    }
    public int PosicionActual()
    {
        if(esperarResetearCancion==true)
        {
            return 0;
        }
        else
        {
            return mPlayer.getCurrentPosition();
        }
    }

    public int posicionFinal()
    {
        if(esperarResetearCancion==true)
        {
            return 0;
        }
        else {
            return mPlayer.getDuration();
        }
    }
    private synchronized void pause() {
        // Sometimes the call to isPlaying can throw an error "internal/external state mismatch corrected"
        // When this happens, I think the player moves itself to "paused" even though it's still playing.
        try{
            // this is a hack, but it seems to be the most consistent way to address the problem
            mPlayer.stop();
            mPlayer.prepare();
            mPlayer.seekTo(0);
        } catch (Exception e){
            Log.w("Error", "Caught exception while trying to pause ", e);
        }
    }
    public void resumeMusic() {
            if(mPlayer.isPlaying())
            {
                mPlayer.pause();
                mPlayer.seekTo(App.tiempoActualCancionActual);
                mPlayer.start();
            }
            else
            {
                mPlayer.seekTo(App.tiempoActualCancionActual);
            }

    }
    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
    /*public void finalizarServicio()
    {
        this.stopSelf();
    }
    */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
