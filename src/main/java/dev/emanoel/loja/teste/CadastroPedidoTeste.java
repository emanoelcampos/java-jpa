package dev.emanoel.loja.teste;

import dev.emanoel.loja.dao.CategoriaDAO;
import dev.emanoel.loja.dao.ClienteDAO;
import dev.emanoel.loja.dao.PedidoDAO;
import dev.emanoel.loja.dao.ProdutoDAO;
import dev.emanoel.loja.model.*;
import dev.emanoel.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CadastroPedidoTeste {

    public static void main(String[] args) {

        popularBancoDeDados();

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);
        Produto produto = produtoDAO.buscarPorID(1L);
        Cliente cliente = clienteDAO.buscarPorId(1L);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(4, pedido, produto));

        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);
        pedidoDAO.cadastrar(pedido);

        entityManager.getTransaction().commit();

    }

    private static void popularBancoDeDados() {
        Categoria categoria = new Categoria();
        categoria.setNome("eletronicos");

        Produto produto = new Produto();
        produto.setNome("fone");
        produto.setDescricao("fone kz pro sem mic");
        produto.setPreco(new BigDecimal("150"));
        produto.setCategoria(categoria);

        Cliente cliente = new Cliente("emanoel", "123456789");

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);

        ClienteDAO clienteDAO = new ClienteDAO(entityManager);

        entityManager.getTransaction().begin();

        categoriaDAO.cadastrar(categoria);
        produtoDAO.cadastrar(produto);
        clienteDAO.cadastrar(cliente);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
