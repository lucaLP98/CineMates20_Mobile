package it.ingsw.cinemates20_mobile.widgets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{
    private final Context context;
    private final List<Movie> movies;
    private final FragmentManager fragmentManager;

    public MovieAdapter(Context context, FragmentManager fragmentManager, List<Movie> movie){
        this.context = context;
        this.movies = movie;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        if(movies.get(position) != null){
            holder.movieNameTextView.setText(movies.get(position).getTitle());

            if(movies.get(position).getPosterUri() != null){
                Glide
                        .with(context)
                        .load(movies.get(position).getPosterUri())
                        .centerCrop()
                        .into(holder.moviePosterImageView);
            }else{
                holder.moviePosterImageView.setImageResource(R.drawable.ic_baseline_local_movies_24);
            }

            holder.rowLayout.setOnClickListener( v -> fragmentManager.beginTransaction().addToBackStack("MOVIE_CARD").replace(R.id.home_page_container, new MovieInformationFragment(movies.get(position).getMovieID())).commit());
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    protected static class MovieHolder extends RecyclerView.ViewHolder{
        private final TextView movieNameTextView;
        private final ImageView moviePosterImageView;
        private final FrameLayout rowLayout;

        protected MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieNameTextView = itemView.findViewById(R.id.movieNameSearchTextView);
            moviePosterImageView = itemView.findViewById(R.id.moviePosterSearchImageView);
            rowLayout = itemView.findViewById(R.id.movie_row_layout);
        }
    }
}
