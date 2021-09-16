package com.algaworks.algafood.domain.exception;

public class ExceptionBadRequest extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExceptionBadRequest(String mensagem){
        super(mensagem);
    }
}
