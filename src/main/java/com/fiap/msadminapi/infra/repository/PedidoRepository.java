package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.infra.model.PedidoModel;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, UUID> {

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    PedidoModel findByUuid(UUID uuid);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    List<PedidoModel> findAll();

}
