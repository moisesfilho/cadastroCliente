package org.moises.repositories;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.moises.models.Cliente;

@ApplicationScoped
public class ClienteRepository {

  @Inject
  EntityManager entityManager;

  public List<Cliente> listPaginated(Integer pageNumber, Integer pageSize) {
    Query query = entityManager.createQuery("SELECT c FROM Cliente c ORDER BY c.nome", Cliente.class);
    if (pageSize > 0) {
      query.setFirstResult((pageNumber) * pageSize);
      query.setMaxResults(pageSize);
    }
    return query.getResultList();
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
