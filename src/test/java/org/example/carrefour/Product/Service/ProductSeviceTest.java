package org.example.carrefour.Product.Service;

import jakarta.inject.Inject;
import org.example.carrefour.Product.Dto.ProductResponseDto;
import org.example.carrefour.Product.Entity.Product;
import org.example.carrefour.Product.Mapper.ProductMapper;
import org.example.carrefour.Product.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSeviceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductSevice productSevice;

    private List<ProductResponseDto> productResponseDtoList= new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.productResponseDtoList.add(new ProductResponseDto(1L, "coca"));
        this.productResponseDtoList.add(new ProductResponseDto(1L, "coca"));
    }


    @Test
    public void getAllProducts() {
        List<Product> mockProducts = List.of(
                new Product(1L, "coca"),
                new Product(2L, "fanta")
        );

        when(productRepository.findAll()).thenReturn(mockProducts);
        List<ProductResponseDto> productResponseDtos = productSevice.getAllProducts();
        assertNotNull(productResponseDtos);
        assertEquals(2, productResponseDtos.size());
        assertEquals("coca", productResponseDtos.get(0).getName());
        assertEquals("fanta", productResponseDtos.get(1).getName());
    }


    @Test
    public void testMapEntityToDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        ProductResponseDto dto = ProductMapper.INSTANCE.mapEntityToDto(product);

        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
    }


}