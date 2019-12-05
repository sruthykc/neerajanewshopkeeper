package com.diviso.graeshoppe.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ElasticRestClientConfig {
	
    @Value("${elasticsearch.host:20a60de2fab94b1b9aca60bcfee1591b.us-central1.gcp.cloud.es.io}")
    public String host;
    @Value("${elasticsearch.port:9243}")
    public int port;
    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }

    private int timeout = 60;
    @Bean
    public RestHighLevelClient client(){
        System.out.println("host:"+ host+"port:"+port);
        final CredentialsProvider credentialsProvider =new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic", "7hcaOIiz0JiemFumZFRsLz6E"));
        RestClientBuilder builder =RestClient.builder(new HttpHost(host, port, "https")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        builder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(timeout * 1000).setSocketTimeout(timeout * 1000)
                .setConnectionRequestTimeout(0));

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}