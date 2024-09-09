package scs.exe201.secondchanceshopbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.services.ProductService;
import scs.exe201.secondchanceshopbe.utils.Constants;

import java.util.Map;

@RequestMapping("/api/v1/product")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @Operation(
            summary = "Get Products",
            description = "Get List Product in DB"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/getAllProduct")
    public ResponseEntity<Object> getProducts(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProductEntity> products = productService.getAllProducts(page, size);
            return ResponseEntity.ok().body(products);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    Map.of(Constants.STATUS, e.getStatusCode().value(), Constants.MESSAGE, e.getReason())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.MESSAGE, e.getMessage())
            );
        }
    }

    @Operation(
            summary = "Get Products",
            description = "Get Products by Id"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{idProduct}")
    public ResponseEntity<Object> getProduct(@PathVariable long idProduct) {
        try {
            ProductEntity product = productService.getProductById(idProduct)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            return ResponseEntity.ok().body(product);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    Map.of(Constants.STATUS, e.getStatusCode().value(), Constants.MESSAGE, e.getReason())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.MESSAGE, e.getMessage())
            );
        }
    }

    @Operation(
            summary = "Add Product",
            description = "Add a new product to the database"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping("/addProduct")
    public ResponseEntity<Object> addProduct(@RequestBody ProductEntity product) {
        try {
            ResponseEntity<Object> response = productService.addProduct(product);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.MESSAGE, e.getMessage())
            );
        }
    }

    @Operation(
            summary = "Delete Product",
            description = "Delete a product by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @DeleteMapping("/deleteProduct/{idProduct}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long idProduct) {
        try {
            ResponseEntity<Object> response = productService.deleteProduct(idProduct);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.MESSAGE, e.getMessage())
            );
        }
    }

    @Operation(
            summary = "Update Product",
            description = "Update an existing product"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductEntity product) {
        try {
            ResponseEntity<Object> response = productService.updateProduct(product);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.MESSAGE, e.getMessage())
            );
        }
    }
}
