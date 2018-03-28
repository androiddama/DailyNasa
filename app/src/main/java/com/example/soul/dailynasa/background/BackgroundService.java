package com.example.soul.dailynasa.background;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.soul.dailynasa.Network.GetPicApi;
import com.example.soul.dailynasa.Network.NasaData;
import com.example.soul.dailynasa.PhotoNasa;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by canom on 28/3/2018.
 */

public class BackgroundService extends IntentService {

    private static String key;
    private String day;

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Background", "Entrant al Background");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            key = (String)extras.get("key");
            day = (String)extras.get("day");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetPicApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetPicApi client = retrofit.create(GetPicApi.class);
        Call<NasaData> call = client.crida(key, day);

        //execute network request
        try{
            Response<NasaData> result = call.execute();
            Log.d("Retrofit", "Success");
        }catch (IOException e) {
            Log.d("Retrofit", "Failure");
            e.printStackTrace();
        }
    }
}
