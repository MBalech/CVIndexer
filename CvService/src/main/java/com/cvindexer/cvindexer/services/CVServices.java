package com.cvindexer.cvindexer.services;

import com.cvindexer.cvindexer.models.cv;
import com.cvindexer.cvindexer.repository.CVRepository;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class CVServices {
    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    private Gson gson;

    @Autowired
    private CVRepository repository;

    /**
     * Insert un cv dans elasticSearch
     * @param cv
     * @return
     */
    public cv insertCv(cv cv) {
        return repository.save(cv);
    }

    /**
     * Retourne tous les cvs indexés
     * @return
     * @throws IOException
     */
    public List<cv> getAllCV() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).size(5000);
        return executeQuery(searchSourceBuilder);
    }

    /**
     * Fais la recherche dans les cvs d'elasticsearch
     * @param research string contenant les mots de recherches
     * @return
     * @throws IOException
     */
    public List<cv> getResearch(String research) throws IOException {
        String str = research.replace(";", " AND ");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(str));
        /*String[] listResearch = research.split(";");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        for(int i = 0; i < listResearch.length; i++){

            searchSourceBuilder.query(QueryBuilders.matchQuery("content", listResearch[i]));
        }*/
        return executeQuery(searchSourceBuilder);
    }

    /**
     * Execute la requête et transmet les résultats
     * @param searchSourceBuilder
     * @return
     * @throws IOException
     */
    private List<cv> executeQuery(SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("cv");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Stream.of(searchResponse.getHits().getHits())
                .map(hit -> hit.getSourceAsString())
                .map(cvs -> gson.fromJson(cvs, cv.class))
                .collect(toList());
    }


}
