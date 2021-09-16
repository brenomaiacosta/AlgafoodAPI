package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Mensagens;
import com.algaworks.algafood.domain.repository.CidadeRepository;
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
public class CadastroCidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    public Cidade buscar(Long id) {
        Cidade cidade = cidadeRepository.buscar(id);
        if (Objects.isNull(cidade)) {
            throw new EntidadeNaoEncontradaException(
                    String.format(Mensagens.MSGE001, "Cidade", id));
        }
        return cidade;
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

    public Cidade atualizar(Cidade cidade) {
        Cidade cidadeAtual = cidadeRepository.buscar(cidade.getId());
        Estado estado = estadoRepository.buscar(cidade.getEstado().getId());

        /* Aplicar Design Pattern Strategy */
        if (Objects.isNull(cidadeAtual)) {
            throw new EntidadeNaoEncontradaException(
                    String.format(Mensagens.MSGE001, "Cidade", cidade.getId()));
        }
        if (Objects.isNull(estado)) {
            throw new EntidadeNaoEncontradaException(
                    String.format(Mensagens.MSGE001, "Estado", cidade.getEstado().getId()));
        }

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cidadeRepository.salvar(cidadeAtual);
    }

    public void remover(Long id) {
        try {
            cidadeRepository.remover(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format(Mensagens.MSGE001, "Cidade", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(Mensagens.MSGE002, id));
        }
    }
}
