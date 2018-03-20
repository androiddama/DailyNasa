package com.example.soul.dailynasa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoNasa extends AppCompatActivity {

    private String dia = "2018-02-02";
    private String key = "DEMO_KEY";
    private ImageView im;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_nasa);

        //intento de sacar la fehca seleccionada
        text = (TextView) findViewById(R.id.title);

        Intent i2 = getIntent();
        Bundle extras = i2.getExtras();

        //preguntar si el extra viene vacío = buena practica
        if(extras != null){
            String dato = extras.getString("FECHA");
            text.setText(dato);
            dia = dato;

        }

        //final del intento de sacar la fecha seleccionada

        im = (ImageView) findViewById(R.id.nasa_image);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetPicApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetPicApi client = retrofit.create(GetPicApi.class);
        Call<NasaData> call = client.crida(key, dia);

        call.enqueue(new Callback<NasaData>() {
            @Override
            public void onResponse(Call<NasaData> call, Response<NasaData> response) {
                NasaData repos = response.body();
                Picasso.with(PhotoNasa.this).load(repos.getUrl()).error(R.drawable.nasa).into(im);
            }

            @Override
            public void onFailure(Call<NasaData> call, Throwable t) {
                Toast to = Toast.makeText(PhotoNasa.this, "Fail to connect to the server", Toast.LENGTH_SHORT);
                to.show();
            }
        });


    }
}
