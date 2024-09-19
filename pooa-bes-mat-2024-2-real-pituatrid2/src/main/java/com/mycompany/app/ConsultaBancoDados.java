package com.mycompany.app;

import java.util.List;
import java.util.Scanner;

public class ConsultaBancoDados {

    private ConteudoHSQL db = new ConteudoHSQL();
    private Scanner scanner = new Scanner(System.in);

    public void menuConsulta() {
        int opcao = -1;
        do {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] Ver todos os conteúdos");
            System.out.println("[2] Ver todas as pessoas e suas senhas");
            System.out.println("[3] Ver todas as pessoas e conteúdos em seu nome");
            System.out.println("[4] Sair");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    mostrarTodosConteudos();
                    break;
                case 2:
                    mostrarTodasPessoasESenhas();
                    break;
                case 3:
                    mostrarPessoasEConteudos();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 4);
    }

    public void mostrarTodosConteudos() {
        List<Conteudo> conteudos = db.listar();
        if (conteudos.isEmpty()) {
            System.out.println("Nenhum conteúdo encontrado.");
        } else {
            System.out.println("+----+----------------------------+-------------------------+----------------------------+");
            System.out.println("| ID | Título                      | Descrição                   | Autor                 |");
            System.out.println("+----+----------------------------+-------------------------+----------------------------+");
            for (Conteudo conteudo : conteudos) {
                System.out.printf("| %-2d | %-26s | %-23s | %-26s |\n", conteudo.getId(), conteudo.getNome(), conteudo.getDescricao(), conteudo.getAutor().getNome());
            }
            System.out.println("+----+----------------------------+-------------------------+----------------------------+");
        }
    }

    public void mostrarTodasPessoasESenhas() {
        List<Pessoa> pessoas = db.listarUsuarios();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada.");
        } else {
            System.out.println("+----------------------------+----------------------------+");
            System.out.println("| Nome                       | Senha                      |");
            System.out.println("+----------------------------+----------------------------+");
            for (Pessoa pessoa : pessoas) {
                System.out.printf("| %-26s | %-26s |\n", pessoa.getNome(), pessoa.getSenha());
            }
            System.out.println("+----------------------------+----------------------------+");
        }
    }


    public void mostrarPessoasEConteudos() {
        List<Pessoa> pessoas = db.listarUsuarios();
        List<Conteudo> conteudos = db.listar();
        
        if (pessoas.isEmpty() || conteudos.isEmpty()) {
            System.out.println("Nenhuma pessoa ou conteúdo encontrado.");
        } else {
            System.out.println("+----------------------------+----------------------------+-------------------------+");
            System.out.println("| Nome                       | Conteúdo                   | Descrição               |");
            System.out.println("+----------------------------+----------------------------+-------------------------+");

            for (Pessoa pessoa : pessoas) {
                boolean encontrouConteudo = false;
                for (Conteudo conteudo : conteudos) {
                    if (conteudo.getAutor().getNome().equals(pessoa.getNome())) {
                        System.out.printf("| %-26s | %-26s | %-23s |\n", pessoa.getNome(), conteudo.getNome(), conteudo.getDescricao());
                        encontrouConteudo = true;
                    }
                }
                if (!encontrouConteudo) {
                    System.out.printf("| %-26s | %-26s | %-23s |\n", pessoa.getNome(), "Nenhum", "Nenhum");
                }
            }
            System.out.println("+----------------------------+----------------------------+-------------------------+");
        }
    }


}
