package com.subham.product_service.controller;

import com.subham.product_service.dto.ProductRequest;
import com.subham.product_service.dto.ProductResponse;
import com.subham.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(description = "Create a new product by providing the name, description and price in the request body.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Product Creation Successful"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
    try {
      ProductResponse product = productService.createProduct(request);
      return new ResponseEntity<>(product, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("Exception occurred while creating a new product", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Operation(description = "Get the list of all products available in the system.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Return the list of all products"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts() {
    try {
      List<ProductResponse> products = productService.getProducts();
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception occurred while fetching all products", e);
      return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
