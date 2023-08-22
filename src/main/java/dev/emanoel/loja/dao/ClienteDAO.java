package dev.emanoel.loja.dao;

import dev.emanoel.loja.model.Cliente;
import dev.emanoel.loja.model.Pedido;

import javax.persistence.EntityManager;

public class ClienteDAO {

    private EntityManager entityManager;

    public ClienteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Cliente cliente ) {
        this.entityManager.persist(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return entityManager.find(Cliente.class, id);
    }

}
