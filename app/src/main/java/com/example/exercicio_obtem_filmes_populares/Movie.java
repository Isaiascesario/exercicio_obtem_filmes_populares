package com.example.exercicio_obtem_filmes_populares;

public class Movie {
    public final String title;
    public final String overView;
    public final String posterPath;
    public final String imgURL;

    public Movie(
        String title,
        String overView,
        String posterPath,
        String imgURL
    ){
        this.title = title;
        this.overView = overView;
        this.posterPath = posterPath;
        this.imgURL = imgURL;
    }

}
