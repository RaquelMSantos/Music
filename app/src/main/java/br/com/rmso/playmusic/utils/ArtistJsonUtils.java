package br.com.rmso.playmusic.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.rmso.playmusic.models.Artist;

/**
 * Created by Raquel on 07/10/2018.
 */

public class ArtistJsonUtils {

    public static ArrayList getArtistStringFromJson(Context context, String artistsJsonString) throws JSONException {

        final String results = "results";
        final String conresult = "sucess";

        ArrayList artists = new ArrayList();
        JSONObject artistsJson = new JSONObject(artistsJsonString);

        if (artistsJson.has(conresult)) {
            String errorCode = artistsJson.getString(conresult);

            if (errorCode.equals("false")) {
                return null;
            }
        }

        JSONArray artistsArray = artistsJson.getJSONArray(results);

        for (int i = 0; i < artistsArray.length(); i++){
            JSONObject artistsJsonObject = artistsArray.getJSONObject(i);

            Artist artist = new Artist();
            artist.setId(artistsJsonObject.getInt("id"));
            artist.setName(artistsJsonObject.getString("name"));
            artist.setPicture(artistsJsonObject.getString("picture"));
            artist.setPicture_small(artistsJsonObject.getString("picture_small"));
            artist.setPicture_medium(artistsJsonObject.getString("picture_medium"));
            artist.setPicture_big(artistsJsonObject.getString("picture_big"));
            artist.setPicture_xl(artistsJsonObject.getString("picture_xl"));
            artist.setRadio(artistsJsonObject.getBoolean("radio"));
        }

        return artists;
    }

}
