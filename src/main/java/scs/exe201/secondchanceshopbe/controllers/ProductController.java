package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.ProductService;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<ResponseObject> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue
                    = "prices") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {




        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Create Pageable object
        PageRequest pageable = PageRequest.of(page, size, sort);

        // Call the service method with sorting and pagination
        Page<ProductResponse> products = productService.searchProducts(category, productName, pageable);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Products retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(products.getContent())
                        .totalPages((long) products.getTotalPages())
                        .totalElements(products.getTotalElements())
                        .currentPage(products.getNumber())
                        .build()
        );
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable long idProduct) {
        ProductResponse product = productService.getProductById(idProduct).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
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
    @GetMapping("/shop/{id}")
    public ResponseEntity<ResponseObject> getAllByShopId(@PathVariable long id) {
        List<ProductResponse> products = productService.getAllByShopId(id);
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
}
