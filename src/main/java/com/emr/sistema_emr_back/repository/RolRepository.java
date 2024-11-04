package com.emr.sistema_emr_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emr.sistema_emr_back.entity.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
