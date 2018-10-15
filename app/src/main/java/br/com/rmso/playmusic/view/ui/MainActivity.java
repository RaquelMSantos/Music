package br.com.rmso.playmusic.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import br.com.rmso.playmusic.view.callback.AdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.service.model.Genre;
import br.com.rmso.playmusic.util.Constants;
import br.com.rmso.playmusic.viewmodel.GenreViewModel;
import br.com.rmso.playmusic.view.adapter.GenreAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterOnclick {

    @BindView(R.id.rv_genre)
    RecyclerView mGenreRecyclerView;

    private GenreAdapter mGenreAdapter;
    private ArrayList<Genre> mGenteList;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGenteList = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, numberOfColumns());
        mGenreRecyclerView.setLayoutManager(mLayoutManager);
        mGenreRecyclerView.setHasFixedSize(true);
        mGenreAdapter = new GenreAdapter(this, mGenteList, this);
        mGenreRecyclerView.setAdapter(mGenreAdapter);

        GenreViewModel genreViewModel = ViewModelProviders.of(this).get(GenreViewModel.class);
        genreViewModel.getGenres().observe(this, genres -> {
            if (genres != null) {
                mGenreAdapter.setGenre(genres);
                mGenreAdapter.notifyDataSetChanged();
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
    public void onClick(int position) {
        Intent intent = new Intent(MainActivity.this, ArtistsActivity.class);
        intent.putExtra(Constants.bundleGenre, position);
        startActivity(intent);
    }
}
