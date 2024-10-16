package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.ShopEntity;
@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    boolean existsByshopEmail(String email);
    boolean existsByshopPhonumber(String phone);
}
