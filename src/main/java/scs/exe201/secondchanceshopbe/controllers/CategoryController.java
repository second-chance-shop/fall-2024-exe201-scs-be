package scs.exe201.secondchanceshopbe.controllers;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CategoryCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CategoryResponse; // Import CategoryResponse
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.CategoryService;
import scs.exe201.secondchanceshopbe.utils.Constants;

import static scs.exe201.secondchanceshopbe.utils.Constants.*;

@RequestMapping("/api/v1/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAllCategory")
    public ResponseEntity<ResponseObject> getCategories(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<CategoryResponse> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code(GET_SUCCESS)
                        .message(GET_SUCCESS)
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(categories)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable long id) {
        CategoryResponse category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,Constants.CATEGORY_NOT_FOUND));
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code(GET_BY_ID_SUCCESS)
                        .message(GET_BY_ID_SUCCESS)
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(category)
                        .build()
        );
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ResponseObject> addCategory(@RequestBody CategoryCreateDTO categoryRequest) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code(CREATE_SUCCESS)
                        .message(CREATE_SUCCESS)
                        .status(HttpStatus.CREATED)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code(DELETE_SUCCESS)
                        .message(DELETE_SUCCESS)
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .build()
        );
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable long id, @RequestBody CategoryCreateDTO categoryRequest) {
        CategoryResponse response = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code(UPDATE_SUCCESS)
                        .message(UPDATE_SUCCESS)
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
}
