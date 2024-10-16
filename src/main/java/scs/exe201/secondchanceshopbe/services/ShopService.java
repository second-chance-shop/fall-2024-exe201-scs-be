package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ProductUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;

import java.util.List;

public interface ShopService {

    ShopResponse createShop(ShopRequestDTO shopRequest);

    ShopResponse getShopById(Long shopId);

    Page<ShopResponse> getAllShops(int page, int size);

    ShopResponse updateShop(Long shopId, ShopRequestDTO shopRequest);

    ShopResponse deleteShop(Long shopId);

    ShopResponse updatev1(ShopUpdateDTO shopUpdateDTO, MultipartFile files);


    ShopResponse addV1(ShopCreateDTO shopCreateDTO, MultipartFile imageShop, MultipartFile cccdFont, MultipartFile cccdBack);
}

