package com.fiap.msadminapi.application.response;

import com.fiap.msadminapi.domain.generic.presenter.PresenterInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PresenterResponse {
    public ResponseEntity<Object> response(PresenterInterface presenterInterface) {
        
        if (presenterInterface.getOutput().getOutputStatus().getCode() == 200) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Security-Policy", "script-src 'self'")
                    .header("Access-Control-Allow-Origin", "https://restaurante.com.br")
                    .header("X-Frame-Options", "SAMEORIGIN")
                    .body(presenterInterface.toArray());
        }

        if (presenterInterface.getOutput().getOutputStatus().getCode() == 201) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Security-Policy", "script-src 'self'")
                    .header("Access-Control-Allow-Origin", "https://restaurante.com.br")
                    .header("X-Frame-Options", "SAMEORIGIN")
                    .body(presenterInterface.toArray());
        }

        if (presenterInterface.getOutput().getOutputStatus().getCode() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(presenterInterface.toArray());
        }

        if (presenterInterface.getOutput().getOutputStatus().getCode() == 204) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Content-Security-Policy", "script-src 'self'")
                    .header("Access-Control-Allow-Origin", "https://restaurante.com.br")
                    .header("X-Frame-Options", "SAMEORIGIN")
                    .body(presenterInterface.toArray());
        }

        if (presenterInterface.getOutput().getOutputStatus().getCode() == 422) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(presenterInterface.toArray());
        }

        if (presenterInterface.getOutput().getOutputStatus().getCode() == 400) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(presenterInterface.toArray());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(presenterInterface.toArray());
    }
}
