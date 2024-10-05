package scs.exe201.secondchanceshopbe.services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import scs.exe201.secondchanceshopbe.models.dtos.requests.ShopRequestDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.ShopResponse;

public interface ShopService {

    ShopResponse createShop(ShopRequestDTO shopRequest);

    Optional<ShopResponse> getShopById(Long shopId);

    Page<ShopResponse> getAllShops(int page, int size);

    ShopResponse updateShop(Long shopId, ShopRequestDTO shopRequest);

    ShopResponse deleteShop(Long shopId);
}

