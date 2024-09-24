package scs.exe201.secondchanceshopbe.utils;

import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UserRegisterDTO;

import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;

import java.time.LocalDate;
import java.util.Set;

@Service
public class DTOToEntity {
    public static UserEntity UserResponseToEntity(UserRegisterDTO userRegister) {
        // Create a new UserEntity object
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegister.getUsername());
        userEntity.setAddress(userRegister.getAddress());
        userEntity.setGender(userRegister.getGender());
        userEntity.setPhoneNumber(userRegister.getPhoneNumber());
        userEntity.setDob(userRegister.getDob());
        userEntity.setEmail(userRegister.getEmail());
        userEntity.setName(userRegister.getName());
        userEntity.setAvatar(userRegister.getAvatar());
        // Return the mapped UserEntity object
        return userEntity;
    }

    public ProductEntity mapProductCreateDTOToProductEntity(ProductCreateDTO productCreateDTO, Set<CategoryEntity> categories, UserEntity createBy) {
        if (productCreateDTO == null) {
            throw new IllegalArgumentException("ProductCreateDTO cannot be null");
        }

        return ProductEntity.builder()
                .productName(productCreateDTO.getProductName())
                .quantity(productCreateDTO.getQuantity())
                .description(productCreateDTO.getDescription())
                .categories((Set<CategoryEntity>) categories)  // Mapping resolved categories
                .prices(productCreateDTO.getPrices())
                .status("true")
                .image(productCreateDTO.getImage())
                .dateCreate(productCreateDTO.getDateCreate() != null ? productCreateDTO.getDateCreate() : LocalDate.now())
                .createBy(createBy)  // Mapping resolved user entity
                .build();
    }
    public void mapProductUpdateDTOToProductEntity(ProductUpdateDTO productUpdateDTO, ProductEntity productEntity, Set<CategoryEntity> categories) {
        if (productUpdateDTO == null || productEntity == null) {
            throw new IllegalArgumentException("ProductUpdateDTO and ProductEntity cannot be null");
        }

        productEntity.setProductName(productUpdateDTO.getProductName());
        productEntity.setQuantity(productUpdateDTO.getQuantity());
        productEntity.setDescription(productUpdateDTO.getDescription());
        productEntity.setCategories(categories);  // Updating categories
        productEntity.setPrices(productUpdateDTO.getPrices());
        productEntity.setStatus(productUpdateDTO.getStatus());
        productEntity.setImage(productUpdateDTO.getImage());
        productEntity.setDateCreate(productUpdateDTO.getDateCreate() != null ? productUpdateDTO.getDateCreate() : productEntity.getDateCreate());  // Keep original if null
    }

}
