package com.luiz.first_dynamo_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoConfig {
    @Value("${accesskey}")
    private String accessKey;

    @Value("${secretkey}")
    private String secretKey;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        var credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1).credentialsProvider(credentials)
                .build();
    }
}