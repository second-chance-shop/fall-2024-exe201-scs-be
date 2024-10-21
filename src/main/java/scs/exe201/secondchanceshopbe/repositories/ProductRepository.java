package scs.exe201.secondchanceshopbe.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT c FROM ProductEntity c WHERE c.status = 'true' ")
    Page<ProductEntity> getAll(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity p SET p.start = " +
            "(SELECT COALESCE(AVG(r.star), 0) FROM RatingEntity r WHERE r.product.productId = p.productId)")
    void updateAllRating();
}
