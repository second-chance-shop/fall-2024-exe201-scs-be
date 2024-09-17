package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scs.exe201.secondchanceshopbe.models.dtos.requests.UpdateRatingDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.entities.CommentEntity;
import scs.exe201.secondchanceshopbe.models.entities.RatingEntity;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity,Long> {

    @Query("SELECT r FROM RatingEntity r WHERE r.product.productId = :productId AND r.userRating.userId = :userId")
    Optional<RatingEntity> findByProductIdAndUserId(Long productId, Long userId);

    @Query("SELECT c FROM RatingEntity c WHERE c.product.productId = :productId")
    List<RatingEntity> findByProductId(long productId);
}
