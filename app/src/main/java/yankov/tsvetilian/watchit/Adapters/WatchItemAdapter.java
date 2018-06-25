package yankov.tsvetilian.watchit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.R;

public class WatchItemAdapter extends RecyclerView.Adapter<WatchItemAdapter.WatchItemViewHolder> {

    private final Context context;
    private List<WatchModel> watchLaterList = new ArrayList<>();

    public WatchItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WatchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
        return new WatchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchItemViewHolder holder, final int i) {
        Picasso.get().load(watchLaterList.get(i).getPoster()).fit().into(holder.moviePoster);
        holder.movieTitle.setText(watchLaterList.get(i).getPresentableName());

        if (watchLaterList.get(i).getSeason() != null) {
            holder.season.setText(watchLaterList.get(i).getSeason());
        } else {
            holder.season.setVisibility(View.GONE);
        }

        holder.movieDescription.setText(watchLaterList.get(i).getDescription());
        holder.movieTime.setText(String.format("Duration: %s", watchLaterList.get(i).getDuration()));
        holder.receivedFrom.setText(String.format("Received from: %s", watchLaterList.get(i).getSourceName()));

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WATCHIT", "PLAYER");
            }
        });
    }

    public void setData(List<WatchModel> data) {
        watchLaterList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return watchLaterList.size();
    }

    public static class WatchItemViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieTitle;
        TextView season;
        TextView movieDescription;
        TextView movieTime;
        TextView receivedFrom;
        MaterialButton playButton;
        CardView cardMain;

        public WatchItemViewHolder(View view) {
            super(view);
            moviePoster = view.findViewById(R.id.movie_poster);
            movieTitle = view.findViewById(R.id.movie_title);
            season = view.findViewById(R.id.tvshow_season);
            movieDescription = view.findViewById(R.id.movie_plot);
            movieTime = view.findViewById(R.id.movie_time);
            receivedFrom = view.findViewById(R.id.movie_received_from);
            playButton = view.findViewById(R.id.movie_play);
            cardMain = view.findViewById(R.id.list_item);
        }
    }
}
