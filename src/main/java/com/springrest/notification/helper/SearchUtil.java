package com.springrest.notification.helper;
import com.springrest.notification.dto.SearchRequestDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {
    public SearchUtil(){}

    public static SearchRequest buildMatchRequest(final String indexName, final SearchRequestDTO dto)
    {
        try{
            int page = dto.getPage();
            int size = dto.getSize();
            int from = page*size;

            SearchSourceBuilder builder = new SearchSourceBuilder().from(from).size(size).postFilter(getQueryBuilder(dto));
            if(dto.getSortBy() != null){
                builder = builder.sort(dto.getSortBy(),dto.getOrder()!=null?dto.getOrder(): SortOrder.ASC);
            }
            final SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildRangeRequest(final String indexName, final String field, SearchRequestDTO dto)
    {
        try{
            int page = dto.getPage();
            int size = dto.getSize();
            int from = page*size;
            final SearchSourceBuilder builder = new SearchSourceBuilder().from(from).size(size).postFilter(getQueryBuilder(field,dto));

            final SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildBoolRequest(final String indexName, final SearchRequestDTO dto)
    {
        try{
            int page = dto.getPage();
            int size = dto.getSize();
            int from = page*size;

            QueryBuilder searchQuery = getQueryBuilder(dto);
            QueryBuilder dateQuery = getQueryBuilder("created_at", dto);

            final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(searchQuery).must(dateQuery);

            SearchSourceBuilder builder = new SearchSourceBuilder().from(from).size(size).postFilter(boolQuery);
            if(dto.getSortBy() != null){
                builder = builder.sort(dto.getSortBy(),dto.getOrder()!=null?dto.getOrder(): SortOrder.ASC);
            }
            final SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            return request;
        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(final SearchRequestDTO dto)
    {
        if(dto == null)
            return null;

        final List<String> fields = dto.getFields();

        if(CollectionUtils.isEmpty(fields))
            return null;

        if(fields.size() > 1) {
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);
            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field -> QueryBuilders.matchQuery(field,dto.getSearchTerm()).operator(Operator.AND))
                .orElse(null);

    }

    public static QueryBuilder getQueryBuilder(final String field,SearchRequestDTO dto)
    {
        return QueryBuilders.rangeQuery(field).gte(dto.getStartDate()).lte(dto.getEndDate());
    }
}
