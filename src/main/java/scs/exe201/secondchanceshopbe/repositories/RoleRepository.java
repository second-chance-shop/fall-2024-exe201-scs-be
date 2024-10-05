package scs.exe201.secondchanceshopbe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scs.exe201.secondchanceshopbe.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "SELECT r from RoleEntity r where r.roleName='USER'")
    RoleEntity getRoleCustomer();

}
