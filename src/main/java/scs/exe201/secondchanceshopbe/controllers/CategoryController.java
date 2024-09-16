package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.services.CategoryService;

@RequestMapping("/api/v1/category")
@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/getAllCategory")
    public ResponseEntity<ResponseObject> getProducts(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size) {

        Page<CategoryEntity> categorys = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Products retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(categorys)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable long id) {

        CategoryEntity category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Category retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(category)
                        .build()
        );

    }

    @PostMapping("/addCategory")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody CategoryEntity category) {

        ResponseEntity<Object> response = categoryService.addCategory(category);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("ADD_SUCCESS")
                        .message("Category added successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );

    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {

        ResponseEntity<Object> response = categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("DELETE_SUCCESS")
                        .message("Category deleted successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );

    }

    @PutMapping("/updateCategory")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody CategoryEntity category) {

        ResponseEntity<Object> response = categoryService.updateCategory(category);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("Category updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response.getBody())
                        .build()
        );
    }
}
