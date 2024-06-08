package com.fiap.msadminapi.infra.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteModelTest {

    @Test
    void deveCriarUmModeloDeClienteComDadosValidos() {
        var uuid = UUID.randomUUID();
        var nome = "Cliente 1";
        var cpf = "88256176660";
        var email = "cliente1@email.com";

        var model = new ClienteModel(
                nome,
                cpf,
                email,
                uuid
        );

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNome()).isEqualTo(nome);
            assertThat(m.getCpf()).isEqualTo(cpf);
            assertThat(m.getEmail()).isEqualTo(email);
        });
    }

    @Test
    void devePermitirAlterarUmModeloDeClienteComDadosValidos() {
        var uuid = UUID.randomUUID();
        var nome = "Cliente 1";
        var cpf = "88256176660";
        var email = "cliente1@email.com";

        var model = new ClienteModel(
                "Cliente 2",
                "88475165460",
                "cliente2@email.com",
                UUID.randomUUID()
        );

        model.setUuid(uuid);
        model.setNome(nome);
        model.setCpf(cpf);
        model.setEmail(email);

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNome()).isEqualTo(nome);
            assertThat(m.getCpf()).isEqualTo(cpf);
            assertThat(m.getEmail()).isEqualTo(email);
        });
    }

    @Test
    void devePermitirCriarUmModeloDeClienteComDadosValidosSemArgumentosNoConstructor() {
        var uuid = UUID.randomUUID();
        var nome = "Cliente 1";
        var cpf = "88256176660";
        var email = "cliente1@email.com";

        var model = new ClienteModel();

        model.setUuid(uuid);
        model.setNome(nome);
        model.setCpf(cpf);
        model.setEmail(email);

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNome()).isEqualTo(nome);
            assertThat(m.getCpf()).isEqualTo(cpf);
            assertThat(m.getEmail()).isEqualTo(email);
        });
    }

}
