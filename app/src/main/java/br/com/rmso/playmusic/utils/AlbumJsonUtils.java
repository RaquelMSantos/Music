package br.com.rmso.playmusic.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.rmso.playmusic.models.Album;

/**
 * Created by Raquel on 07/10/2018.
 */

public class AlbumJsonUtils {
    public static ArrayList getTrackStringFromJson(Context context, String albumJsonString) throws JSONException {

        final String results = "results";
        final String conresult = "sucess";

        ArrayList<Album> albums = new ArrayList();
        JSONObject albumJson = new JSONObject(albumJsonString);

        if (albumJson.has(conresult)) {
            String errorCode = albumJson.getString(conresult);

            if (errorCode.equals("false")) {
                return null;
            }
        }

        JSONArray albumArray = albumJson.getJSONArray(results);

        for (int i = 0; i < albumArray.length(); i++){
            JSONObject trackJsonObject = albumArray.getJSONObject(i);

            Album album = new Album();
            album.setId(trackJsonObject.getInt("id"));
            album.setTitle(trackJsonObject.getString("title"));
            album.setCover(trackJsonObject.getString("cover"));
            album.setCover_small(trackJsonObject.getString("cover_small"));
            album.setCover_medium(trackJsonObject.getString("cover_medium"));
            album.setCover_big(trackJsonObject.getString("cover_big"));
            album.setCover_xl(trackJsonObject.getString("cover_xl"));
        }
        return albums;
    }

}
