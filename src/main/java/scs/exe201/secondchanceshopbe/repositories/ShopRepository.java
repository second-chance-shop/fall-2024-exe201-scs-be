package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;


import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    boolean existsByShopEmail(String email);
    boolean existsByShopPhoneNumber(String phone);

    @Query("SELECT c FROM ShopEntity c WHERE c.shopOwner.userId = :userId")
    List<ShopEntity> findByUserId(long userId);


    @Modifying
    @Query("UPDATE ShopEntity s SET s.valueFollow = (SELECT COALESCE(COUNT(f),0.0)  FROM FollowShopEntity f WHERE f.shopFollow = s AND f.status = 'FOLLOW')")
    void updateValueFollow();

    @Query("SELECT s FROM ShopEntity s WHERE s.shopOwner.userId = :userId AND s.status = 'ACTIVE'")
    List<ShopEntity> findActiveShopsByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM ShopEntity s WHERE s.shopId = :shopId AND s.shopOwner.userId = :userId AND s.status = 'ACTIVE'")
    Optional<ShopEntity> findByShopIdAndUserID(@Param("shopId") long shopId, @Param("userId") long userId);



}
