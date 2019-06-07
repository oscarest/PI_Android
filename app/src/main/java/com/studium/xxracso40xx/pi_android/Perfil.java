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
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    Button boton1;
    Button boton3;
    Button boton4;
    Intent intent;
    Intent intent3;
    TextView editTextPerfilNombre, editTextPerfilApellidos, editTextPerfilNombreUsuario, editTextPerfilEmail, editTextPerfilFechaDeNacimiento, editTextPerfilDireccion;
    private boolean mIsBound = false;
    private MusicService mServ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //overridePendingTransition(android.R.anim.replace, android.R.anim.replaceto);
        boton1 = findViewById(R.id.buttonPerfilCanciones);
        boton4 = findViewById(R.id.buttonPrincipalInicio);
        editTextPerfilNombre = findViewById(R.id.editTextPerfilNombre);
        editTextPerfilApellidos = findViewById(R.id.editTextPerfilApellidos);
        editTextPerfilNombreUsuario = findViewById(R.id.editTextPerfilNombreUsuario);
        editTextPerfilEmail = findViewById(R.id.editTextPerfilEmail);
        editTextPerfilFechaDeNacimiento = findViewById(R.id.editTextPerfilFechaDeNacimiento);
        editTextPerfilDireccion = findViewById(R.id.editTextPerfilDireccion);

        editTextPerfilNombre.setText(App.nombreUsuario);
        editTextPerfilApellidos.setText(App.apellidosUsuario);
        editTextPerfilNombreUsuario.setText(App.nickUsuario);
        editTextPerfilEmail.setText(App.emailUsuario);
        editTextPerfilFechaDeNacimiento.setText(App.fechaNacimientoUsuario);
        editTextPerfilDireccion.setText(App.direccionUsuario);



;        intent = new Intent(this, Canciones.class);
        intent3 = new Intent(this, Principal.class);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
        boton4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

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
    public void onDestroy() {

        super.onDestroy();
        doUnbindService();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (App.urlCancionActual != null) {
            doBindService();
        }
    }
}