package com.exadel.movie.controller;

import com.exadel.movie.model.Movie;
import com.exadel.movie.model.ResponseModel;
import com.exadel.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {


    @Autowired
     MovieService movieService;

    @PostMapping("/movie")
    public ResponseModel addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
    }

    @GetMapping("/movies/all")
    public ResponseModel fetchAllMovies() {

        return movieService.fetchAllMovies();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseModel fetchMovieDetails(@PathVariable String movieId){

        return movieService.fetchMovieDetails(movieId);
    }

    @PutMapping("/movie/{movieId}")
    public ResponseModel updateMovie(@RequestBody Movie movie, @PathVariable String movieId){
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("movie/{movieId}")
    public ResponseModel deleteMovie(@PathVariable String movieId){
        return movieService.deleteMovie(movieId);
    }



}
