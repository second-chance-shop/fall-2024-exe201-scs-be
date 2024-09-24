package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CategoryCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CategoryResponse;

import java.util.Optional;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateDTO categoryRequest);

    Optional<CategoryResponse> getCategoryById(Long categoryId);

    Page<CategoryResponse> getAllCategories(int page, int size);

    CategoryResponse updateCategory(Long categoryId, CategoryCreateDTO categoryRequest);

    void deleteCategory(Long categoryId);
}
