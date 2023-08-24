package dev.emanoel.loja.dao;

import dev.emanoel.loja.model.Pedido;
import dev.emanoel.loja.model.Produto;
import dev.emanoel.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoDAO {

    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Pedido pedido ) {
        this.entityManager.persist(pedido);
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }


    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "SELECT new dev.emanoel.loja.vo.RelatorioDeVendasVo" +
                "(produto.nome, " +
                "SUM(item.quantidade), " +
                "MAX(pedido.data)) " +
                "FROM Pedido pedido " +
                "JOIN pedido.itens item " +
                "JOIN item.produto produto " +
                "GROUP BY produto.nome " +
                "ORDER BY item.quantidade DESC";
        return entityManager.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        return entityManager.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate  dataCadastro) {
        String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
        if(nome != null && !nome.trim().isEmpty()) {
            jpql = " AND p.nome = :nome ";
        }
        if(preco != null) {
            jpql = " AND p.preco = :preco ";
        }
        if(dataCadastro != null) {
            jpql = " AND p.dataCadastro = :dataCadastro ";
        }

        TypedQuery<Produto> query = entityManager.createQuery(jpql, Produto.class);
        if(nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if(preco != null) {
            query.setParameter("preco", preco);
        }
        if(dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosComCriteriaAPI(String nome, BigDecimal preco, LocalDate  dataCadastro) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = criteriaBuilder.and();
        if(nome != null && !nome.trim().isEmpty()) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("nome"), nome));
        }
        if(preco != null) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("preco"), preco));
        }
        if(dataCadastro != null) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("dataCadastro"), dataCadastro));
        }

        query.where(filtros);

        return entityManager.createQuery(query).getResultList();
    }

}
