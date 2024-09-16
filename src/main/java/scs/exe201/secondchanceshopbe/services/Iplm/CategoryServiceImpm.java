package scs.exe201.secondchanceshopbe.services.Iplm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.repositories.CategoryReposity;
import scs.exe201.secondchanceshopbe.services.CategoryService;

import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpm implements CategoryService {
    private CategoryReposity categoryReposity;

    @Override
    public Page<CategoryEntity> getAllCategories(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<CategoryEntity> category = categoryReposity.findAll(pageable);
        if (category.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return category;
    }

    @Override
    public ResponseEntity<Object> addCategory(CategoryEntity category) {
        try {
            CategoryEntity savedProduct = categoryReposity.save(category);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteCategory(long id) {
        try {
            categoryReposity.deleteById(id);
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateCategory(CategoryEntity category) {
        if (categoryReposity.existsById(category.getCategoryId())) {
            CategoryEntity updatedCategory = categoryReposity.save(category);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(long idProduct) {
        return categoryReposity.findById(idProduct);
    }
}
