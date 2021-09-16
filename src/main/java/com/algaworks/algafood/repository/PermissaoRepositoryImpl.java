package com.algaworks.algafood.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Permissao salvar(Permissao permissao) {
        return entityManager.merge(permissao);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        permissao = buscar(permissao.getId());
        entityManager.remove(permissao);
    }

    @Override
    public Permissao buscar(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Override
    public List<Permissao> listar() {
        return entityManager.createQuery("select p from Permissao p", Permissao.class)
                .getResultList();
    }
}
