package com.fiap.msadminapi.domain.entity.cliente;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteTest {

    @Test
    void devePermitirIncializarClienteSemArgumentos() {
        var uuid = UUID.randomUUID();
        var nome = "Cliente 1";
        var email = "cliente1@email.com";
        var cpf = "12345678912";
        Cliente cliente = new Cliente();
        cliente.setUuid(uuid);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCpf(cpf);

        assertThat(cliente.getUuid()).isEqualTo(uuid);
        assertThat(cliente.getNome()).isEqualTo(nome);
        assertThat(cliente.getEmail()).isEqualTo(email);
        assertThat(cliente.getCpf()).isEqualTo(cpf);
    }

}
