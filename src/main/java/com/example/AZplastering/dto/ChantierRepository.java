package com.example.AZplastering.dto;

import com.example.AZplastering.model.Chantier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChantierRepository extends JpaRepository<Chantier, Long> {

}
