package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {
    ProdutoModel findByUuid(UUID uuid);
    void delete(ProdutoModel entity);

    List<ProdutoModel> findAll();

    ProdutoModel save(ProdutoModel produtoModel);

    List<ProdutoModel> findByCategoria(CategoriaEnum categoria);

    @Query("SELECT p FROM ProdutoModel p LEFT JOIN FETCH p.imagens WHERE p.uuid = :uuid")
    Optional<ProdutoModel> findByUuidWithImages(@Param("uuid") UUID uuid);

    @Query("SELECT DISTINCT p FROM ProdutoModel p LEFT JOIN FETCH p.imagens")
    List<ProdutoModel> findAllWithImages();
}
