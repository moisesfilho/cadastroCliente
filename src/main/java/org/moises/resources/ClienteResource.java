package org.moises.resources;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.moises.models.Cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("clientes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ClienteResource {

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());

    @Inject
    EntityManager entityManager;

    @GET
    public List<Cliente> get() {
        return entityManager.createNamedQuery("Cliente.findAll", Cliente.class)
                .getResultList();
    }

    @GET
    @Path("{id}")
    public Cliente getSingle(Integer id) {
        Cliente entity = entityManager.find(Cliente.class, id);
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

        entityManager.persist(cliente);
        return Response.ok(cliente).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Cliente update(Integer id, Cliente cliente) {
        if (cliente.getNome() == null) {
            throw new WebApplicationException("Nome do cliente não foi definido para a requisição", 422);
        }

        Cliente entity = entityManager.find(Cliente.class, id);

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
        Cliente entity = entityManager.getReference(Cliente.class, id);
        if (entity == null) {
            throw new WebApplicationException("Cliente com id " + id + " não existe", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Falha na requisição", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
