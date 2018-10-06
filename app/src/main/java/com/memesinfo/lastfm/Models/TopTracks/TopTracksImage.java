package com.memesinfo.lastfm.Models.TopTracks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopTracksImage implements Serializable {
    @SerializedName("#text")
    public String text;
    public String size;
}
