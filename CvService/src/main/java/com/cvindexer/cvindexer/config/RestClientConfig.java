package com.cvindexer.cvindexer.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

public class RestClientConfig extends AbstractElasticsearchConfiguration {

    /**
     * Permet de connecter l'application à elasticSearch
     * @return le client elastic search
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        //Configuration d'elastic search, le port est à changé en fonction des env
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
