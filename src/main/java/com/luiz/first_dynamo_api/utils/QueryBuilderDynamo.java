package com.luiz.first_dynamo_api.utils;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

public class QueryBuilderDynamo<T> {
    private final String partitionKey;
    private final DynamoDbTemplate dynamoDbTemplate;
    private final Class<T> entityType;

    public QueryBuilderDynamo(String partitionKey, DynamoDbTemplate dynamoDbTemplate, Class<T> entityType) {
        this.partitionKey = partitionKey;
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.entityType = entityType;
    }

    public List<T> createQuery() {
        var condition = QueryConditional.keyEqualTo(Key.builder().partitionValue(this.partitionKey).build());
        var q = QueryEnhancedRequest.builder().queryConditional(condition).build();
        PageIterable<T> result = dynamoDbTemplate.query(q, entityType);
        return result.items().stream().toList();
    }
}