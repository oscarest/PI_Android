package com.studium.xxracso40xx.pi_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.security.auth.PrivateCredentialPermission;

public class Contrasena_Cambio extends AppCompatActivity {
Button boton1;
Button boton2;
Intent intent1;
Intent intent2;
EditText edit1;
EditText edit2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena__cambio);
        boton1 = findViewById(R.id.button10);
        boton2 = findViewById(R.id.button11);
        edit1 = findViewById(R.id.editText7);
        edit2 = findViewById(R.id.editText9);
        intent1 = new Intent(this, MainActivity.class);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent1);
                edit1.setText("");
                edit2.setText("");
            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(intent1);
                edit1.setText("");
                edit2.setText("");
            }
        });
    }
}
