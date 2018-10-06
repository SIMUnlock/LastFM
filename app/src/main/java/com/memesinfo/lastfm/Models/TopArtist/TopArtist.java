package com.memesinfo.lastfm.Models.TopArtist;

import java.io.Serializable;
import java.util.ArrayList;

public class TopArtist implements Serializable{
    public String name;
    public String playcount;
    public String listeners;
    public String mbid;
    public String url;
    public String streamable;
    public ArrayList<TopArtistImage> image;

}
