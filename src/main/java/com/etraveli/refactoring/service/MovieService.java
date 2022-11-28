package com.etraveli.refactoring.service;

import com.etraveli.refactoring.Exception.NoMovieFoundException;
import com.etraveli.refactoring.model.Movie;
import com.etraveli.refactoring.util.MovieCode;
import java.util.HashMap;

import static com.etraveli.refactoring.util.MovieCode.*;

public class MovieService {

    private HashMap<String, Movie> movies = new HashMap();

    public MovieService() {
        setMovies();
    }

    public void setMovies() {
        movies.put("F001", saveMovie("You've Got Mail", REGULAR));
        movies.put("F002", saveMovie("Matrix", REGULAR));
        movies.put("F003", saveMovie("Cars", CHILDRENS));
        movies.put("F004", saveMovie("Fast & Furious X", NEW));
    }
    public HashMap<String, Movie> getMovies() {
        return movies;
    }

    public Movie saveMovie(String title, MovieCode code) {

        Movie movie = new Movie(title, code);
        return movie;
    }
    public Movie getMovie(String movieRentalId) {
        Movie movie = movies.get(movieRentalId);
        if (movie == null) {
            throw new NoMovieFoundException(
                    String.format("Could not find movie with rental id of %s", movieRentalId));
        }
        return movie;
    }
}
