package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.ProductService;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAllProduct")
    public ResponseEntity<ResponseObject> getProducts(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<ProductResponse> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Products retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(products)
                        .build()
        );
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAll() {
        List<ProductResponse> products = productService.getAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Products retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(products)
                        .build()
        );
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable long idProduct) {
        ProductResponse product = productService.getProductById(idProduct);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Product retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(product)
                        .build()
        );
    }

@PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ResponseObject> addProductV1(
        @RequestPart("product") ProductCreateDTO product,
        @RequestPart(value = "file", required = false) List<MultipartFile> files) { // Cho phép file là null

    // Gọi phương thức service để thêm sản phẩm
    ProductResponse response = productService.addProduct(product, files);

    // Trả về ResponseEntity
    return ResponseEntity.ok().body(
            ResponseObject.builder()
                    .code("ADD_SUCCESS")
                    .message("Product added successfully")
                    .status(HttpStatus.OK)
                    .isSuccess(true)
                    .data(response)
                    .build()
    );
}

    @DeleteMapping("/deleteProduct/{idProduct}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long idProduct) {

        ProductResponse response = productService.deleteProduct(idProduct);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("DELETE_SUCCESS")
                        .message("Product deleted successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }

// here
    @PutMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> updateProductV1(
            @RequestPart("product") ProductUpdateDTO product,
            @RequestPart(value = "file", required = false) List<MultipartFile> files) {
        ProductResponse response = productService.updateProductv1(product,files);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Product updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
}
