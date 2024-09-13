package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scs.exe201.secondchanceshopbe.models.entities.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
