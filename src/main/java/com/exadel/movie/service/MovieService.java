package com.exadel.movie.service;

import com.exadel.movie.model.Movie;
import com.exadel.movie.model.ResponseModel;
import org.springframework.stereotype.Component;


public interface MovieService {
    ResponseModel addMovie(Movie movie);

    ResponseModel fetchAllMovies();

    ResponseModel fetchMovieDetails(String movieId);

    ResponseModel updateMovie(Movie movie);

    ResponseModel deleteMovie(String movieId);

}
