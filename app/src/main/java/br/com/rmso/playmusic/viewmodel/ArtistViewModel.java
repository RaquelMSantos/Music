package br.com.rmso.playmusic.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.rmso.playmusic.service.model.Artist;
import br.com.rmso.playmusic.service.repository.GenreClient;
import br.com.rmso.playmusic.util.Utility;

/**
 * Created by Raquel on 14/10/2018.
 */

public class ArtistViewModel extends AndroidViewModel {
    private final LiveData<List<Artist>> artists;

    public ArtistViewModel(@NonNull Application application) {
        super(application);
        artists = GenreClient.getInstance().getArtistList(Utility.mCurrentGenre.getId());
    }

    public LiveData<List<Artist>> getArtists() {
        return artists;
    }
}
