package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.FollowShopEntity;

@Repository
public interface FollowShopRepository extends JpaRepository<FollowShopEntity , Long> {
    @Query("SELECT f FROM FollowShopEntity f WHERE f.userFollow.userId = :userId AND f.shopFollow.shopId = :shopId")
    FollowShopEntity findByUserIdAndShopId(@Param("userId") Long userId, @Param("shopId") Long shopId);
}
