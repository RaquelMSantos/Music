package br.com.rmso.playmusic.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.rmso.playmusic.models.Track;

/**
 * Created by Raquel on 07/10/2018.
 */

public class TrackJsonUtils {

    public static ArrayList getTrackStringFromJson(Context context, String trackJsonString) throws JSONException {

        final String results = "results";
        final String conresult = "sucess";

        ArrayList<Track> tracks = new ArrayList();
        JSONObject trackJson = new JSONObject(trackJsonString);

        if (trackJson.has(conresult)) {
            String errorCode = trackJson.getString(conresult);

            if (errorCode.equals("false")) {
                return null;
            }
        }

        JSONArray trackArray = trackJson.getJSONArray(results);

        for (int i = 0; i < trackArray.length(); i++){
            JSONObject trackJsonObject = trackArray.getJSONObject(i);

            Track track = new Track();
            track.setId(trackJsonObject.getInt("id"));
            track.setReadable(trackJsonObject.getBoolean("readable"));
            track.setTitle(trackJsonObject.getString("title"));
            track.setTitle_short(trackJsonObject.getString("title_short"));
            track.setTitle_version(trackJsonObject.getString("title_version"));
            track.setLink(trackJsonObject.getString("link"));
            track.setDuration(trackJsonObject.getInt("duration"));
            track.setRank(trackJsonObject.getInt("rank"));
            track.setExplicit_lyrics(trackJsonObject.getBoolean("explicit_lyrics"));
            track.setPreview(trackJsonObject.getString("preview"));
        }

        return tracks;
    }


}
