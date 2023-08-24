package dev.emanoel.loja.model;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @EmbeddedId
    private CategoriaId categoriaId;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.categoriaId = new CategoriaId(nome, "tipo");
    }

}
