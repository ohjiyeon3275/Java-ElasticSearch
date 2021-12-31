package com.jiyeon.project.service;

import com.jiyeon.project.util.IndexUtil;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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
import java.util.List;

@Service
@AllArgsConstructor
public class IndexService {

    private static List<String> INDICES_TO_CREATE = List.of("actor-mapping");

    private static final Logger LOG = LoggerFactory.getLogger(IndexUtil.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void  tryToCreateIndices() {
        /**
         * isDeleted 로 flag 를 설정해주는 이유는
         * @PostConstruct 작동으로 어플리케이션을 실행시킬때마다
         * indices가 리셋되지 않도록 함이다.
         */
        recreateIndices(false);
    }


    public void recreateIndices(Boolean isDeleted) {

        String settings = IndexUtil.loadAsString("static/es-mapping.json");

        for( String index : INDICES_TO_CREATE ){

            try {
                //to get checking indices
                Boolean isIndex = restHighLevelClient
                        .indices()
                        .exists(new GetIndexRequest(index), RequestOptions.DEFAULT);


                if (isIndex) {
                    if(!isDeleted) {
                        //if index exists and isDelete flag is false -> skip the creating process.
                        continue;
                    }

                    // to make sure index is empty (?)
                    restHighLevelClient.indices().delete(
                            new DeleteIndexRequest(index), RequestOptions.DEFAULT);

                }

                //불러온 파일로 새로운 인덱스를 생성 -- with settings, mappings.
                CreateIndexRequest createIndexRequest = new CreateIndexRequest("actor");
                createIndexRequest.settings(settings, XContentType.JSON);

                String mappings = loadMappings(index);
                System.out.println(mappings);
                System.out.println(">>>mapping exists");

                if(mappings != null){
                    createIndexRequest.mapping(mappings, XContentType.JSON);
                }

                restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);


            }catch(Exception e){
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String loadMappings(String index) {
        //mapping file 불러옴 --> 따로 함수 생성

        String mappings = IndexUtil.loadAsString("static/mappings/" + index + ".json");
        if (mappings == null) {
            LOG.error("fail to create index");
            return null;
        }
        return mappings;
    }


}
