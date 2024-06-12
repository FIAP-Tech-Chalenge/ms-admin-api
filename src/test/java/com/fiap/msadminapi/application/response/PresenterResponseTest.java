package com.fiap.msadminapi.application.response;

import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.pedido.BuscaPedidoOutput;
import com.fiap.msadminapi.domain.output.pedido.BuscaTodosPedidoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.pedido.GetPedidoPresenter;
import com.fiap.msadminapi.domain.presenters.cliente.pedido.GetPedidosPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PresenterResponseTest {

    PresenterResponse presenterResponse;

    OutputStatus outputStatus;

    @BeforeEach
    void setup() {
        presenterResponse = new PresenterResponse();
    }

    @Test
    void deveRetornarStatusOk() {
        outputStatus = new OutputStatus(200, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deveRetornarStatusCreated() {
        outputStatus = new OutputStatus(201, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deveRetornarStatusNotFound() {
        outputStatus = new OutputStatus(404, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deveRetornarStatusNoContent() {
        outputStatus = new OutputStatus(204, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deveRetornarStatusUnprocessableEntity() {
        outputStatus = new OutputStatus(422, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void deveRetornarStatusUnprocessableNotFound400() {
        outputStatus = new OutputStatus(400, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deveRetornarStatusServerError() {
        outputStatus = new OutputStatus(500, "", "");
        var buscaPedidoOutput = new BuscaTodosPedidoOutput(List.of(), outputStatus);
        var presenter = new GetPedidosPresenter(buscaPedidoOutput);
        var response = new PresenterResponse().response(presenter);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
