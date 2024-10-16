package scs.exe201.secondchanceshopbe.services.iplm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ShopRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.ShopService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

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
                .orElseThrow(() -> new NotFoundException("User not found"));

        CategoryEntity typeShop = categoryRepository.findById(Long.valueOf(shopRequest.getCategoryId()))
                .orElseThrow(() -> new NotFoundException("Category not found"));

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

        return EntityToDTO.shopEntityTODTO(savedShop);
    }

    @Override
    public ShopResponse getShopById(Long shopId) {
        ShopEntity shopEntity = shopRepository.findById(shopId).orElseThrow(
                () -> new NotFoundException("Shop not found"));
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }

    @Override
    public Page<ShopResponse> getAllShops(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShopEntity> shopPage = shopRepository.findAll(pageable);

        var shopResponses =  shopPage.stream().map(EntityToDTO::shopEntityTODTO).toList();

        return new PageImpl<>(shopResponses, pageable, shopPage.getTotalElements());
    }

    @Override
    public ShopResponse updateShop(Long shopId, ShopRequestDTO shopRequest) {
        ShopEntity shopEntity = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop not found"));

        UserEntity shopOwner = userRepository.findById(shopRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        CategoryEntity typeShop = categoryRepository.findById(shopRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

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

        return EntityToDTO.shopEntityTODTO(updatedShop);
    }

    @Override
    public ShopResponse deleteShop(Long shopId) {
        ShopEntity shopEntity = shopRepository.findById(shopId).orElseThrow(
                () -> new NotFoundException("Shop not found"));
        shopEntity.setStatus(StatusEnum.DELETED);
        shopRepository.save(shopEntity);
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }


}