package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductDTO;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;

import java.util.Optional;

@Service
public interface ProductService {
    Page<ProductEntity> getAllProducts(int page, int size);
    ResponseEntity<Object> addProduct(ProductDTO product);
    ResponseEntity<Object> deleteProduct(long idProduct);
    ResponseEntity<Object> updateProduct(long idProduct, ProductDTO product);
    Optional<ProductEntity> getProductById(long idProduct);
}
