package com.exadel.movie.service;

import com.exadel.movie.model.Movie;
import com.exadel.movie.model.ResponseModel;
import com.exadel.movie.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Override
    public ResponseModel addMovie(Movie movie) {
        ResponseModel responseModel = new ResponseModel();
        List<Movie> movieData = readJsonData();
        Movie moviePresent = null;

        if (movieData != null) {
            moviePresent = movieData.parallelStream().filter(mov -> mov.getId().equals(movie.getId())).findAny().orElse(null);

        } else {
            movieData = new ArrayList<>();
        }
        if (moviePresent == null) {
            movieData.add(movie);
            boolean status = writeJsonData(movieData);
            if (status) {
                responseModel.setStatus(Constants.SUCCESS);
                responseModel.setData(Constants.MOVIE_ADDED);
            }
        } else {
            responseModel.setData((Constants.MOVIE_ALREADY_PRESENT));
        }
        return responseModel;


    }

    @Override
    public ResponseModel fetchAllMovies() {
        ResponseModel responseModel = new ResponseModel();
        List<Movie> movieData = readJsonData();
        responseModel.setStatus(Constants.SUCCESS);
        responseModel.setData(movieData);
        return responseModel;
    }

    @Override
    public ResponseModel fetchMovieDetails(String movieId) {
        ResponseModel responseModel = new ResponseModel();
        List<Movie> movieData = readJsonData();
        Movie movie = movieData.parallelStream().filter(mov -> mov.getId().equals(movieId)).findAny().orElse(null);

        if (movie != null) {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(movie);
        } else {
            responseModel.setStatus(Constants.FAIL);
            responseModel.setData(Constants.MOVIE_NOT_FOUND);
        }
        return responseModel;
    }

    @Override
    public ResponseModel updateMovie(Movie movie) {
        ResponseModel responseModel = new ResponseModel();
        List<Movie> movieData = readJsonData();
        movieData.removeIf(mov -> mov.getId().equals(movie.getId()));
        movieData.add(movie);

        boolean status = writeJsonData(movieData);

        if (status) {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(Constants.MOVIE_UPDATED);
        }
        return responseModel;
    }

    @Override
    public ResponseModel deleteMovie(String movieId) {
        ResponseModel responseModel = new ResponseModel();
        List<Movie> movieData = readJsonData();
        movieData.removeIf(mov -> mov.getId().equals(movieId));


        boolean status = writeJsonData(movieData);

        if (status) {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(Constants.MOVIE_DELETED);
        }
        return responseModel;
    }


    public List<Movie> readJsonData() {
        List<Movie> movies = new ArrayList<>();
        try {
            String json = Files.readString(Path.of("movies.json"));
            movies = new Gson().fromJson(json, new TypeToken<List<Movie>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;

    }


    public boolean writeJsonData(List<Movie> movieData) {
        boolean status = false;
        try (FileWriter file = new FileWriter("movies.json")) {
            file.write(new Gson().toJson(movieData));
            file.flush();
            status = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;


    }

}

