package com.algaworks.algafood.repository;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ExceptionBadRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class CozinhaRepositoryImpl  implements CozinhaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = buscar(id);
        if (Objects.isNull(cozinha)) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(buscar(id));
    }

    @Override
    public Cozinha buscar(Long id) {
        if (Objects.isNull(id)) {
            throw new EntidadeNaoEncontradaException("Atributo id do objeto cozinha não pode ser nulo!");
        }
        return entityManager.find(Cozinha.class, id);
    }

    @Override
    public List<Cozinha> listar() {
        return entityManager.createQuery("select c from Cozinha c", Cozinha.class)
                .getResultList();
    }
}
