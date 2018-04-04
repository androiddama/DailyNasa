package com.example.soul.dailynasa.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soul.dailynasa.R;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Button btn; //boton -veure imatge-

    private static final String TAG = "MainActivity";
    private static final Date FECHALIMITEB = new Date(1995, 6, 16);
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private boolean botonok = false; //saber si ya se le ha dado al boton ok alguna vez
    private int years, months, days;
    BlurImageView myBlurImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonListener();

        mDisplayDate = (TextView) findViewById(R.id.date);
        myBlurImage = (BlurImageView) findViewById(R.id.myBlurImage);
        myBlurImage.setBlur(2);

        //función para mostrar el DatePickDialog si se presiona el botón: 'Fecha'.
        //(o bien si se presiona una fecha ya escogida y que se muestra en lugar del botón 'Fecha').
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year;
                int month;
                int day;

                if(botonok){//si se le ha dado al ok anteriormente hacer que se muestren los ultimos valores elegidos
                    year = years;
                    month = months;
                    day = days;
                }
                //hace un get para el año, mes y dia actuales si todavia no se le ha dado al ok
                else {
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                }

                //muestra el DatePickerDialog
                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Botón 'ok': Para mostrar la fecha escogida.
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "el valor de botonok1:" + botonok);
                botonok = true; //cuando se presion en el boton ok el valor pasa a cierto
                //guardar los valores escogidos en las variables globales para mostrarlos la proxima
                //vez que se muestre el datepickerdialog
                years = year;
                months = month;
                days = dayOfMonth;
                Log.d(TAG, "el valor de botonok2:" + botonok);

                month = month +1;

                Calendar cal2 = Calendar.getInstance(); //crear otro calendario para mirar la fecha actual


                //creo un objeto date con la fecha escogida para compararla con la fecha limite
                Date fechaescogida = new Date(year, month, dayOfMonth);

                //creo un objeto date con la fecha actual para compararla con la fecha escogida
                Date fechalimitea = new Date(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH)+1, cal2.get(Calendar.DAY_OF_MONTH));

                //controlar que la fecha escogida no sea anterior a la FECHALIMITEB
                int t1 = FECHALIMITEB.compareTo(fechaescogida); //comparar FECHALIMITEB y fecha escogida

                    Log.d(TAG, "onDateSet: mm/dd/yyyy: " + cal2.get(Calendar.YEAR) +  cal2.get(Calendar.MONTH) +  cal2.get(Calendar.DAY_OF_MONTH));

                if(t1 > 0) {//si la fecha escogida es anterior

                    Log.d(TAG, "entra en el if t1");

                    //indicar la restriccion con un toast
                    Toast.makeText(getApplicationContext(),"La fecha tiene que ser posterior a 1995/6/16", Toast.LENGTH_SHORT ).show();
                    //cambiar la fecha escogida por una valida(la minima posible)
                    year = 1995;
                    month = 6;
                    dayOfMonth = 16;
                }

                //controlar que la fecha escogida no sea posterior a la fechalimitea(fecha actual)
                int t2 = fechalimitea.compareTo(fechaescogida); //comparar fechalimitea y fecha escogida
                if(t2 < 0) {//si la fecha escogida es posterior a la de hoy

                    Log.d(TAG, "entra en el if t2");

                    //indicar la restriccion con un toast
                    Toast.makeText(getApplicationContext(),"La fecha tiene que ser anterior a hoy", Toast.LENGTH_SHORT ).show();
                    //cambiar la fecha escogida por una valida(la maxima posible)
                    year = cal2.get(Calendar.YEAR);
                    month = cal2.get(Calendar.MONTH);
                    dayOfMonth = cal2.get(Calendar.DAY_OF_MONTH);
                }
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String dates = year + "-" + month + "-" + dayOfMonth;
                mDisplayDate.setText(dates);
            }
        };
    }

    private void addButtonListener() {

        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dato = mDisplayDate.getText().toString();

                //Intent para pasar la fecha a la activity que muestra la imagen de la NASA.
                /*(Coje el dato del DatePickerDialog y lo envia a la nueva activity cuando se
                presiona el boton VEURE IMATGE*/

                Intent i = new Intent("com.example.soul.dailynasa.PhotoNasa");
                i.putExtra("FECHA", dato);
                startActivity(i);
            }
        });
    }
}
