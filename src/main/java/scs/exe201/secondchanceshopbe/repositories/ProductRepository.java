package scs.exe201.secondchanceshopbe.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT c FROM ProductEntity c WHERE c.status = 'true' ")
    Page<ProductEntity> getAll(PageRequest pageable);
    @Query("SELECT p FROM ProductEntity p WHERE " +
           "LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<ProductEntity> searchByNameOrDescription(@Param("search") String search, Pageable pageable);
    
    
}
