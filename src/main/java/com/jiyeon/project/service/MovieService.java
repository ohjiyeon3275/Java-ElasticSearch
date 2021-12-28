package com.jiyeon.project.service;

import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public void save(Movie movie){
        movieRepository.save(movie);
    }

}
