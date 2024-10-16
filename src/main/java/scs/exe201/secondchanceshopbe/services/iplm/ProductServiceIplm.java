package scs.exe201.secondchanceshopbe.services.iplm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ProductRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.ProductService;
import scs.exe201.secondchanceshopbe.utils.DTOToEntity;

@Service
@Transactional
public class ProductServiceIplm implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final DTOToEntity dtoToEntity;

    public ProductServiceIplm(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, DTOToEntity dtoToEntity) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.dtoToEntity = dtoToEntity;
    }

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
                .image(productEntity.getImage())
                .dateCreate(productEntity.getDateCreate())
                .createByUsername(productEntity.getCreateBy().getUsername())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<ProductEntity> productEntities = productRepository.getAll(pageable);
        if (productEntities.isEmpty()) {
            throw new NotFoundException( "Products not found");
        }

        List<ProductResponse> productResponses = productEntities.getContent().stream()
                .map(this::mapProductEntityToProductResponse)
                .collect(Collectors.toList());

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
            .map(this::mapProductEntityToProductResponse)
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

        return mapProductEntityToProductResponse(savedProduct);
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
}