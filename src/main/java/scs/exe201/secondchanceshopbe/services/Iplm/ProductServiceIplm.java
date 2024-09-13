package scs.exe201.secondchanceshopbe.services.Iplm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.services.ProductService;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceIplm implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceIplm(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductEntity> getAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<ProductEntity> products = productRepository.findAll(pageable);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return products;
    }

    @Override
    public ResponseEntity<Object> addProduct(ProductEntity product) {
        try {
            ProductEntity savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(long idProduct) {
        try {
            productRepository.deleteById(idProduct);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(ProductEntity product) {
        if (productRepository.existsById(product.getProductId())) {
            ProductEntity updatedProduct = productRepository.save(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<ProductEntity> getProductById(long idProduct) {
        return productRepository.findById(idProduct);
    }
}
