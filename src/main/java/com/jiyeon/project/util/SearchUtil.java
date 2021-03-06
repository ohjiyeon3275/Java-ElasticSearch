package com.jiyeon.project.util;

import com.jiyeon.project.dto.SearchRequestDto;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class SearchUtil {


    /**
     * getQueryBuilders
     */
    private static QueryBuilder getQueryBuilder(String field, Date date){

        return QueryBuilders.rangeQuery(field).gte(date);

    }

    private static QueryBuilder getQueryBuilder(String field, Integer year){
        return QueryBuilders.rangeQuery(field).gte(year);

    }

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


    /**
     * buildSearchRequest
     */
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
            return null;
        }

    }

    public static SearchRequest buildSearchRequest(String index,
                                                   String field,
                                                   Date date) {

        try{
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(field, date));


            SearchRequest request = new SearchRequest(index);
            request.source(sourceBuilder);

            return request;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    //movies
    public static SearchRequest buildSearchRequest(String index,
                                                   SearchRequestDto searchRequestDto,
                                                   Integer year) {
        try {

            QueryBuilder searchQuery = getQueryBuilder(searchRequestDto);
            QueryBuilder yearQuery  = getQueryBuilder("year", year);

            BoolQueryBuilder boolQuery =
                    QueryBuilders.boolQuery().must(searchQuery).must(yearQuery);

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .postFilter(boolQuery);

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
            return null;
        }

    }

    public static SearchRequest buildSearchRequestPage(String index,
                                                   SearchRequestDto searchRequestDto) {

        try {

            int page = searchRequestDto.getPage();
            int size = searchRequestDto.getSize();
            int from = page <=0 ? 0 : page * size;

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(searchRequestDto));

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
            return null;
        }

    }



}
