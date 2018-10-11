package br.com.rmso.playmusic.views.activities;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

public class DetailArtistActivity extends AppCompatActivity implements ExoPlayer.EventListener{

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
    @BindView(R.id.btn_play)
    FloatingActionButton mPlayFloatingButton;
    //SimpleExoPlayer
    @BindView(R.id.exo_prev)
    ImageButton mPrevButton;
    @BindView(R.id.exo_play)
    ImageButton mPlayButton;
    @BindView(R.id.exo_pause)
    ImageButton mPauseButton;
    @BindView(R.id.exo_next)
    ImageButton mNextButton;
    @BindView(R.id.exo_position)
    TextView mExoPositionTextView;
    @BindView(R.id.exo_progress)
    SeekBar mExoProgress;
    @BindView(R.id.exo_duration)
    TextView mExoDurationTextView;

    private Artist artist;
    private MusicAdapter mMusicAdapter;
    private ArrayList<Track> mTrackList;
    private static final String TAG = DetailArtistActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);
        ButterKnife.bind(this);

        mTrackList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMusicRecyclerView.setLayoutManager(layoutManager);
        mMusicRecyclerView.setHasFixedSize(true);
        mMusicAdapter = new MusicAdapter(mTrackList);
        mMusicRecyclerView.setAdapter(mMusicAdapter);

        mPlayerView = findViewById(R.id.playerView);

        artist = getIntent().getExtras().getParcelable(Constants.bundleArtist);

        if (artist != null) {
            mArtistNameTextView.setText(artist.getName());
            Picasso.with(this)
                    .load(artist.getPicture())
                    .into(mArtistImageView);
            loadDetailArtistJson();
            loadTrackJson();
        }

        initializeMediaSession();

        mPlayFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializePlayer(Uri.parse(Utility.mCurrentTrackList.get(0).getPreview()));
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "PREV", Toast.LENGTH_SHORT).show();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "PLAY", Toast.LENGTH_SHORT).show();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "PAUSE", Toast.LENGTH_SHORT).show();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "NEXT", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(this, TAG);

        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onStop() {
       super.onStop();
       releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
