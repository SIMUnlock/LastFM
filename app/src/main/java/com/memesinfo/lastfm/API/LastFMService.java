package com.memesinfo.lastfm.API;

import com.memesinfo.lastfm.Models.TopArtist.TopArtistSuperHeader;
import com.memesinfo.lastfm.Models.TopTracks.TopTracksSuperHeader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMService {

    @GET ("2.0/?")
    Call<TopArtistSuperHeader> getAllTopArtist (@Query("format") String format, @Query ("page") String page, @Query ("method") String method, @Query ("api_key") String key);

    @GET ("2.0/?")
    Call<TopTracksSuperHeader> getAllTopTracks (@Query("format") String format, @Query ("page") String page, @Query ("method") String method, @Query ("api_key") String key);

    // @GET ("2.0/{id}")
    //Call<TopArtist> getPlanet (@Path ("id") int id);
}
