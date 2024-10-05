package scs.exe201.secondchanceshopbe.services.Iplm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ShopRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.ShopService;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {


    private final ShopRepository shopRepository;


    private final UserRepository userRepository;


    private final CategoryRepository categoryRepository;

    @Override
    public ShopResponse createShop(ShopRequestDTO shopRequest) {
        UserEntity shopOwner = userRepository.findById(Long.valueOf(shopRequest.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        CategoryEntity typeShop = categoryRepository.findById(Long.valueOf(shopRequest.getCategoryId()))
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setShopName(shopRequest.getShopName());
        shopEntity.setDescription(shopRequest.getDescription());
        shopEntity.setShopEmail(shopRequest.getShopEmail());
        shopEntity.setShopPhonumber(shopRequest.getShopPhonumber());
        shopEntity.setShopImage(shopRequest.getShopImage());
        shopEntity.setBackSideOfCCCD(shopRequest.getBackSideOfCCCD());
        shopEntity.setFrontSideOfCCCD(shopRequest.getFrontSideOfCCCD());
        shopEntity.setCccdNumber(shopRequest.getCccdNumber());
        shopEntity.setIndustry(shopRequest.getIndustry());
        shopEntity.setDateCreate(shopRequest.getDateCreate());
        shopEntity.setShippingAddress(shopRequest.getShippingAddress());
        shopEntity.setShopAddress(shopRequest.getShopAddress());
        shopEntity.setShopOwner(shopOwner);
        shopEntity.setTypeShop(typeShop);

        ShopEntity savedShop = shopRepository.save(shopEntity);

        return mapToResponse(savedShop);
    }

    @Override
    public Optional<ShopResponse> getShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .map(this::mapToResponse);
    }

    @Override
    public Page<ShopResponse> getAllShops(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShopEntity> shopPage = shopRepository.findAll(pageable);

        List<ShopResponse> shopResponses = shopPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(shopResponses, pageable, shopPage.getTotalElements());
    }

    @Override
    public ShopResponse updateShop(Long shopId, ShopRequestDTO shopRequest) {
        ShopEntity shopEntity = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        UserEntity shopOwner = userRepository.findById(shopRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CategoryEntity typeShop = categoryRepository.findById(shopRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        shopEntity.setShopName(shopRequest.getShopName());
        shopEntity.setDescription(shopRequest.getDescription());
        shopEntity.setShopEmail(shopRequest.getShopEmail());
        shopEntity.setShopPhonumber(shopRequest.getShopPhonumber());
        shopEntity.setShopImage(shopRequest.getShopImage());
        shopEntity.setBackSideOfCCCD(shopRequest.getBackSideOfCCCD());
        shopEntity.setFrontSideOfCCCD(shopRequest.getFrontSideOfCCCD());
        shopEntity.setCccdNumber(shopRequest.getCccdNumber());
        shopEntity.setIndustry(shopRequest.getIndustry());
        shopEntity.setDateCreate(shopRequest.getDateCreate());
        shopEntity.setShippingAddress(shopRequest.getShippingAddress());
        shopEntity.setShopAddress(shopRequest.getShopAddress());
        shopEntity.setShopOwner(shopOwner);
        shopEntity.setTypeShop(typeShop);

        ShopEntity updatedShop = shopRepository.save(shopEntity);

        return mapToResponse(updatedShop);
    }

    @Override
    public ShopResponse deleteShop(Long shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new RuntimeException("Shop not found");
        }
        shopRepository.deleteById(shopId);
        return mapToResponse(shopRepository.getOne(shopId));
    }

    private ShopResponse mapToResponse(ShopEntity shopEntity) {
        ShopResponse shopResponse = new ShopResponse();
        shopResponse.setShopId(shopEntity.getShopId());
        shopResponse.setShopName(shopEntity.getShopName());
        shopResponse.setDescription(shopEntity.getDescription());
        shopResponse.setShopEmail(shopEntity.getShopEmail());
        shopResponse.setShopPhonumber(shopEntity.getShopPhonumber());
        shopResponse.setShopImage(shopEntity.getShopImage());
        shopResponse.setBackSideOfCCCD(shopEntity.getBackSideOfCCCD());
        shopResponse.setFrontSideOfCCCD(shopEntity.getFrontSideOfCCCD());
        shopResponse.setCccdNumber(shopEntity.getCccdNumber());
        shopResponse.setIndustry(shopEntity.getIndustry());
        shopResponse.setDateCreate(shopEntity.getDateCreate());
        shopResponse.setShippingAddress(shopEntity.getShippingAddress());
        shopResponse.setShopAddress(shopEntity.getShopAddress());
        shopResponse.setOwnerName(shopEntity.getShopOwner().getUsername()); // Example of getting owner name
        shopResponse.setCategoryName(shopEntity.getTypeShop().getCategoryName()); // Example of getting category name

        return shopResponse;
    }
}
