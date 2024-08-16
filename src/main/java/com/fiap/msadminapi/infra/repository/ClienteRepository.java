package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.infra.model.ClienteModel;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    ClienteModel findByCpf(String cpf);

    @QueryHints({@QueryHint(name = "java.persistence.query.timeout", value = "2000")})
    ClienteModel findByUuid(UUID uuid);

}
