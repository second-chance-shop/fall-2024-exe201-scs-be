package scs.exe201.secondchanceshopbe.services.iplm;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.FileObjectResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.ActionFailedException;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.FileDatabaseService;
import scs.exe201.secondchanceshopbe.services.FileService;
import scs.exe201.secondchanceshopbe.services.ProductService;
import scs.exe201.secondchanceshopbe.utils.DTOToEntity;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceIplm implements ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final DTOToEntity dtoToEntity;
    private final FileDatabaseService fileDatabaseService;



    // Map ProductEntity to ProductResponse
    private ProductResponse mapProductEntityToProductResponse(ProductEntity productEntity) {
        Set<String> categoryNames = productEntity.getCategories().stream()
                .map(CategoryEntity::getCategoryName)
                .collect(Collectors.toSet());

        return ProductResponse.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .quantity(productEntity.getQuantity())
                .description(productEntity.getDescription())
                .categoryNames(categoryNames)
                .prices(productEntity.getPrices())
                .status(productEntity.getStatus().toString())
           //     .image(productEntity.getImage())
                .dateCreate(productEntity.getDateCreate())
                .createByUsername(productEntity.getCreateBy().getUsername())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page-1, size);
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = productEntities.stream().map(EntityToDTO::productEntityToDTO).toList();
        return new PageImpl<>(productResponses, pageable, productEntities.getTotalElements());
    }
    @Override
public Page<ProductResponse> getAllProductsv1(String search, String sortField, String sortDirection, int page, int size) {
    // Phân trang và sắp xếp
    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
    PageRequest pageable = PageRequest.of(page - 1, size, sort);
    
    // Tìm kiếm (nếu có chuỗi tìm kiếm)
    Page<ProductEntity> productEntities;
    if (search != null && !search.isEmpty()) {
        productEntities = productRepository.searchByNameOrDescription(search, pageable);
    } else {
        productEntities = productRepository.getAll(pageable);
    }

    // Xử lý nếu không tìm thấy sản phẩm nào
    if (productEntities.isEmpty()) {
        throw new NotFoundException("Products not found");
    }

    // Chuyển đổi từ ProductEntity sang ProductResponse
    List<ProductResponse> productResponses = productEntities.getContent().stream()
            .map(EntityToDTO::productEntityToDTO)
            .collect(Collectors.toList());

    // Trả về trang kết quả
    return new PageImpl<>(productResponses, pageable, productEntities.getTotalElements());
}

    @Override
    public ProductResponse createProduct(ProductCreateDTO request) {
        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        var auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity createBy = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        ProductEntity productEntity = dtoToEntity.mapProductCreateDTOToProductEntity(request, categories, createBy);
        ProductEntity savedProduct = productRepository.save(productEntity);

        return EntityToDTO.productEntityToDTO(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateDTO request) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        productEntity = DTOToEntity.mapProductUpdateDTOToProductEntity(request,categories);
        ProductEntity updatedProduct = productRepository.save(productEntity);
        return mapProductEntityToProductResponse(updatedProduct);
    }

    @Override
    public ProductResponse deleteProduct(long idProduct) {
        ProductEntity productEntity = productRepository.findById(idProduct)
                .orElseThrow(() -> new NotFoundException( "Product not found"));
        productEntity.setStatus(StatusEnum.DELETED);
        productRepository.save(productEntity);
        return mapProductEntityToProductResponse(productEntity);
    }

    @Override
    public ProductResponse getProductById(long idProduct) {
        ProductEntity productEntity = productRepository.findById(idProduct)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return mapProductEntityToProductResponse(productEntity);
    }


    @Override
    public ProductResponse addProduct(ProductCreateDTO product, List<MultipartFile> files) {
        if(product.getCategoryIds().isEmpty()||product == null){
            throw new ActionFailedException("Product empty");
        }
        List<String> imageUrls = new ArrayList<>();
        if (files!= null && !files.isEmpty()) {
            List<FileObjectResponse> images = fileDatabaseService.upNhieufile(files);

            // Lấy URL từ FileObjectResponse
            for (FileObjectResponse image : images) {
                imageUrls.add(image.getUrl());
            }
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found"));
        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(product.getCategoryIds()));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(product.getProductName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrices(product.getPrices());
        productEntity.setCategories(categories);
        productEntity.setStatus(StatusEnum.ACTIVE);
        productEntity.setCreateBy(userEntity);
        productEntity.setDateCreate(LocalDate.now());
        try {
            productEntity.setImages(imageUrls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ActionFailedException("Error processing image URLs: " + e.getMessage());
        }

        productRepository.save(productEntity);

     return mapProductEntityToProductResponse(productEntity);
    }
    @Override
    public List<ProductResponse> getAll() {
        List<ProductEntity> productEntities = productRepository.findAll();

        return productEntities.stream().map(EntityToDTO::productEntityToDTO).toList();
    }

    @Override
    public ProductResponse updateProductv1(ProductUpdateDTO product, List<MultipartFile> files) {
        ProductEntity productEntity = productRepository.findById(product.getId()).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
        List<String> imageUrls = new ArrayList<>();
        if (files!= null && !files.isEmpty()) {
            List<FileObjectResponse> images = fileDatabaseService.upNhieufile(files);
            // Lấy URL từ FileObjectResponse
            for (FileObjectResponse image : images) {
                imageUrls.add(image.getUrl());
            }
            try {
                productEntity.setImages(imageUrls);
            }catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ActionFailedException("Error processing image URLs: " + e.getMessage());
            }
        }
        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(product.getCategoryIds()));
        productEntity.setProductName(product.getProductName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrices(product.getPrices());
        productEntity.setCategories(categories);

        productRepository.save(productEntity);
        return EntityToDTO.productEntityToDTO(productEntity);
    }
}
