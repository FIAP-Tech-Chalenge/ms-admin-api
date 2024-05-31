package com.fiap.msadminapi.domain.gateway.pagamento;

import com.fiap.msadminapi.domain.entity.pagamento.GatewayQrCode;

import java.util.UUID;

public interface PagamentoQrCodeInterface {
    GatewayQrCode geraQrCodePagamento(UUID uuid, Float valorTotal);
}
