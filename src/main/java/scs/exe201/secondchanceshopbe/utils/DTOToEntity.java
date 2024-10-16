package scs.exe201.secondchanceshopbe.utils;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.dtos.enums.MethodPayment;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.*;

import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DTOToEntity {

    private final UserRepository userRepository;
    public static UserEntity UserResponseToEntity(UserRegisterDTO userRegister) {
        if(userRegister == null) {
            throw new ActionFailedException("User empty");
        }
        return UserEntity.builder()
                .username(userRegister.getUsername())
                .address(userRegister.getAddress())
                .gender(userRegister.getGender())
                .phoneNumber(userRegister.getPhoneNumber())
                .dob(userRegister.getDob())
                .email(userRegister.getEmail())
                .name(userRegister.getName())
                .avatar(userRegister.getAvatar())
                .build();
    }

    public static ProductEntity mapProductCreateDTOToProductEntity(ProductCreateDTO productCreateDTO, Set<CategoryEntity> categories, UserEntity createBy) {
        if (productCreateDTO == null) {
            throw new ActionFailedException("ProductCreateDTO cannot be null");
        }

        return ProductEntity.builder()
                .productName(productCreateDTO.getProductName())
                .quantity(productCreateDTO.getQuantity())
                .description(productCreateDTO.getDescription())
                .categories(categories)  // Mapping resolved categories
                .prices(productCreateDTO.getPrices())
                .status(StatusEnum.ACTIVE)
                .image(productCreateDTO.getImage())
                .dateCreate( LocalDate.now())
                .createBy(createBy)  // Mapping resolved user entity
                .build();
    }
    public static ProductEntity mapProductUpdateDTOToProductEntity(ProductUpdateDTO productUpdateDTO,  Set<CategoryEntity> categories) {
        if (productUpdateDTO == null ) {
            throw new ActionFailedException("ProductUpdateDTO and  cannot be null");
        }
       return ProductEntity.builder()
               .productName(productUpdateDTO.getProductName())
               .quantity(productUpdateDTO.getQuantity())
               .description(productUpdateDTO.getDescription())
               .categories(categories)
               .prices(productUpdateDTO.getPrices())
               .image(productUpdateDTO.getImage())
               .dateCreate(productUpdateDTO.getDateCreate() != null ? productUpdateDTO.getDateCreate() : null)
               .build();
    }

    public static CategoryEntity mapCategoryCreateDTOToCategoryEntity(CategoryCreateDTO categoryCreateDTO) {
        if (categoryCreateDTO == null) {
            throw new ActionFailedException("CategoryCreateDTO cannot be null");
        }
        return CategoryEntity.builder()
                .categoryName(categoryCreateDTO.getCategoryName())
                .status(StatusEnum.ACTIVE)
                .description(categoryCreateDTO.getDescription())
                .build();
    }
    public static CategoryEntity mapCategoryUpdateDTOToCategoryEntity(CategoryUpdateDTO categoryUpdateDTO) {

        if (categoryUpdateDTO == null) {
            throw new ActionFailedException("CategoryCreateDTO cannot be null");
        }
        return CategoryEntity.builder()
                .categoryName(categoryUpdateDTO.getCategoryName())
                .status(StatusEnum.ACTIVE)
                .description(categoryUpdateDTO.getDescription())
                .build();
    }
    public  CommentEntity mapCommentCreateDTOToCommentEntity(CommentCreateDTO commentCreateDTO ) {
        if (commentCreateDTO == null) {
            throw new ActionFailedException("CommentCreateDTO cannot be null");
        }
        UserEntity userEntity = userRepository.findById(commentCreateDTO.getUserId()).orElseThrow(
                ()-> new NotFoundException("user not found")
        );
        return CommentEntity.builder()
                .content(commentCreateDTO.getContent())
                .dateCreate(LocalDate.now())

                .build();
    }




}
