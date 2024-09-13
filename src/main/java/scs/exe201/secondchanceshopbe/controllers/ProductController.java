package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

        Page<ProductEntity> products = productService.getAllProducts(page, size);
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

        ProductEntity product = productService.getProductById(idProduct)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
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
    public ResponseEntity<ResponseObject> addProduct(@RequestBody ProductEntity product) {

        ResponseEntity<Object> response = productService.addProduct(product);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("ADD_SUCCESS")
                        .message("Product added successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );

    }

    @DeleteMapping("/deleteProduct/{idProduct}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long idProduct) {

        ResponseEntity<Object> response = productService.deleteProduct(idProduct);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("DELETE_SUCCESS")
                        .message("Product deleted successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );

    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody ProductEntity product) {

        ResponseEntity<Object> response = productService.updateProduct(product);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Product updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );
    }
}
