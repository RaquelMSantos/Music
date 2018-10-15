package br.com.rmso.playmusic.util;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.playmusic.service.model.Artist;
import br.com.rmso.playmusic.service.model.Genre;
import br.com.rmso.playmusic.service.model.Track;

public class Utility {
    public static List<Genre> mCurrentGenreList = new ArrayList<>();
    public static List<Artist> mCurrentArtistList = new ArrayList<>();
    public static List<Track> mCurrentTrackList = new ArrayList<>();
    public static Genre mCurrentGenre = new Genre();
}
