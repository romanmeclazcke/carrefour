package org.example.carrefour.Product.Controller;

import jakarta.validation.Valid;
import org.example.carrefour.Product.Dto.ProductRequestDto;
import org.example.carrefour.Product.Service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductSevice productSevice;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(this.productSevice.getAllProducts());
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productDto) {
            return ResponseEntity.status(HttpStatus.OK).body(this.productSevice.createProduct(productDto));
    }

}
