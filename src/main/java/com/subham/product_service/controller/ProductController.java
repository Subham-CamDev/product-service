package com.subham.product_service.controller;

import com.subham.product_service.dto.ProductRequest;
import com.subham.product_service.dto.ProductResponse;
import com.subham.product_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<String> createProduct(@RequestBody ProductRequest request) {
    try {
      String productId = productService.createProduct(request);
      log.info("Product created with ID : {}", productId);
      return new ResponseEntity<>("Product Creation Successful",
              HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("Exception occurred while creating a new product", e);
      return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts() {
    try {
      List<ProductResponse> products = productService.getProducts();
      log.info("The list of products: {}", products);
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception occurred while fetching all products", e);
      return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
