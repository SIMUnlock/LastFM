package com.memesinfo.lastfm.Models.TopArtist;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TopArtistHeader {
    @SerializedName("artist")
    public ArrayList<TopArtist> artist;

    @SerializedName("@attr")
    @Nullable public TopArtistAttrib attr;

}
