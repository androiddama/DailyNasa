package com.example.soul.dailynasa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Button btn;

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonListener();

        mDisplayDate = (TextView) findViewById(R.id.date);

        //función para mostrar el DatePickDialog si se presiona el botón: 'Fecha'.
        //(o bien si se presiona una fecha ya escogida y que se muestra en lugar del botón 'Fecha').
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();

                //hace un get para el año, mes y dia actuales.
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

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
                month = month +1;

                Calendar cal2 = Calendar.getInstance(); //crear otro calendario para mirar la fecha actual

                //mirar si los años son válidos.
                if(year < 1995) {
                    year = 1995;
                    if(month < 6) {
                        month = 6;
                        if(dayOfMonth < 16) dayOfMonth = 16;
                    }
                }
                else if(year > cal2.get(Calendar.YEAR)) year = cal2.get(Calendar.YEAR);

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
