package com.algaworks.algafood.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Mensagens;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CadastroEstadoService {

    private final EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    public Estado buscar(Long id) {
        Estado estado = estadoRepository.buscar(id);
        if (Objects.isNull(estado)) {
            throw new EntidadeNaoEncontradaException(
                String.format(Mensagens.MSGE001, "Estado", id));
        }
        return estado;
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public Estado atualizar(Estado estado) {
        Estado estadoAtual = estadoRepository.buscar(estado.getId());
        if (Objects.isNull(estadoAtual)) {
            throw new EntidadeNaoEncontradaException(
                String.format(Mensagens.MSGE001, "Estado", estado.getId()));
        }
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoRepository.salvar(estadoAtual);
    }

    public void remover(Long id) {
        try {
            estadoRepository.remover(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE001, "Estado", id));
        } catch(DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE002, id));
        }
    }
}
