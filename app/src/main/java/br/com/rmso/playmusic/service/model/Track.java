package br.com.rmso.playmusic.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raquel on 06/10/2018.
 */

public class Track {
    @SerializedName("data")
    private List<Track> trackList;
    private int id;
    private boolean readable;
    private String title;
    private String title_short;
    private String title_version;
    private String link;
    private int duration;
    private int rank;
    private boolean explicit_lyrics;
    private String preview;
    private Album album;
    private int imagePlay;
    private boolean isPlaying;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_short() {
        return title_short;
    }

    public void setTitle_short(String title_short) {
        this.title_short = title_short;
    }

    public String getTitle_version() {
        return title_version;
    }

    public void setTitle_version(String title_version) {
        this.title_version = title_version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isExplicit_lyrics() {
        return explicit_lyrics;
    }

    public void setExplicit_lyrics(boolean explicit_lyrics) {
        this.explicit_lyrics = explicit_lyrics;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getImagePlay() {
        return imagePlay;
    }

    public void setImagePlay(int imagePlay) {
        this.imagePlay = imagePlay;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
