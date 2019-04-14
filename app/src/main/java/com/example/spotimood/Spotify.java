package com.example.spotimood;
import android.util.Log;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.tracks.GetSeveralTracksRequest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Spotify {
    GetSeveralTracksRequest getSeveralTracksRequest;
    private static String accessToken;
    private static final String[] ids = new String[]{"01iyCAUm8EvOFqVWYJ3dVX"};  //TODO: Remove
    private static SpotifyApi spotifyApi;

    /*public Spotify(String accessToken) {

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        getSeveralTracksRequest = spotifyApi.getSeveralTracks(ids)
//          .market(CountryCode.SE)
                .build();


        this.accessToken = accessToken;
        getSeveralTracks_Sync();
    }

    public void getSeveralTracks_Sync() {
        try {
            final Track[] tracks = getSeveralTracksRequest.execute();

            Log.i("SpotiMood","Length: " + tracks.length);
            Log.i("SpotiMood",tracks[0].getName());
        } catch (IOException | SpotifyWebApiException e) {
            Log.e("SpotiMood","Error: " + e.getMessage());
        }
    }*/
}