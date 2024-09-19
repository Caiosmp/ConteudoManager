package com.mycompany.app;

public class Pessoa {
    private String nome;
    private String senha;
    
    public Pessoa() {
    }
    
    public Pessoa(String nome, String senha) {
    	this.nome = nome;
    	this.senha = senha;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome.toLowerCase();
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Usuario [nome=").append(nome).append(", senha= ********").append("]");
		return sb.toString(); 
    }
}
