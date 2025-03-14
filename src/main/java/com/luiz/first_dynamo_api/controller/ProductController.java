package com.luiz.first_dynamo_api.controller;

import com.luiz.first_dynamo_api.dtos.ProductDto;
import com.luiz.first_dynamo_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public ResponseEntity<ProductPageDto> getAllProducts(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
//                                                         @RequestParam(defaultValue = "20") @Positive @Max(100) int qtdProducts) {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(page, qtdProducts));
//    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(dto));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getOneProduct(@PathVariable String name) {
        var product = productService.getOneProduct(name);
        return ResponseEntity.ok().body(new ProductDto(product.getId(), product.getName(), product.getPrice()));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.updateProduct(dto));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
        return ResponseEntity.noContent().build();
    }
}