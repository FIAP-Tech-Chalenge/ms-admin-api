package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msadminapi.domain.output.produto.DeletaProdutoOutput;

import java.util.HashMap;
import java.util.Map;

public class DeleteProdutoPresenter implements PresenterInterface {
    private final DeletaProdutoOutput deleteProdutoOutput;

    public DeleteProdutoPresenter(DeletaProdutoOutput deleteProdutoOutput) {
        this.deleteProdutoOutput = deleteProdutoOutput;
    }

    public Map<String, Object> toArray() {
        return new HashMap<>();
    }

    public DeletaProdutoOutput getOutput() {
        return this.deleteProdutoOutput;
    }
}