package org.example.carrefour.Product;
import org.example.carrefour.Product.Dto.ProductRequestDto;
import org.example.carrefour.Product.Repository.ProductRepository;
import org.example.carrefour.Product.Service.ProductSevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductTest {
    @InjectMocks//dice que la clase recibira como dependencia los mock que definimos
    private ProductSevice productSevice;

    @Mock //indica que es un objeto simulado
    private ProductRepository productRepository;


    @Test
    void createProduct() {
        ProductRequestDto p= new ProductRequestDto("pepe");
        assertNotNull(productSevice.createProduct(p),"Product wasnt creat");
    }


    @Test
    void getAllProducts() {
        assertNotNull(productSevice.getAllProducts());
    }

}
