package com.fiap.msadminapi.infra.model;

import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="pedidos")
public class PedidoModel {
    @Id
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;
    @Column(name = "numeropedido")
    private Long numeroPedido;
    @Column(name = "clienteid")
    private UUID clienteId;
    @Column(name = "datacriacao")
    private Date dataCriacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "statuspedido")
    private StatusPedido statusPedido;
    @Enumerated(EnumType.STRING)
    @Column(name = "statuspagamento")
    private StatusPagamento statusPagamento;
    @Column(name = "valortotal")
    private Float valorTotal;

}

