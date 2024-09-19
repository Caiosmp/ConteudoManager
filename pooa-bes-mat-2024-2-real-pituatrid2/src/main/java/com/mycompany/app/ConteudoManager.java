package com.mycompany.app;

import java.util.List;
import java.util.Scanner;

public class ConteudoManager implements IConteudoManager {
    private List<Conteudo> conteudos;
    private Scanner scan = new Scanner(System.in);
    

    public ConteudoManager(List<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }

    @Override
    public void criarConteudo() {
        try {
            System.out.print("Quantas manchetes deseja adicionar: ");
            int quantidade = scan.nextInt();
            scan.nextLine(); 
            for (int i = 0; i < quantidade; i++) {
                Conteudo conteudo = new Conteudo();
                System.out.println("Digite o nome da manchete: ");
                conteudo.setNome(scan.nextLine().toLowerCase());
                System.out.println("Digite uma descrição para o conteúdo: ");
                conteudo.setDescricao(scan.nextLine());
                conteudo.setId(conteudos.size() + 1);
                conteudo.setAutor(conteudo.getAutor());
                conteudos.add(conteudo);
            }
        } catch (Exception e) {
            System.out.println("Digite um numero");
            scan.nextLine();
            criarConteudo();
        }
        
    }

    @Override
    public List<Conteudo> listarConteudos() {
        return conteudos;
    }

    @Override
    public void atualizarConteudo(int id, String nome, String descricao) {
        conteudos.get(id - 1).setNome(nome);
        conteudos.get(id - 1).setDescricao(descricao);
    }

    @Override
    public boolean apagarConteudo(int id) {
        return conteudos.removeIf(c -> c.getId() == id);
    }
}
