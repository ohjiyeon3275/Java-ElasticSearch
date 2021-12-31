package com.jiyeon.project.util;

import com.jiyeon.project.dto.SearchRequestDto;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {

    public static QueryBuilder getQueryBuilder(SearchRequestDto searchRequestDto) {

        if( searchRequestDto == null ){
            return null;
        }

        List<String> fields = searchRequestDto.getFields();
        if(CollectionUtils.isEmpty(fields)){
            return null;
        }

        /**
         * MultiMatchQuery
         */
        if(fields.size() > 1) {

            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchRequestDto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND); // Operator.AND ==> it only allows perfect match

            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field ->
                        QueryBuilders.matchQuery(field, searchRequestDto.getSearchTerm())
                                .operator(Operator.AND))
                .orElse(null);
    }

    public static SearchRequest buildSearchRequest(String index,
                                                   SearchRequestDto searchRequestDto) {

        try {

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(searchRequestDto));


            //add sort
            if(searchRequestDto.getSort() != null){
                sourceBuilder = sourceBuilder.sort(
                        searchRequestDto.getSort(),
                        searchRequestDto.getOrder() != null ? searchRequestDto.getOrder() : SortOrder.ASC
                );
            }


            SearchRequest request = new SearchRequest(index);
            request.source(sourceBuilder);

            return request;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
