package br.com.rmso.playmusic.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;

import br.com.rmso.playmusic.AdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.models.Genre;
import br.com.rmso.playmusic.utils.Constants;
import br.com.rmso.playmusic.utils.GenreClient;
import br.com.rmso.playmusic.utils.Utility;
import br.com.rmso.playmusic.views.adapters.GenreAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        loadGenreJson();
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

    private void loadGenreJson(){
        Call<Genre> call = new GenreClient().getGenreService().getGenre();

        call.enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                Genre genre = response.body();
                Utility.mCurrentGenreList = genre.getGenreList();
                mGenreAdapter.setGenre(Utility.mCurrentGenreList);
                mGenreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {
                Log.e("GenreService","Error:" + t.getMessage());
            }
        });
    }
}
