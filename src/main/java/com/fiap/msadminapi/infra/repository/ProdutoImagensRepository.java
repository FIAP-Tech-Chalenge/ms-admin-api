package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.infra.model.ImagemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoImagensRepository extends JpaRepository<ImagemModel, Long> {
}
