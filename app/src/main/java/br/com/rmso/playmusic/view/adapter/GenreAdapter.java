package br.com.rmso.playmusic.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.playmusic.view.callback.AdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.service.model.Genre;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    private List<Genre> mGenreList;
    private final Context mContext;
    private final AdapterOnclick mGenreOnclick;

    public GenreAdapter (Context context, ArrayList<Genre> genreList, AdapterOnclick genreOnclick) {
        mContext = context;
        mGenreList = genreList;
        mGenreOnclick = genreOnclick;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        final Genre genre = mGenreList.get(position);
        holder.mGenreNameTextView.setText(genre.getName());
        Picasso.with(mContext)
                .load(genre.getPicture_xl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image_error)
                .into(holder.mGenreCoverImageView);

        Typeface custom_font_roboto_regular = Typeface.createFromAsset(mContext.getAssets(),  "Roboto-Regular.ttf");
        holder.mGenreNameTextView.setTypeface(custom_font_roboto_regular);
    }

    @Override
    public int getItemCount() {
       if (mGenreList != null) return mGenreList.size(); else return 0;
    }

    public void setGenre(List<Genre> genreList){
        if (genreList != null){
            this.mGenreList = genreList;
            notifyDataSetChanged();
        }
    }


    public class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mGenreNameTextView;
        public final ImageView mGenreCoverImageView;

        public GenreViewHolder(View itemView) {
            super(itemView);
            mGenreNameTextView = itemView.findViewById(R.id.tv_genre_name);
            mGenreCoverImageView = itemView.findViewById(R.id.img_genre_cover);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            mGenreOnclick.onClick(itemPosition);
        }
    }
}
