package com.fiap.msadminapi.infra.dependecy.jwt;

public interface JWTDecodeInterface {
    String claimClienteUuid(String token) throws Exception;
}
