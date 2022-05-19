package org.moises.controllers;

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

import org.moises.dto.ClienteDTO;
import org.moises.services.ClienteService;

@Path("clientes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ClienteController {

  @Inject
  ClienteService clienteService;

  @GET
  public List<ClienteDTO> get() {
    return clienteService.listAll();
  }

  @GET
  @Path("{id}")
  public ClienteDTO getById(Integer id) {
    ClienteDTO entity = clienteService.getById(id);

    if (entity == null) {
      throw new WebApplicationException("Cliente com id " + id + " não existe.", 404);
    }

    return entity;
  }

  @POST
  @Transactional
  public Response create(ClienteDTO clienteDTO) {
    ClienteDTO clienteSalvo = clienteService.create(clienteDTO);

    if (!clienteDTO.getErros().isEmpty()) {
      throw new WebApplicationException(clienteDTO.getErrosString(), 404);
    }

    return Response.ok(clienteSalvo).status(201).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public ClienteDTO update(Integer id, ClienteDTO cliente) {
    ClienteDTO clienteDTO = clienteService.update(id, cliente);

    if (!clienteDTO.getErros().isEmpty()) {
      throw new WebApplicationException(clienteDTO.getErrosString(), 404);
    }

    return clienteDTO;
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public Response delete(Integer id) {
    ClienteDTO clienteDTO = clienteService.delete(id);

    if (clienteDTO == null) {
      throw new WebApplicationException("Cliente com id " + id + " não existe", 404);
    }

    return Response.status(204).build();
  }
}