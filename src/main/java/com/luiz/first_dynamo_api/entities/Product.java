package com.luiz.first_dynamo_api.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@DynamoDbBean
public class Product {
    private UUID id;
    private String name;
    private Double price;

    @DynamoDbSortKey
    @DynamoDbAttribute("id")
    public UUID getId() {
        return id;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("price")
    public Double getPrice() {
        return price;
    }
}