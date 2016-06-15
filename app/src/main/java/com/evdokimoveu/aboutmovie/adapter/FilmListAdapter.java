package com.evdokimoveu.aboutmovie.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.evdokimoveu.aboutmovie.R;
import com.evdokimoveu.aboutmovie.TheMoveDBConfig;
import com.evdokimoveu.aboutmovie.entity.FilmPoster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmListAdapter extends BaseAdapter{

    private ArrayList<FilmPoster>filmPosters;
    private Context context;

    public FilmListAdapter(ArrayList<FilmPoster> filmPosters, Context context) {
        this.filmPosters = filmPosters;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filmPosters.size();
    }

    @Override
    public Object getItem(int position) {
        return filmPosters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filmPosters.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.film_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_item);
            FilmPoster film = (FilmPoster)this.getItem(position);
            String imagePath = film.getPosterPath();
            Picasso.with(context).load(TheMoveDBConfig.IMG_URL + context.getResources().getString(R.string.img_size) + imagePath).into(imageView);
            imageView.setId(position);
        }
        else{
            view = convertView;
        }

        return view;
    }
}
