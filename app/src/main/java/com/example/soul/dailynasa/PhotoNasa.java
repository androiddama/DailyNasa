package com.example.soul.dailynasa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soul.dailynasa.Network.GetPicApi;
import com.example.soul.dailynasa.Network.NasaData;
import com.example.soul.dailynasa.background.BackgroundService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.AsyncTask;

import java.io.IOException;

public class PhotoNasa extends AppCompatActivity {

    private static String dia = "2018-02-02";
    private static final String key = "AG0bdbRJFcygFGWDfL6BK6Ju3PzNV8Z5ms8kzGJf";
    private ImageView im;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_nasa);


        //sacar la fecha seleccionada
        text = (TextView) findViewById(R.id.title);

        Intent i2 = getIntent();
        Bundle extras = i2.getExtras();

        //preguntar si el extra viene vacío => buena practica
        if(extras != null){
            String fecha = extras.getString("FECHA");
            text.setText(fecha);
            dia = fecha;

        }
        //final de sacar la fecha seleccionada

        im = (ImageView) findViewById(R.id.nasa_image);

        new Peticion().execute();

    }

    //TODO: https://www.youtube.com/watch?v=s99e62gnva8 mirar link. Hi ha Asynctask + Retrofit

    public static class Peticion extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GetPicApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GetPicApi client = retrofit.create(GetPicApi.class);
            Call<NasaData> call = client.crida(key, dia);

            try {
                NasaData s = call.execute().body();
                Log.d("PhotoNasa.Activity", s.getUrl());
                return s.getUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String message){
            Picasso.with(PhotoNasa.class).load(message).error(R.drawable.nasa).into(im);
        }
    }

}
