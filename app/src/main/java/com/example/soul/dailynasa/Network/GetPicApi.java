package com.example.soul.dailynasa.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by soul on 05/03/2018.
 */

public interface GetPicApi {
    String URL = "https://api.nasa.gov/";

    @GET("/planetary/apod")
    Call<NasaData> crida (@Query("api_key") String key,
                          @Query("date") String dia);

}
