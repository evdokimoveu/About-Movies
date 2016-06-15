package com.evdokimoveu.aboutmovie.entity;


public class FilmPoster {
    private long id;
    private String posterPath;

    public FilmPoster(long id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
