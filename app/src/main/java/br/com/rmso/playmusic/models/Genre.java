package br.com.rmso.playmusic.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raquel on 07/10/2018.
 */

public class Genre implements Serializable{
    @SerializedName("data")
    private List<Genre> genreList;
    private int id;
    private String name;
    private String picture;
    private String picture_small;
    private String picture_medium;
    private String picture_big;
    private String picture_xl;

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

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}
