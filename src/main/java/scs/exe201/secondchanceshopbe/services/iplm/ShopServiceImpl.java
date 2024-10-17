package scs.exe201.secondchanceshopbe.services.iplm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;
import scs.exe201.secondchanceshopbe.models.entities.CategoryEntity;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;
import scs.exe201.secondchanceshopbe.models.entities.UserEntity;
import scs.exe201.secondchanceshopbe.models.exception.NotFoundException;
import scs.exe201.secondchanceshopbe.repositories.CategoryRepository;
import scs.exe201.secondchanceshopbe.repositories.ShopRepository;
import scs.exe201.secondchanceshopbe.repositories.UserRepository;
import scs.exe201.secondchanceshopbe.services.FileDatabaseService;
import scs.exe201.secondchanceshopbe.services.ShopService;
import scs.exe201.secondchanceshopbe.utils.EntityToDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;


    private final UserRepository userRepository;


    private final CategoryRepository categoryRepository;

    private final FileDatabaseService fileDatabaseService;



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
    public ShopResponse deleteShop(Long shopId) {
        ShopEntity shopEntity = shopRepository.findById(shopId).orElseThrow(
                () -> new NotFoundException("Shop not found"));
        shopEntity.setStatus(StatusEnum.DELETED);
        shopRepository.save(shopEntity);
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }


    @Override
    public ShopResponse updatev1(ShopUpdateDTO shopUpdateDTO, MultipartFile files) {
        ShopEntity shopEntity = shopRepository.findById(shopUpdateDTO.getId()).orElseThrow(
                () -> new NotFoundException("Shop not found")
        );
        shopEntity.setShopName(shopUpdateDTO.getShopName());
        shopEntity.setDescription(shopUpdateDTO.getDescription());
        shopEntity.setShopEmail(shopUpdateDTO.getShopEmail());
        shopEntity.setShopPhonumber(shopUpdateDTO.getShopPhonumber());
        if(files!=null && !files.isEmpty()) {
            var image =fileDatabaseService.uploadFile(files);
            shopEntity.setShopImage(image.getUrl());
        }

        shopEntity.setShopAddress(shopUpdateDTO.getShopAddress());
        shopEntity.setShippingAddress(shopUpdateDTO.getShippingAddress()); // Bổ sung địa chỉ giao hàng
        shopEntity.setIndustry(shopUpdateDTO.getIndustry()); // Bổ sung ngành nghề
        CategoryEntity categoryEntity = categoryRepository.findById(shopUpdateDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        shopEntity.setTypeShop(categoryEntity);
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }

    @Override
    public ShopResponse addV1(ShopCreateDTO shopCreateDTO, MultipartFile imageShop, MultipartFile cccdFont, MultipartFile cccdBack) {
        ShopEntity shopEntity = new ShopEntity();
        if(shopRepository.existsByShopEmail(shopCreateDTO.getShopEmail())) {
            throw new NotFoundException("Shop email already exists");
        }
        if (shopRepository.existsByShopPhonumber(shopCreateDTO.getShopPhonumber())) {
            throw new NotFoundException("Shop phone already exists");
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity= userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        CategoryEntity categoryEntity = categoryRepository.findById(shopCreateDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
        shopEntity.setShopName(shopCreateDTO.getShopName());
        shopEntity.setDescription(shopCreateDTO.getDescription());
        shopEntity.setShopEmail(shopCreateDTO.getShopEmail());
        shopEntity.setShopPhonumber(shopCreateDTO.getShopPhonumber());
        shopEntity.setShopAddress(shopCreateDTO.getShopAddress());
        shopEntity.setShippingAddress(shopCreateDTO.getShippingAddress());
        shopEntity.setCccdNumber(shopCreateDTO.getCccdNumber());
        shopEntity.setIndustry(shopCreateDTO.getIndustry());
        shopEntity.setShopOwner(userEntity);
        shopEntity.setTypeShop(categoryEntity);
        shopEntity.setDateCreate(LocalDate.now());
        if(imageShop !=null && !imageShop.isEmpty()) {
          var image=  fileDatabaseService.uploadFile(imageShop);
          shopEntity.setShopImage(image.getUrl());
        }
        if(cccdFont!=null && !cccdFont.isEmpty()) {
            var image=  fileDatabaseService.uploadFile(cccdFont);
            shopEntity.setFrontSideOfCCCD(image.getUrl());
        }
        if(cccdBack!=null && !cccdBack.isEmpty()) {
            var image=  fileDatabaseService.uploadFile(cccdBack);
            shopEntity.setBackSideOfCCCD(image.getUrl());
        }
        shopRepository.save(shopEntity);
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }

    @Override
    public ShopResponse getByUserId(Long id) {
        ShopEntity shopEntity = shopRepository.findByUserId(id).orElseThrow(
                () -> new NotFoundException("Shop not found")
        );
        return EntityToDTO.shopEntityTODTO(shopEntity);
    }
}
