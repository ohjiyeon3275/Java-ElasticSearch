package com.jiyeon.project.controller;

import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.dto.SearchRequestDto;
import com.jiyeon.project.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
     *
     *  --> migration done.
     */
    @GetMapping("/{title}")
    public Movie findById(@PathVariable String title){
        return movieService.findByTitle(title);
    }

    @PostMapping("/search/{year}")
    public List<Movie> getAllMovieByTitle(@RequestBody SearchRequestDto searchRequestDto,
                                          @PathVariable Integer year) throws Exception {
        return movieService.getAllMovieByTitle(searchRequestDto, year);
    }
}
