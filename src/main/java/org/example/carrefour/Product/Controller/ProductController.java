package org.example.carrefour.Product.Controller;

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
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productSevice.getAllProducts());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productDto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productSevice.createProduct(productDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
