package scs.exe201.secondchanceshopbe.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;

import java.util.Optional;

public interface ShopService
{
    Page<ShopEntity> getShops(int page, int size);
    ResponseEntity<Optional<ShopEntity>> getShopById(int id);
    ResponseEntity<ShopEntity> addShop(ShopEntity shop);
    ResponseEntity<ShopEntity> updateShop(ShopEntity shop);
    ResponseEntity<Void> deleteShopById(int id);
    ResponseEntity<Void> deleteAllShops();

}
