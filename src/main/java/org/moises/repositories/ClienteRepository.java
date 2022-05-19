package org.moises.repositories;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.moises.models.Cliente;

@ApplicationScoped
public class ClienteRepository {

  @Inject
  EntityManager entityManager;

  public List<Cliente> listAll() {
    return entityManager.createNamedQuery("Cliente.findAll", Cliente.class)
        .getResultList();
  }

  public Cliente getById(Integer id) {
    return entityManager.find(Cliente.class, id);
  }

  public void persist(Cliente cliente) {
    entityManager.persist(cliente);
  }

  public void remove(Cliente cliente) {
    entityManager.remove(cliente);
  }
}
