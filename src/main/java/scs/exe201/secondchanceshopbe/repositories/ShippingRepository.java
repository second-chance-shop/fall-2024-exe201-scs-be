package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import scs.exe201.secondchanceshopbe.models.entities.ShippingEntity;

public interface ShippingRepository extends JpaRepository<ShippingEntity,Long>{
    
}
