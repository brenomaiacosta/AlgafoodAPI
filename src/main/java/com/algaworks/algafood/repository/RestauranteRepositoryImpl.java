package com.algaworks.algafood.repository;

import com.algaworks.algafood.domain.exception.ExceptionBadRequest;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Restaurante restaurante = buscar(id);
        if (Objects.isNull(restaurante)) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(restaurante);
    }

    @Override
    public Restaurante buscar(Long id) {
        if (Objects.isNull(id)) {
            throw new ExceptionBadRequest(
                    "Atributo id do objeto restaurante não pode ser nulo!");
        }
        return entityManager.find(Restaurante.class, id);
    }

    @Override
    public List<Restaurante> listar() {
        return entityManager.createQuery("select r from Restaurante r", Restaurante.class)
                .getResultList();
    }
}
