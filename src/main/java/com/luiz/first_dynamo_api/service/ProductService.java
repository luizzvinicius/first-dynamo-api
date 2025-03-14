package com.luiz.first_dynamo_api.service;

import com.luiz.first_dynamo_api.dtos.ProductDto;
import com.luiz.first_dynamo_api.entities.Product;
import com.luiz.first_dynamo_api.exceptions.RecordNotFoundExp;
import com.luiz.first_dynamo_api.utils.QueryBuilderDynamo;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final DynamoDbTemplate dynamoDbTemplate;

    public ProductService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

//    public ProductPageDTO getAllProducts(@PositiveOrZero int pageNumber, @Positive @Max(100) int qtdProducts) {
//        Page<Product> page = dynamoDbTemplate.findAllProductByStatusEquals(PageRequest.of(pageNumber, qtdProducts));
//        if (page.isEmpty()) {
//            throw new RecordNotFoundExp("Product");
//        }
//        List<ProductDto> productDTOS = page.get().map(productMapper::toDTO).toList();
//        return new ProductPageDTO(productDTOS, page.getTotalElements(), page.getTotalPages());
//    }

    public ProductDto saveProduct(@Valid ProductDto dto) {
        var result = new QueryBuilderDynamo<>(dto.name(), dynamoDbTemplate, Product.class).createQuery();

        if (!result.isEmpty()) {
            throw new RuntimeException("Product already exists");
        }

        var savedProduct = dynamoDbTemplate.save(new Product(UUID.randomUUID(), dto.name(), dto.price()));
        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
    }

    public Product getOneProduct(String name) {
        var result = new QueryBuilderDynamo<>(name, dynamoDbTemplate, Product.class).createQuery();

        return result.stream().findFirst()
                .orElseThrow(() -> new RecordNotFoundExp("Product with name " + name));
    }

    public ProductDto updateProduct(@Valid ProductDto dto) {
        Optional<Product> result = new QueryBuilderDynamo<>(dto.name(), dynamoDbTemplate, Product.class).createQuery().stream().findFirst();

        return result.map(p -> {
            p.setName(dto.name());
            p.setPrice(dto.price());
            dynamoDbTemplate.update(p);
            return new ProductDto(p.getId(), p.getName(), p.getPrice());
        }).orElseThrow(() -> new RecordNotFoundExp("Product with id " + dto.id()));
    }

    public void deleteProduct(String name) {
        var result = new QueryBuilderDynamo<>(name, dynamoDbTemplate, Product.class).createQuery().stream().findFirst();

        result.ifPresentOrElse(
                dynamoDbTemplate::delete,
                () -> {
                    throw new RecordNotFoundExp("Product with name " + name);
                }
        );
    }
}