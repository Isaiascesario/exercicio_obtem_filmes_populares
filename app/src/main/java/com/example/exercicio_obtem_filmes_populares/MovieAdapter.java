package com.example.exercicio_obtem_filmes_populares;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private List<Movie> movies;

    public MovieAdapter (Context context,
                           List<Movie> movies){
        super(context, -1, movies);
        this.context = context;
        this.movies = movies;
    }
    public int getCount() {
        return movies.size();
    }

    private class MovieViewHolder {
        ImageView movieImgView;
        TextView tittle;
        TextView overView;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieViewHolder mh = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.list_item,
                    parent,
                    false
            );
            mh = new MovieViewHolder();

        }
        else
            mh = (MovieViewHolder) convertView.getTag();

        Movie moviesDaVez = movies.get(position);

        mh.tittle.setText( moviesDaVez.title);
        mh.overView.setText( moviesDaVez.overView);

        baixarImagem (moviesDaVez,  mh.movieImgView);

        return convertView;

    }

    private void baixarImagem (Movie moviedaVez,
                               ImageView conditionImageView){
        new Thread (() -> {
            try{
                URL url = new URL (moviedaVez.imgURL);
                HttpURLConnection conn =
                        (HttpURLConnection)
                                url.openConnection();
                InputStream is =
                        conn.getInputStream();
                Bitmap figura =
                        BitmapFactory.decodeStream(is);
                ((Activity)context).runOnUiThread(
                        () -> {
                            conditionImageView.setImageBitmap(
                                    figura
                            );
                        }
                );
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }
}
