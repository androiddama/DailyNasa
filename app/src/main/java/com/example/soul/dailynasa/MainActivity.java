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

public class MainActivity extends AppCompatActivity {

    public Button btn;
    /*Spinners
    Spinner Meses;
    Spinner Dias;
    Spinner Años;
    */
    //segunta version de la fecha
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonListener();

        /*spinners
        Meses = (Spinner) findViewById(R.id.sp1);
        Dias = (Spinner) findViewById(R.id.sp2);
        Años = (Spinner) findViewById(R.id.sp3);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Meses, android.R.layout.simple_spinner_item);
        Meses.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Dias, android.R.layout.simple_spinner_item);
        Dias.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Años, android.R.layout.simple_spinner_item);
        Años.setAdapter(adapter3);
        */

        //segunda version fecha
        mDisplayDate = (TextView) findViewById(R.id.date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
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

                Intent i = new Intent("com.example.soul.dailynasa.PhotoNasa");
                i.putExtra("FECHA", dato);
                startActivity(i);
            }
        });

    }
}
