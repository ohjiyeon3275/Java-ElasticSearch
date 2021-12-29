package com.jiyeon.project.service;

import com.jiyeon.project.util.IndexUtil;
import lombok.AllArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class IndexService {

    private List<String> INDICES_TO_CREATE = List.of("actor-mapping");

    private static final Logger LOG = LoggerFactory.getLogger(IndexUtil.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @PostConstruct
    public void tryToCreateIndices() throws IOException {
        String settings = IndexUtil.loadAsString("static/es-mapping.json");

        for( String index : INDICES_TO_CREATE ){

            //to get checking indices
            Boolean isIndex = restHighLevelClient
                    .indices()
                    .exists(new GetIndexRequest(index), RequestOptions.DEFAULT);

            if(isIndex){
                continue;
            }


            //mapping file 불러옴
            String mappings = IndexUtil.loadAsString("static/mappings/"+ index + ".json");
            if(settings == null || mappings == null){
                LOG.error("fail to create index");
            }

            //불러온 파일로 새로운 인덱스를 생성 -- with settings, mappings.
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
            createIndexRequest.settings(settings, XContentType.JSON);
            createIndexRequest.mapping(mappings, XContentType.JSON);

            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        }
    }



}
