package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;

@Service
public interface ProductService {
    Page<ProductResponse> getAllProducts(int page, int size);

    ProductResponse createProduct(ProductCreateDTO request);

    ProductResponse updateProduct(Long productId, ProductUpdateDTO request);

    ProductResponse deleteProduct(long idProduct);
    ProductResponse getProductById(long idProduct);
}
