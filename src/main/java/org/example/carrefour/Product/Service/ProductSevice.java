package org.example.carrefour.Product.Service;


import org.example.carrefour.Product.Dto.ProductRequestDto;
import org.example.carrefour.Product.Dto.ProductResponseDto;
import org.example.carrefour.Product.Entity.Product;
import org.example.carrefour.Product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSevice {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponseDto> getAllProducts() {
        try{
            return this.productRepository
                    .findAll()
                    .stream()
                    .map(this::mapEntityToDto)
                    .toList();
        }catch (Exception e){
         throw new RuntimeException(e);
        }
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        try{
            if(productRequestDto.getName().equals("coca")){
                throw new RuntimeException("Product cannot be coca");
            }
            return this.mapEntityToDto(this.productRepository.save(this.mapDtoToEntity(productRequestDto)));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Product mapDtoToEntity(ProductRequestDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        return product;
    }

    private ProductResponseDto mapEntityToDto(Product product){
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        return productDto;

    }

}
