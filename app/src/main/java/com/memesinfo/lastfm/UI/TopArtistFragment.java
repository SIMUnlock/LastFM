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
import com.memesinfo.lastfm.Models.TopArtist.TopArtist;
import com.memesinfo.lastfm.Models.TopArtist.TopArtistSuperHeader;
import com.memesinfo.lastfm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopArtistFragment extends Fragment {
    public static TopArtistSuperHeader artistSuperHeader;
    public static Retrofit retrofit;
    public static LastFMService service;

    public static TopArtistFragment newInstance() {
        TopArtistFragment fragment = new TopArtistFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.topartistadapter, container, false);
    }


    private void loadData(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Call<TopArtistSuperHeader> foo = service.getAllTopArtist(ApiConstants.API_REQUEST_FORMAT, "1", ApiConstants.API_METHOD_TOPARTIST, ApiConstants.API_KEY);
        Callback<TopArtistSuperHeader> cbHandler = new Callback<TopArtistSuperHeader>() {
            @Override
            public void onResponse(Call<TopArtistSuperHeader> call, Response<TopArtistSuperHeader> response) {

                if (response == null || !response.isSuccessful()) return;

                artistSuperHeader = response.body();
                if (artistSuperHeader == null) {
                    return;
                }
                ArrayList<TopArtist> artists = artistSuperHeader.artists.artist;
                if (artists == null) return;

                RecyclerView recyclerView = view.findViewById(R.id.artistList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new TopArtistAdapter(getContext(), artists));


            }

            @Override
            public void onFailure(Call<TopArtistSuperHeader> call, Throwable t) {
            }
        };

        /***llamada asíncrona para obtener el primer conjunto de resultados**/
        foo.enqueue(cbHandler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.API_BASE_URL) //la url base de la api rest
                .addConverterFactory(GsonConverterFactory.create()) //la clase que moldeará el json en POJO's
                .build();
        service = retrofit.create(LastFMService.class);
        loadData(view, savedInstanceState);


    }

}

class TopArtistAdapter extends RecyclerView.Adapter<TopArtistViewHolder> {
    private Context context;
    private ArrayList<TopArtist> artist;

    TopArtistAdapter(Context context, ArrayList<TopArtist> artist) {
        this.artist = artist;
        this.context = context;
    }

    @NonNull
    @Override
    public TopArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_top_artist, viewGroup, false);
        return new TopArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopArtistViewHolder topArtistViewHolder, int i) {
        TopArtist artists = artist.get(i);
        topArtistViewHolder.bind(artists.name, artists.playcount, artists.image.get(4).text);
    }

    @Override
    public int getItemCount() {
        return artist.size();
    }
}

class TopArtistViewHolder extends RecyclerView.ViewHolder {
    private TextView textView1, textView2;
    private ImageView imageView;

    TopArtistViewHolder(@NonNull View itemView) {
        super(itemView);

        textView1 = itemView.findViewById(R.id.tvData1);
        textView2 = itemView.findViewById(R.id.tvData2);
        imageView = itemView.findViewById(R.id.imgArtist);
    }

    void bind(String text1, String text2, String text3) {
        textView1.setText(text1);
        textView2.setText(text2);

        Picasso.get().load(text3).fit().centerCrop().into(imageView);

    }
}
