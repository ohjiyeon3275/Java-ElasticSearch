package com.jiyeon.project.controller;

import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/save")
    public void save(Movie movie){
        movieService.save(movie);
    }

    @GetMapping("/{title}")
    public Movie findById(@PathVariable String title){
        return movieService.findByTitle(title);
    }
}
