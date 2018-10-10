package br.com.rmso.playmusic.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.rmso.playmusic.AdapterOnclick;
import br.com.rmso.playmusic.ArtistAdapterOnclick;
import br.com.rmso.playmusic.R;
import br.com.rmso.playmusic.models.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    private List<Artist> mArtistList;
    private final Context mContext;
    private final ArtistAdapterOnclick mClickHandler;

    public ArtistAdapter (Context context, List<Artist> artistList, ArtistAdapterOnclick clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
        mArtistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        final Artist artist = mArtistList.get(position);
        holder.mArtistNameTextView.setText(artist.getName());
        Picasso.with(mContext)
                .load(artist.getPicture_xl())
                .into(holder.mArtistCoverImageView);
    }

    @Override
    public int getItemCount() {
        if (mArtistList != null) return mArtistList.size(); else return 0;
    }

    public void setArtist(List<Artist> artistList){
        if (artistList != null){
            this.mArtistList = artistList;
            notifyDataSetChanged();
        }
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView mArtistCoverImageView;
        public final TextView mArtistNameTextView;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            mArtistCoverImageView = itemView.findViewById(R.id.img_artist_cover);
            mArtistNameTextView = itemView.findViewById(R.id.tv_artist_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            Artist artistClicked = mArtistList.get(itemPosition);
            mClickHandler.onClick(itemPosition, artistClicked);
        }
    }

}
