package com.jiyeon.project.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.jiyeon.project.domain.Movie;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import java.io.IOException;

public class ElasticRestService {
    
    RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200)).build();

    ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

    ElasticsearchClient client = new ElasticsearchClient(transport);

    public void search() throws IOException {
        SearchResponse<Movie> search = client.search(s -> s
                .index("")
                .query(q -> q
                        .term(t -> t
                                .field("name")
                                .value(v -> v.stringValue(""))
                        )),
                Movie.class);

        for(Hit<Movie> hit : search.hits().hits()){
//            process(hit.source());
        }

    }
}
