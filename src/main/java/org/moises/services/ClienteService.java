package org.moises.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.moises.dto.ClienteDTO;
import org.moises.models.Cliente;
import org.moises.repositories.ClienteRepository;

import io.netty.util.internal.StringUtil;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    ObjectMapper mapper;

    public ClienteService() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public List<ClienteDTO> listPaginated(Integer pageNumber, Integer pageSize) {
        List<Cliente> entities;
        
        entities = clienteRepository.listPaginated(pageNumber, pageSize);
        
        List<ClienteDTO> listaClienteDTO = new ArrayList<>();

        for (Cliente entity : entities) {
            listaClienteDTO.add(mapper.convertValue(entity, ClienteDTO.class));
        }

        return listaClienteDTO;
    }

    public ClienteDTO getById(Integer id) {
        Cliente entity = clienteRepository.getById(id);
        if (entity == null)
            return null;
        return mapper.convertValue(entity, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO create(ClienteDTO clienteDTO) {
        if (clienteDTO.getId() != null) {
            clienteDTO.addErro("Id inválido para a requisição.");
            return clienteDTO;
        }

        if (StringUtil.isNullOrEmpty(clienteDTO.getNome())) {
            clienteDTO.addErro("Nome do cliente é um campo obrigatório.");
            return clienteDTO;
        }

        Cliente entity = mapper.convertValue(clienteDTO, Cliente.class);

        clienteRepository.persist(entity);

        return mapper.convertValue(entity, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO update(Integer id, ClienteDTO clienteDTO) {
        if (StringUtil.isNullOrEmpty(clienteDTO.getNome())) {
            clienteDTO.addErro("Nome do cliente é um campo obrigatório.");
            return clienteDTO;
        }

        Cliente entity = clienteRepository.getById(id);

        if (entity == null) {
            clienteDTO.addErro("Cliente com id " + id + " não existe.");
            return clienteDTO;
        }

        entity.setNome(clienteDTO.getNome());
        return mapper.convertValue(entity, ClienteDTO.class);
    }

    @Transactional
    public ClienteDTO delete(Integer id) {
        Cliente entity = clienteRepository.getById(id);
        if (entity == null)
            return null;
        clienteRepository.remove(entity);
        return mapper.convertValue(entity, ClienteDTO.class);
    }
}
