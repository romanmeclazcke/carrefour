package org.example.carrefour.Product.Service;


import org.example.carrefour.Product.Dto.ProductRequestDto;
import org.example.carrefour.Product.Dto.ProductResponseDto;
import org.example.carrefour.Product.Entity.Product;
import org.example.carrefour.Product.Mapper.ProductMapper;
import org.example.carrefour.Product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                    .map(ProductMapper.INSTANCE::mapEntityToDto)
                    .toList();
        }catch (Exception e){
         throw new RuntimeException(e);
        }
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        try{
            Product p = ProductMapper.INSTANCE.mapDtoToEntity(productRequestDto);
            this.productRepository.save(p);
            return ProductMapper.INSTANCE.mapEntityToDto(p);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
