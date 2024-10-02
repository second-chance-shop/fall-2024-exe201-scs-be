package scs.exe201.secondchanceshopbe.services.Iplm;


import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CategoryCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.CategoryResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateDTO categoryRequest) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryRequest.getCategoryName());

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mapToResponse(savedCategory);
    }

    @Override
    public Optional<CategoryResponse> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(this::mapToResponse);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> categoryResponses = categoryPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryResponses, pageable, categoryPage.getTotalElements());
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryCreateDTO categoryRequest) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryEntity.setCategoryName(categoryRequest.getCategoryName());
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        return mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }

    private CategoryResponse mapToResponse(CategoryEntity categoryEntity) {
        return new CategoryResponse(
                categoryEntity.getCategoryId(),
                categoryEntity.getCategoryName()
        );
    }
}