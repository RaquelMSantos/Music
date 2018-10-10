package br.com.rmso.playmusic.utils;

import br.com.rmso.playmusic.models.Artist;
import br.com.rmso.playmusic.models.Genre;
import br.com.rmso.playmusic.models.Track;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GenreService {

    @GET("genre/")
    Call<Genre> getGenre();

    @GET("genre/{id}/artists")
    Call<Artist> getArtist(@Path("id") int id);

    @GET("artist/{id}")
    Call<Artist> getDetailArtist(@Path("id") int id);

    @GET("artist/{id}/top?limit=50")
    Call<Track> getTrack(@Path("id") int track);
}
