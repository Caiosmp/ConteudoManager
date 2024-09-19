package com.mycompany.app;

import java.util.List;
import java.util.Scanner;

public class ConteudoService {

    private Persistencia<Conteudo> persistencia;
    private UsuarioHSQL usuarioHSQL;
    private Scanner scanner = new Scanner(System.in); // Adicionando Scanner para ler input do usuário

    public ConteudoService(Persistencia<Conteudo> persistencia, UsuarioHSQL usuarioHSQL) {
        this.persistencia = persistencia;
        this.usuarioHSQL = usuarioHSQL;
    }

    public void save(Conteudo conteudo) {
        persistencia.save(conteudo);
    }

    public void atualizarConteud(int id, String nome, String descricao, Pessoa autor) {
        persistencia.atualizar(new Conteudo(id, nome, descricao, autor));
    }

    public List<Conteudo> listarConteudos() {
        return persistencia.listar();
    }

    public boolean removerConteudo(int id) {
        return persistencia.remover(id);
    }

    public Conteudo buscarConteudoPorId(int id) {
        for (Conteudo c : listarConteudos()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Adicionando método lerInfo
    public String lerInfo(String label) {
        System.out.println(label + ": ");
        return scanner.nextLine().trim();
    }

    public void atualizarNome(Pessoa currentUser) {
        String senhaAtual = lerInfo("Digite sua senha atual");

        if (senhaAtual.equals(currentUser.getSenha())) {
            String novoNome = lerInfo("Digite seu novo nome").toLowerCase();
            String nomeAntigo = currentUser.getNome();

            currentUser.setNome(novoNome); 
            usuarioHSQL.atualizarPessoa(currentUser, nomeAntigo); 

            atualizarNomeAutor(nomeAntigo, novoNome); 

            System.out.println("Nome atualizado com sucesso!");
        } else {
            System.out.println("Senha atual incorreta.");
        }
    }

    public void atualizarNomeAutor(String nomeAntigo, String novoNome) {
        List<Conteudo> conteudos = listarConteudos();
        for (Conteudo conteudo : conteudos) {
            if (conteudo.getAutor().getNome().equalsIgnoreCase(nomeAntigo)) {
                conteudo.getAutor().setNome(novoNome);
                atualizarConteud(conteudo.getId(), conteudo.getNome(), conteudo.getDescricao(), conteudo.getAutor());
            }
        }
    }
}
