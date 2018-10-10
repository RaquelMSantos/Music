package br.com.rmso.playmusic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.IDENTITY;

public class GenreClient {

    private static Retrofit retrofit;

    public GenreClient(){
        if (retrofit == null){
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(IDENTITY)
                    .create();

            this.retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.deezer.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public GenreService getGenreService(){
        return this.retrofit.create(GenreService.class);
    }
}
