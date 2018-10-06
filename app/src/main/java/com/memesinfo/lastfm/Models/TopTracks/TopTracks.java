package com.memesinfo.lastfm.Models.TopTracks;

import java.io.Serializable;
import java.util.ArrayList;

public class TopTracks implements Serializable{
    public String name;
    public String duration;
    public String playcount;
    public String listeners;
    public String mbid;
    public String url;
    public TopTracksStreamable streamable;
    public TopTracksArtist artist;
    public ArrayList<TopTracksImage> image;

}
