package com.fiap.msadminapi.infra.dependency;

import com.fiap.msadminapi.infra.dependecy.StringValidatorsAdapter;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValidatorsAdapterTest {

    @Test
    void deveRetornarTrueQuandoUUIDCorreto() {
        var uuid = UUID.randomUUID().toString();

        var response = StringValidatorsAdapter.isValidUUID(uuid);
        assertThat(response).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoUUIDENulo() {
        var response = StringValidatorsAdapter.isValidUUID(null);
        assertThat(response).isFalse();
    }

}
