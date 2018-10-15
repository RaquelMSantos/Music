package br.com.rmso.playmusic.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.rmso.playmusic.view.callback.ArtistAdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.service.model.Artist;
import br.com.rmso.playmusic.util.Constants;
import br.com.rmso.playmusic.util.Utility;
import br.com.rmso.playmusic.view.adapter.ArtistAdapter;
import br.com.rmso.playmusic.viewmodel.ArtistViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistsActivity extends AppCompatActivity implements ArtistAdapterOnclick {

    private int genrePosition;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mColllapsingToolbarLayout;
    @BindView(R.id.rv_artists)
    RecyclerView mArtistsRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_genre)
    ImageView mGenreImageView;

    private ArtistAdapter mArtistAdapter;
    private ArrayList<Artist> mArtistList;
    private GridLayoutManager mLayoutManager;
    private Drawer.Result navigationDrawerLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        genrePosition = intent.getIntExtra(Constants.bundleGenre, 0);
        Utility.mCurrentGenre = Utility.mCurrentGenreList.get(genrePosition);

        mColllapsingToolbarLayout.setTitle(Utility.mCurrentGenreList.get(genrePosition).getName());
        mToolbar.setTitle(Utility.mCurrentGenreList.get(genrePosition).getName());
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mArtistList = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, numberOfColumns());
        mArtistsRecyclerView.setLayoutManager(mLayoutManager);
        mArtistsRecyclerView.setHasFixedSize(true);
        mArtistsRecyclerView.setNestedScrollingEnabled(false);

        mArtistAdapter = new ArtistAdapter(this, mArtistList, this);
        mArtistsRecyclerView.setAdapter(mArtistAdapter);

        Picasso.with(this)
                .load(Utility.mCurrentGenre.getPicture_xl())
                .into(mGenreImageView);

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(false)
                .withCloseOnClick(true)
                .withActionBarDrawerToggleAnimated(false)
                .withActionBarDrawerToggle(new ActionBarDrawerToggle(this, new DrawerLayout(this), R.string.drawer_open, R.string.drawer_close){
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);
                        navigationDrawerLeft.closeDrawer();
                        finish();
                    }
                })
                .build();

        ArtistViewModel artistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        artistViewModel.getArtists().observe(this, artists -> {
            if (artists != null) {
                mArtistAdapter.setArtist(artists);
                mArtistAdapter.notifyDataSetChanged();
            }
        });
    }

    private int numberOfColumns(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public void onClick(int position, Artist artistClicked) {
        Intent intent = new Intent(this, DetailArtistActivity.class);
        intent.putExtra(Constants.bundleArtist, artistClicked);
        startActivity(intent);
    }
}
