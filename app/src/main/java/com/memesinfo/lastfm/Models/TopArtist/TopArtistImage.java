package com.memesinfo.lastfm.Models.TopArtist;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class TopArtistImage implements Serializable{
    @SerializedName("#text")
    public String text;
    public String size;
}
