package com.jiyeon.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyeon.project.domain.Actor;
import com.jiyeon.project.domain.Movie;
import com.jiyeon.project.dto.SearchRequestDto;
import com.jiyeon.project.repository.MovieRepository;
import com.jiyeon.project.util.SearchUtil;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper mapper = new ObjectMapper();


    public Movie findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public void save(Movie movie){
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovieByTitle(SearchRequestDto searchRequestDto, Integer year) throws Exception {

        SearchRequest searchRequest = SearchUtil.buildMovieSearchRequest("movies", searchRequestDto, year);


        if(searchRequest == null){
            throw new Exception("searchRequest not exists");
        }

        try{
            SearchResponse searchResponse = restHighLevelClient.search(
                    searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<Movie> movies = new ArrayList<>(searchHits.length);

            for( SearchHit hit : searchHits ){
                movies.add(
                        mapper.readValue(hit.getSourceAsString(), Movie.class)
                );
            }

            return movies;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
