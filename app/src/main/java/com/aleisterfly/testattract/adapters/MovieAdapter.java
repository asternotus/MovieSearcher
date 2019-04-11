package com.aleisterfly.testattract.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleisterfly.testattract.R;
import com.aleisterfly.testattract.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Movie> movies;
    private List<Movie> moviesFiltered = new ArrayList<Movie>();

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
        this.layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        }

        Movie movie = getMovie(position);

        if (movie.getImageBitmap() == null) {
            ((ImageView) view.findViewById(R.id.iv_cover)).setImageResource(android.R.drawable.btn_plus);
        } else {
            ((ImageView) view.findViewById(R.id.iv_cover)).setImageBitmap(movie.getImageBitmap());
        }
        ((TextView) view.findViewById(R.id.tv_name)).setText(movie.getName());
        ((TextView) view.findViewById(R.id.tv_time)).setText(movie.getFormattedTime());

        return view;
    }

    private Movie getMovie(int position) {
        return ((Movie) getItem(position));
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
