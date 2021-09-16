package com.algaworks.algafood.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ExceptionBadRequest;
import com.algaworks.algafood.domain.model.Mensagens;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CadastroRestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if (Objects.isNull(cozinha)) {
            throw new EntidadeNaoEncontradaException(
                String.format(Mensagens.MSGE001, "Cozinha", cozinhaId));
        }
        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    public Restaurante buscar(Long id) {
        Restaurante restaurante = restauranteRepository.buscar(id);
        if(Objects.isNull(restaurante)) {
            throw new EntidadeNaoEncontradaException(
                String.format(Mensagens.MSGE001, "Restaurante", id));
        }
        return restaurante;
    }

    public Restaurante atualizar(Restaurante restaurante) {
        Restaurante restauranteAtual = buscar(restaurante.getId());
        if (Objects.isNull(restauranteAtual)) {
            throw new ExceptionBadRequest(
                String.format(Mensagens.MSGE001, "Restaurante", restaurante.getId()));
        }

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
        return restauranteRepository.salvar(restauranteAtual);
    }

    public void remover (Long id) {
        try {
            restauranteRepository.remover(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE001, "Restaurante", id));
        } catch(DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(Mensagens.MSGE002, id));
        }
    }
}
