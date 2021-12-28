package com.jiyeon.project.controller;

import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/movie")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    public void save(Movie movie){
        movieService.save(movie);
    }

    public Movie findById(@PathVariable String id){
        return movieService.findById(id);
    }
}
