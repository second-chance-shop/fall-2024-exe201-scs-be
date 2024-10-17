package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;


import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    boolean existsByShopEmail(String email);
    boolean existsByShopPhonumber(String phone);

    @Query("SELECT c FROM ShopEntity c WHERE c.shopOwner.userId = :userId")
    Optional<ShopEntity> findByUserId(long userId);

}
