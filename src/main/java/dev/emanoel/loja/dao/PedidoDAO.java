package dev.emanoel.loja.dao;

import dev.emanoel.loja.model.Pedido;
import dev.emanoel.loja.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Pedido pedido ) {
        this.entityManager.persist(pedido);
    }

}
