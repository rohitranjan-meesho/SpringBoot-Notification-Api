package com.springrest.notification.services;
import com.springrest.notification.dto.SearchRequestDTO;
import com.springrest.notification.elasticsearch.ESDao;
import com.springrest.notification.entity.SmsES;
import com.springrest.notification.helper.Indices;
import com.springrest.notification.helper.SearchUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);


    private final RestHighLevelClient client;

    @Autowired
    private ESDao esDao;

    @Autowired
    public SearchService(RestHighLevelClient client) {
        this.client = client;
    }

    public Boolean index(final SmsES sms){
        try{
            final String smsAsString = MAPPER.writeValueAsString(sms);

            final IndexRequest request = new IndexRequest(Indices.SMS_INDEX);
            request.id(String.valueOf(sms.getId()));
            request.source(smsAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            System.out.println("Index successfull! ES");
            return response != null && response.status().equals(RestStatus.OK);

        }catch (final Exception e)
        {
            LOG.error(e.getMessage(),e);
            return false;
        }
    }

    public List<SmsES> search(final SearchRequestDTO dto) {
        SearchRequest request;
        if (dto.getStartDate() == "2022-01-01 00:00:00" && dto.getEndDate() == "2023-01-01 00:00:00") {
            LOG.info("Built MatchQuery");
            request = SearchUtil.buildMatchRequest(Indices.SMS_INDEX, dto);
        } else if (dto.getFields() == null){
            LOG.info("Built a range query");
            request = SearchUtil.buildRangeRequest(Indices.SMS_INDEX, "created_at", dto);
        }
        else{
            LOG.info("Built Bool Query");
            request = SearchUtil.buildBoolRequest(Indices.SMS_INDEX,dto);
        }
        return searchInternal(request);
    }

    private List<SmsES> searchInternal(SearchRequest request) {
        if(request == null) {
            LOG.error("Failed to build search Request");
            return Collections.emptyList();
        }
        try{
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<SmsES> smsList = new ArrayList<>(searchHits.length);

            for(SearchHit hit : searchHits)
            {
                smsList.add(MAPPER.readValue(hit.getSourceAsString(), SmsES.class));
            }
            return smsList;
        }catch (Exception e)
        {
            LOG.error(e.getMessage(),e);
            return Collections.emptyList();
        }
    }

    public List<SmsES> findAll() {
        List<SmsES> list=new ArrayList<>();
        Iterable<SmsES> iter=esDao.findAll();
        iter.forEach(list::add);
        return list;

    }
}
