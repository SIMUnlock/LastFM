package com.memesinfo.lastfm.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.memesinfo.lastfm.API.ApiConstants;
import com.memesinfo.lastfm.API.LastFMService;
import com.memesinfo.lastfm.Models.TopTracks.TopTracks;
import com.memesinfo.lastfm.Models.TopTracks.TopTracksSuperHeader;
import com.memesinfo.lastfm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopTracksFragment extends Fragment{
    public static TopTracksSuperHeader topTracksSuperHeader;
    public static Retrofit retrofit;
    public static LastFMService service;

    public static TopTracksFragment newInstance ( ) {
        TopTracksFragment fragment = new TopTracksFragment ();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate (R.layout.toptracksadapter, container, false);
    }


    private void loadData(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Call<TopTracksSuperHeader> foo = service.getAllTopTracks(ApiConstants.API_REQUEST_FORMAT, "1", ApiConstants.API_METHOD_TOPTRACKS, ApiConstants.API_KEY);
        Callback<TopTracksSuperHeader> cbHandler = new Callback<TopTracksSuperHeader>() {
            @Override
            public void onResponse(Call<TopTracksSuperHeader> call, Response<TopTracksSuperHeader> response) {

                if (response == null || !response.isSuccessful()) return;

                topTracksSuperHeader = response.body();
                if (topTracksSuperHeader == null) {
                    return;
                }
                ArrayList<TopTracks> tracks = topTracksSuperHeader.tracks.track;
                if (tracks == null) return;

                RecyclerView recyclerView = view.findViewById (R.id.trackList);

                //Cambiar el new LinearLayoutManager por un new GridLayoutManager de 2 columnas
                recyclerView.setLayoutManager (new LinearLayoutManager(getContext ()));
                recyclerView.setAdapter (new TopTracksAdapter (getContext(), tracks));


            }

            @Override
            public void onFailure(Call<TopTracksSuperHeader> call, Throwable t) {
            }
        };

        /***llamada asíncrona para obtener el primer conjunto de resultados**/
        foo.enqueue(cbHandler);
    }
    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        retrofit=  new Retrofit.Builder ()
                .baseUrl (ApiConstants.API_BASE_URL) //la url base de la api rest
                .addConverterFactory (GsonConverterFactory.create ()) //la clase que moldeará el json en POJO's
                .build ();
        service=retrofit.create(LastFMService.class);
        loadData(view,savedInstanceState);


    }

}

class TopTracksAdapter extends RecyclerView.Adapter<TopTracksViewHolder>{
    private Context context;
    private ArrayList<TopTracks> track;

    TopTracksAdapter (Context context, ArrayList<TopTracks> tracks) {
        this.track = tracks;
        this.context = context;
    }

    @NonNull
    @Override
    public TopTracksViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (context).inflate (R.layout.list_top_tracks, viewGroup, false);
        return new TopTracksViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull TopTracksViewHolder topTracksViewHolder, int i) {
        TopTracks tracks = track.get (i);
        topTracksViewHolder.bind (tracks.name, tracks.image.get(3).text);
    }

    @Override
    public int getItemCount () {
        return track.size ();
    }
}

class TopTracksViewHolder extends RecyclerView.ViewHolder {
    private TextView textView1;
    private ImageView imageView;

    TopTracksViewHolder (@NonNull View itemView) {
        super(itemView);

        textView1 = itemView.findViewById (R.id.tvDataT1);
        imageView = itemView.findViewById (R.id.imgTrack);
    }

    void bind (String text1, String text3) {
        textView1.setText(text1);

        Picasso.get().load(text3).fit().centerCrop().into(imageView);

    }
}
