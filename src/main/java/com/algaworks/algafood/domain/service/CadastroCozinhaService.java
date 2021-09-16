package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ExceptionBadRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Mensagens;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CadastroCozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public void remover(Long id) {
        try {
            cozinhaRepository.remover(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE001, "Cozinha", id));
        } catch(DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE002, id));
        }
    }

    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    public Cozinha buscar(Long cozinhaId) {
        return cozinhaRepository.buscar(cozinhaId);
    }

    public Cozinha atualizar(Cozinha cozinha) {
        Cozinha cozinhaAtual = buscar(cozinha.getId());
        if (Objects.isNull(cozinhaAtual)) {
            throw new ExceptionBadRequest(
                String.format("Não existe um cadastro de cozinha com o código %d", cozinha.getId()));
        }
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return salvar(cozinhaAtual);
    }
}
