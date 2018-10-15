package br.com.rmso.playmusic.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.rmso.playmusic.service.model.Artist;
import br.com.rmso.playmusic.service.model.Genre;
import br.com.rmso.playmusic.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.IDENTITY;

public class GenreClient {

    private static Retrofit retrofit;
    private static GenreClient genreClient;

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

    public synchronized static GenreClient getInstance() {
        if (genreClient == null) {
            if (genreClient == null) {
                genreClient = new GenreClient();
            }
        }
        return genreClient;
    }

    public LiveData<List<Genre>> getGenreList() {
        final MutableLiveData<List<Genre>> data = new MutableLiveData<>();

        Call<Genre> call = new GenreClient().getGenreService().getGenre();

        call.enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                Genre genre = response.body();
                Utility.mCurrentGenreList = genre.getGenreList();
                data.setValue( Utility.mCurrentGenreList);
            }

            @Override
            public void onFailure(Call<Genre> call , Throwable t) {
                data.setValue(null);
                Log.e("GenreService","Error:" + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<List<Artist>> getArtistList(int idGenre) {
        final MutableLiveData<List<Artist>> data = new MutableLiveData<>();

        Call<Artist> call = new GenreClient().getGenreService().getArtist(idGenre);

        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                Artist artist = response.body();
                Utility.mCurrentArtistList = artist.getArtistsList();
                data.setValue(Utility.mCurrentArtistList);
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                data.setValue(null);
                Log.e("GenreService","Error:" + t.getMessage());
            }
        });
        return data;
    }

//
//    Call<Artist> getDetailArtist(@Path("id") int id);
//
//    Call<Track> getTrack(@Path("id") int track);

    public GenreService getGenreService(){
        return this.retrofit.create(GenreService.class);
    }
}
