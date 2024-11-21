package scs.exe201.secondchanceshopbe.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scs.exe201.secondchanceshopbe.models.dtos.enums.StatusEnum;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT c FROM ProductEntity c WHERE c.status = 'true' ")
    Page<ProductEntity> getAll(PageRequest pageable);

//    @Query("SELECT p FROM ProductEntity p WHERE p.status = :status")
    Page<ProductEntity> findAllByStatus(@Param("status") StatusEnum status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity p SET p.start = " +
            "(SELECT COALESCE(AVG(r.star), 0.0) FROM RatingEntity r WHERE r.product.productId = p.productId)")
    void updateAllRating();

    @Query("SELECT p FROM ProductEntity p WHERE p.shop.shopOwner.userId = :userId AND p.shop.shopId = :shopId AND p.status = 'ACTIVE'")
    List<ProductEntity> findAllProductActiveByUserIdAndShopId(@Param("userId") Long userId, @Param("shopId") Long shopId);

}
