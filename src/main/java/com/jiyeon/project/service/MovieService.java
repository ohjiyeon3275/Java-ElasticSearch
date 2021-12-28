package com.jiyeon.project.service;

import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie findById(String id){
        return movieRepository.findById(id).orElse(null);
    }

    public void save(Movie movie){
        movieRepository.save(movie);
    }

}
