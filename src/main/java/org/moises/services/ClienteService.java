package org.moises.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.moises.models.Cliente;
import org.moises.repositories.ClienteRepository;

import io.netty.util.internal.StringUtil;

@Path("clientes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ClienteService {

    @Inject
    ClienteRepository repository;

    @GET
    public List<Cliente> get() {
        return repository.listAll();
    }

    @GET
    @Path("{id}")
    public Cliente getSingle(Integer id) {
        Cliente entity = repository.getById(id);
        if (entity == null) {
            throw new WebApplicationException("Cliente com id " + id + " não existe.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Cliente cliente) {
        if (cliente.getId() != null) {
            throw new WebApplicationException("Id inválido para a requisição.", 422);
        }

        if (StringUtil.isNullOrEmpty(cliente.getNome())) {
            throw new WebApplicationException("Nome do cliente é um campo obrigatório.", 422);
        }

        repository.persist(cliente);
        return Response.ok(cliente).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Cliente update(Integer id, Cliente cliente) {
        if (StringUtil.isNullOrEmpty(cliente.getNome())) {
            throw new WebApplicationException("Nome do cliente é um campo obrigatório.", 422);
        }

        Cliente entity = repository.getById(id);

        if (entity == null) {
            throw new WebApplicationException("Cliente com id " + id + " não existe.", 404);
        }

        entity.setNome(cliente.getNome());

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Integer id) {
        Cliente entity = repository.getById(id);
        if (entity == null) {
            throw new WebApplicationException("Cliente com id " + id + " não existe", 404);
        }
        repository.remove(entity);
        return Response.status(204).build();
    }
}
