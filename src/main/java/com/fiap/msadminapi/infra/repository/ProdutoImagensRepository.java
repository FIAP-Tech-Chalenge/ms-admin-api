package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.infra.model.ProdutoImagemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdutoImagensRepository extends JpaRepository<ProdutoImagemModel, Long> {
}
