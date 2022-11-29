package com.etraveli.refactoring.model;

import com.etraveli.refactoring.util.MovieCode;

public class Movie {
    private String title;
    private MovieCode code;

    public Movie(String title, MovieCode code) {

        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieCode getCode() {
        return code;
    }

    public void setCode(MovieCode code) {
        this.code = code;
    }
}
