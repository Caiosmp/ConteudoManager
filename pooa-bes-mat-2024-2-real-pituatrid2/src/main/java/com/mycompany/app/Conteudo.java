package com.mycompany.app;

public class Conteudo {
    private Integer id;
    private String nome;
    private String descricao; 
    private Pessoa autor; 

    public Conteudo() {
    }
    
    public Conteudo(Integer id, String nome, String descricao, Pessoa autor) {
    	this.id = id;
    	this.nome = nome;
    	this.descricao = descricao;
    	this.autor = autor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Pessoa getAutor() {
        return autor;
    }

    public void setAutor(Pessoa autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Conteúdo [ID = " + id + ", Título = " + nome + ", Autor = " + autor.getNome() + ", Descrição = " + descricao + "]";
    }
}
