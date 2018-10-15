package br.com.rmso.playmusic.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raquel on 06/10/2018.
 */

public class Artist implements Parcelable {
    @SerializedName("data")
    private List<Artist> artistsList;
    private int id;
    private String name;
    private String picture;
    private String picture_small;
    private String picture_medium;
    private String picture_big;
    private String picture_xl;
    private int nb_album;
    private int nb_fan;
    private boolean radio;
    private String tracklist;

    public Artist (){

    }

    public Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        picture = in.readString();
        picture_small = in.readString();
        picture_medium = in.readString();
        picture_big = in.readString();
        picture_xl = in.readString();
        nb_album = in.readInt();
        nb_fan = in.readInt();
        radio = in.readByte() != 0;
        tracklist = in.readString();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public String getPicture_xl() {
        return picture_xl;
    }

    public void setPicture_xl(String picture_xl) {
        this.picture_xl = picture_xl;
    }

    public int getNb_album() {
        return nb_album;
    }

    public void setNb_album(int nb_album) {
        this.nb_album = nb_album;
    }

    public int getNb_fan() {
        return nb_fan;
    }

    public void setNb_fan(int nb_fan) {
        this.nb_fan = nb_fan;
    }

    public boolean isRadio() {
        return radio;
    }

    public void setRadio(boolean radio) {
        this.radio = radio;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public List<Artist> getArtistsList() {
        return artistsList;
    }

    public void setArtistsList(List<Artist> artistsList) {
        this.artistsList = artistsList;
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(picture);
        parcel.writeString(picture_small);
        parcel.writeString(picture_medium);
        parcel.writeString(picture_big);
        parcel.writeString(picture_xl);
        parcel.writeInt(nb_album);
        parcel.writeInt(nb_fan);
        parcel.writeByte((byte) (radio ? 1 : 0));
        parcel.writeString(tracklist);
    }
}
