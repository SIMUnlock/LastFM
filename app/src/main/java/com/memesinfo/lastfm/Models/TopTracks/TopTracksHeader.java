package com.memesinfo.lastfm.Models.TopTracks;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class TopTracksHeader {
    @SerializedName("track")
    public ArrayList<TopTracks> track;

    @SerializedName("@attr")
    @Nullable
    public TopTracksAttrib attr;
}
