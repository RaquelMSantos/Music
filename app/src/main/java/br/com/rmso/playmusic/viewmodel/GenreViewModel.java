package br.com.rmso.playmusic.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.rmso.playmusic.service.model.Genre;
import br.com.rmso.playmusic.service.repository.GenreClient;

/**
 * Created by Raquel on 14/10/2018.
 */

public class GenreViewModel extends AndroidViewModel {
    private final LiveData<List<Genre>> genres;

    public GenreViewModel(@NonNull Application application) {
        super(application);
        genres = GenreClient.getInstance().getGenreList();
    }

    public LiveData<List<Genre>> getGenres() {
        return genres;
    }
}
