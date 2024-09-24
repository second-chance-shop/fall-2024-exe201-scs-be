package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.services.ProductService;

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

    @PostMapping("/addProduct")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody ProductCreateDTO product) {

        ProductResponse response = productService.createProduct(product);
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

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable long id, @RequestBody ProductUpdateDTO product) {

        ProductResponse response = productService.updateProduct(id, product);
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
