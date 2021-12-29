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

    /**
     *  title로 찾는것 까지는 완료했으나
     *  localhost:9200/_bulk --data-binary @movies.json (외부 json데이터 마이그레이션)
     *  & 해당 데이터들의 검색기능은 아직 미완
     */
    @GetMapping("/{title}")
    public Movie findById(@PathVariable String title){
        return movieService.findByTitle(title);
    }
}
