package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    ProdutoModel findByUuid(UUID uuid);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    void delete(ProdutoModel entity);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    List<ProdutoModel> findAll();

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    ProdutoModel save(ProdutoModel produtoModel);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    List<ProdutoModel> findByCategoria(CategoriaEnum categoria);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    @Query("SELECT p FROM ProdutoModel p LEFT JOIN FETCH p.imagens WHERE p.uuid = :uuid")
    Optional<ProdutoModel> findByUuidWithImages(@Param("uuid") UUID uuid);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    @Query("SELECT DISTINCT p FROM ProdutoModel p LEFT JOIN FETCH p.imagens")
    List<ProdutoModel> findAllWithImages();
}
