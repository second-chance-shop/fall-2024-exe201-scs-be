package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT c FROM OrderEntity c WHERE c.userOrder.userId = :userId")
    List<OrderEntity> findByUserOrder(long userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.userOrder.userId = :userId AND o.status = 'HAS_BUY'")
    List<OrderEntity> findByUserOrderAndStatusHasBuy(@Param("userId") Long userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.userOrder.userId = :userId AND o.status = 'CART'")
    List<OrderEntity> findByUserOrderAndStatusCart(@Param("userId") Long userId);

}
