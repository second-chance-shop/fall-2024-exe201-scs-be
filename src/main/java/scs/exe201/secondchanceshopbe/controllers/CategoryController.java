package scs.exe201.secondchanceshopbe.controllers;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CategoryCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CategoryResponse; // Import CategoryResponse
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.CategoryService;

@RequestMapping("/api/v1/category")
@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAllCategory")
    public ResponseEntity<ResponseObject> getCategories(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<CategoryResponse> categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("FETCH_SUCCESS")
                        .message("Categories retrieved successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(categories)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable long id) {
        CategoryResponse category = categoryService.getCategoryById(id)
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
    public ResponseEntity<ResponseObject> addCategory(@RequestBody CategoryCreateDTO categoryRequest) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("ADD_SUCCESS")
                        .message("Category added successfully")
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
                        .code("DELETE_SUCCESS")
                        .message("Category deleted successfully")
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
                        .code("UPDATE_SUCCESS")
                        .message("Category updated successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
}
