package com.fiap.msadminapi.infra.adpter.repository.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteEntityRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteEntityRepository entityRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        entityRepository = new ClienteEntityRepository(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarClientes() {
        Cliente cliente = new Cliente("Cliente 1", "88256176660", "cliente1@email.com", UUID.fromString("74d8c418-4001-7004-a6a8-8265b4a2c496"));

        ClienteModel clienteModel = new ClienteModel("Cliente 1", "88256176660", "cliente1@email.com", UUID.fromString("74d8c418-4001-7004-a6a8-8265b4a2c496"));
        var listaClientesModel = List.of(clienteModel);

        when(clienteRepository.findAll()).thenReturn(listaClientesModel);

        var listaRecebidaDeClientes = entityRepository.buscarClientes();

        assertThat(listaRecebidaDeClientes)
                .hasSize(1)
                .satisfies(c -> {
                    var cliente1 = c.getFirst();
                    assertThat(cliente1.getNome()).isEqualTo(cliente.getNome());
                    assertThat(cliente1.getEmail()).isEqualTo(cliente.getEmail());
                    assertThat(cliente1.getCpf()).isEqualTo(cliente.getCpf());
                    assertThat(cliente1.getUuid()).isEqualTo(cliente.getUuid());
                });
    }

    @Test
    void devePermitirBuscarClientePorUuid() {
        var uuid = UUID.fromString("74d8c418-4001-7004-a6a8-8265b4a2c496");
        Cliente cliente = new Cliente("Cliente 1", "88256176660", "cliente1@email.com", uuid);
        ClienteModel clienteModel = new ClienteModel("Cliente 1", "88256176660", "cliente1@email.com", uuid);

        when(clienteRepository.findByUuid(uuid)).thenReturn(clienteModel);

        var clienteEncontrado = entityRepository.getClienteByUuid(uuid);

        assertThat(clienteEncontrado)
                .satisfies(c -> {
                    assertThat(c.getNome()).isEqualTo(cliente.getNome());
                    assertThat(c.getEmail()).isEqualTo(cliente.getEmail());
                    assertThat(c.getCpf()).isEqualTo(cliente.getCpf());
                    assertThat(c.getUuid()).isEqualTo(cliente.getUuid());
                });
    }

    @Test
    void deveRetornarNulo_QuandoBuscarClientePorUuid_ClienteNaoEncontrado() {
        when(clienteRepository.findByUuid(any(UUID.class))).thenReturn(null);

        var clienteEncontrado = entityRepository.getClienteByUuid(UUID.randomUUID());

        assertThat(clienteEncontrado).isNull();
    }

}
