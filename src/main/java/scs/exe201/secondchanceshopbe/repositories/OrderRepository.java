package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT c FROM OrderEntity c WHERE c.userOrder.userId = :userId")
    List<OrderEntity> findByUserOrder(long userId);
}
