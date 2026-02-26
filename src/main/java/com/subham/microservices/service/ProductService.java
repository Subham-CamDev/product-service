package com.subham.microservices.service;

import com.subham.microservices.dto.ProductRequest;
import com.subham.microservices.dto.ProductResponse;
import com.subham.microservices.entity.Product;
import com.subham.microservices.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductResponse createProduct(ProductRequest request) {

    Product product = Product.builder()
            .skuCode(request.skuCode())
            .name(request.name())
            .description(request.description())
            .price(request.price())
            .build();

    Product savedP = productRepository.save(product);
    log.info("New Product successfully added with ID: {}", savedP.getId());

    return new ProductResponse(
            savedP.getId(), savedP.getSkuCode(), savedP.getName(),
            savedP.getDescription(), savedP.getPrice()
    );
  }

  public List<ProductResponse> getProducts() {
    return productRepository.findAll()
            .stream()
            .map(product -> new ProductResponse(
                    product.getId(), product.getSkuCode(), product.getName(),
                    product.getDescription(), product.getPrice()
            ))
            .toList();
  }

  public void deleteAllProducts() {
    log.info("Deleting all products from the database");
    productRepository.deleteAll();
  }
}
