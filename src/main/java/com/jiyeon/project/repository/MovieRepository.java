package com.jiyeon.project.repository;

import com.jiyeon.project.domain.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieRepository extends ElasticsearchRepository<Movie, String> {

    Movie findByTitle(String title);
}
