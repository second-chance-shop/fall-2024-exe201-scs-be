package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;

import java.util.List;

@Service
public interface ProductService {
    Page<ProductResponse> getAllProducts(int page, int size);

    ProductResponse deleteProduct(long idProduct);

    ProductResponse getProductById(long idProduct);

    ProductResponse addProduct(ProductCreateDTO product, List<MultipartFile> files);

    List<ProductResponse> getAll();

    ProductResponse updateProductv1(ProductUpdateDTO product, List<MultipartFile> files);
}
