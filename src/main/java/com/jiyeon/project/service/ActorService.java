package com.jiyeon.project.service;

import com.jiyeon.project.dto.SearchRequestDto;
import com.jiyeon.project.util.SearchUtil;
import org.elasticsearch.action.get.GetRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyeon.project.domain.Actor;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActorService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger LOG = LoggerFactory.getLogger(ActorService.class);

    private final RestHighLevelClient restHighLevelClient;

    public boolean save(Actor actor){

        try{
            String actorAsString = mapper.writeValueAsString(actor);

            IndexRequest request = new IndexRequest("actor-mapping");
            request.id(actor.getId());
            request.source(actorAsString, XContentType.JSON);

            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);

            return response!= null && response.status().equals(RestStatus.OK);

        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return true;
    }

    public Actor findById(String id){
        try {


            GetResponse documents = restHighLevelClient.get(new GetRequest("actor-mapping", id), RequestOptions.DEFAULT);

            if (documents == null) {
                return null;
            }

            return mapper.readValue(documents.getSourceAsString(), Actor.class);

        }catch (Exception e){
            LOG.error(e.getMessage(), e);
        }
        return null;

    }

    public List<Actor> search(SearchRequestDto searchRequestDto){

        SearchRequest searchRequest = SearchUtil.buildSearchRequest(
                "actor-mapping", searchRequestDto);

        if(searchRequest == null){
            LOG.error("searchRequest 없음");
            return null;
        }

        try{
            SearchResponse searchResponse = restHighLevelClient.search(
                    searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<Actor> actors = new ArrayList<>(searchHits.length);

            for( SearchHit hit : searchHits ){
                actors.add(
                        mapper.readValue(hit.getSourceAsString(), Actor.class)
                );
            }

            return actors;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
