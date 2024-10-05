package scs.exe201.secondchanceshopbe.services.Iplm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ProductResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
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
                .status(productEntity.getStatus())
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found");
        }

        List<ProductResponse> productResponses = productEntities.getContent().stream()
                .map(this::mapProductEntityToProductResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, productEntities.getTotalElements());
    }

    @Override
    public ProductResponse createProduct(ProductCreateDTO request) {
        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        UserEntity createBy = userRepository.findById(request.getCreateByUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ProductEntity productEntity = dtoToEntity.mapProductCreateDTOToProductEntity(request, categories, createBy);
        ProductEntity savedProduct = productRepository.save(productEntity);

        return mapProductEntityToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateDTO request) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        dtoToEntity.mapProductUpdateDTOToProductEntity(request, productEntity, categories);

        ProductEntity updatedProduct = productRepository.save(productEntity);
        return mapProductEntityToProductResponse(updatedProduct);
    }

    @Override
    public ProductResponse deleteProduct(long idProduct) {
        ProductEntity productEntity = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productEntity.setStatus("DELETE");
        productRepository.save(productEntity);
        return mapProductEntityToProductResponse(productEntity);
    }

    @Override
    public ProductResponse getProductById(long idProduct) {
        ProductEntity productEntity = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return mapProductEntityToProductResponse(productEntity);
    }
}
