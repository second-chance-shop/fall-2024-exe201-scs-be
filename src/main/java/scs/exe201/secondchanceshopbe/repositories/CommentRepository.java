package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.product.productId = :productId")
    List<CommentEntity> findByProductId(long productId);
}
