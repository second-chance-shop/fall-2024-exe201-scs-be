package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryEntity> getAllCategories(int page, int size);
    ResponseEntity<Object> addCategory(CategoryEntity category);
    ResponseEntity<Object> deleteCategory(long id);
    ResponseEntity<Object> updateCategory(CategoryEntity category);
    Optional<CategoryEntity> getCategoryById(long id);
}
