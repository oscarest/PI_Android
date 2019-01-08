package com.studium.xxracso40xx.pi_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
EditText edit1, edit2, edit3, edit4, edit5, edit6;
Button boton1, boton2;
Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //edit1=fecha
        edit1 = findViewById(R.id.editText8);
        edit2 = findViewById(R.id.editText);
        edit3 = findViewById(R.id.editText3);
        edit4 = findViewById(R.id.editText4);
        edit5 = findViewById(R.id.editText5);
        edit6 = findViewById(R.id.editText6);
        boton1 = findViewById(R.id.button8);
        boton2 = findViewById(R.id.button9);
        intent1 = new Intent(this, MainActivity.class );
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Poner aquí toda la funcionalidad
                Toast toast = Toast.makeText(getApplicationContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT);
                toast.show();
               //Borrar los datos solo si ha funcionado correctamente el registro
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
                edit5.setText("");
                edit6.setText("");
                startActivity(intent1);
                /*
                Toast toast1 = Toast.makeText(getApplicationContext(),"Ha ocurrido un error en el registro", Toast.LENGTH_SHORT);
                toast1.show();
                 */
                //FALTA POR AÑADIR TAMBIÉN EL CASO EN EL QUE ALGÚN ELEMENTO SE QUEDE VACÍO
            }
        });
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
                edit5.setText("");
                edit6.setText("");
                startActivity(intent1);
            }
        });

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editText8:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                edit1.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
