package br.com.rmso.playmusic.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.rmso.playmusic.AdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.models.Artist;
import br.com.rmso.playmusic.models.Track;
import br.com.rmso.playmusic.utils.Constants;
import br.com.rmso.playmusic.utils.GenreClient;
import br.com.rmso.playmusic.utils.Utility;
import br.com.rmso.playmusic.views.adapters.MusicAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailArtistActivity extends AppCompatActivity implements AdapterOnclick{

    @BindView(R.id.img_profile_artist)
    ImageView mArtistImageView;
    @BindView(R.id.tv_artist_name)
    TextView mArtistNameTextView;
    @BindView(R.id.tv_fan)
    TextView mFanTextView;
    @BindView(R.id.tv_album)
    TextView mAlbumTextView;
    @BindView(R.id.rv_musics)
    RecyclerView mMusicRecyclerView;

    private Artist artist;
    private MusicAdapter mMusicAdapter;
    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);
        ButterKnife.bind(this);

        mTrackList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMusicRecyclerView.setLayoutManager(layoutManager);
        mMusicRecyclerView.setHasFixedSize(true);
        mMusicAdapter = new MusicAdapter(mTrackList, this);
        mMusicRecyclerView.setAdapter(mMusicAdapter);

        artist = getIntent().getExtras().getParcelable(Constants.bundleArtist);

        if (artist != null) {
            mArtistNameTextView.setText(artist.getName());
            Picasso.with(this)
                    .load(artist.getPicture())
                    .into(mArtistImageView);
            loadDetailArtistJson();
            loadTrackJson();
        }
    }

    private void loadDetailArtistJson(){
        Call<Artist> call = new GenreClient().getGenreService().getDetailArtist(artist.getId());

        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                Artist artistResponse = response.body();
                artist.setNb_album(artistResponse.getNb_album());
                artist.setNb_fan(artistResponse.getNb_fan());
                mFanTextView.setText(artist.getNb_fan() + " " + getString(R.string.title_fan));
                mAlbumTextView.setText(artistResponse.getNb_album() + " " +  getString(R.string.title_album));
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                Log.e("GenreService","Error:" + t.getMessage());
            }
        });
    }

    private void loadTrackJson() {
        Call<Track> call = new GenreClient().getGenreService().getTrack(artist.getId());

        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Track track = response.body();
                Utility.mCurrentTrackList = track.getTrackList();
                mMusicAdapter.setTrack(Utility.mCurrentTrackList);
                mMusicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("GenreService","Error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(int position) {

    }

}
