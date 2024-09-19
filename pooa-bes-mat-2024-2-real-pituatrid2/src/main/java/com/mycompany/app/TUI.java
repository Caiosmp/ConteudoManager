package com.mycompany.app;

import java.util.Scanner;

public class TUI extends UI {

    private Scanner scan = new Scanner(System.in);
    private UsuarioHSQL usuarioHSQL = new UsuarioHSQL();
    private ConteudoService conteudoService = new ConteudoService(new ConteudoHSQL(), usuarioHSQL);
    private MenuActions menuActions = new MenuActions(usuarioHSQL, conteudoService);


    public Pessoa menuInicial() {
        int opcao;
        do {
            System.out.println("Escolha a opção que você deseja: \n[1] Fazer login no sistema \n[2] Cadastrar-se \n[3] Sair");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Por favor, digite seu nome: ");
                    String nome = scan.nextLine().toLowerCase();
                    System.out.print("Agora, digite sua senha: ");
                    String senha = scan.nextLine();
                    Pessoa pessoa = usuarioHSQL.loginPessoa(nome, senha);
                    
                    
                    if (pessoa != null) {
                        if (nome.equals("teste") && senha.equals("123")) {
                            System.out.println("Administrador logado.");
                        } else {
                        	System.out.println("Usuário logado com sucesso!");
                        }
                        
                        return pessoa; // Retorna a pessoa logada
                    }
                    System.out.println("Login inválido");
                    break;
                case 2:
                    menuActions.cadastrarPessoa();
                    break;
                case 3:
                    System.out.println("Encerrando programa");
                    scan.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (true);
    }

    public Pessoa menuLogado(Pessoa currentUser) {
        if (currentUser.getNome().equals("teste") && currentUser.getSenha().equals("123")) {
            return menuAdministrador(currentUser);
        } else {
            return menuUsuario(currentUser);
        }
    }

    private Pessoa menuUsuario(Pessoa currentUser) {
        int opcao;
        do {
            System.out.println("Digite a opção que você deseja: \n[1] CRIAR CONTEÚDO \n[2] LISTAR CONTEÚDOS \n[3] ALTERAR MINHAS INFORMAÇÕES \n[4] REMOVER MEU CONTEÚDO \n[5] EDITAR MEU CONTEÚDO \n[6] LOGOUT");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    String nome = lerInfo("Digite o nome do conteúdo");
                    String descricao = lerInfo("Digite a descrição do conteúdo");
                    Conteudo conteudo = new Conteudo(null, nome, descricao, currentUser);
                    conteudoService.save(conteudo);
                    System.out.println("Conteúdo criado!");
                    break;
                case 2:
                    menuActions.listarConteudos();
                    break;
                case 3:
                    menuActions.atualizarInformacoes(currentUser);  // Chama a função para alterar as informações do próprio usuário
                    break;
                case 4:
                    menuActions.removerConteudoProprio(currentUser);
                    break;
                case 5:
                    menuActions.editarConteudoProprio(currentUser);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
        return currentUser;
    }

    private Pessoa menuAdministrador(Pessoa currentUser) {
        int opcao;
        do {
            System.out.println("Digite a opção que você deseja: ");
            System.out.println("[1] CRIAR CONTEÚDO \n[2] LISTAR CONTEÚDOS \n[3] ALTERAR MINHAS INFORMAÇÕES \n[4] REMOVER MEU CONTEÚDO \n[5] EDITAR MEU CONTEÚDO");
            System.out.println("-----------");
            System.out.println("[6] CONSULTAR BANCO DE DADOS \n[7] ALTERAR INFORMAÇÕES \n[8] REMOVER CONTEÚDO \n[9] EDITAR CONTEÚDO \n[10] REMOVER USUÁRIO \n[11] LOGOUT");
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    String nome = lerInfo("Digite o nome do conteúdo");
                    String descricao = lerInfo("Digite a descrição do conteúdo");
                    Conteudo conteudo = new Conteudo(null, nome, descricao, currentUser);
                    conteudoService.save(conteudo);
                    System.out.println("Conteúdo criado!");
                    break;
                case 2:
                    menuActions.listarConteudos();
                    break;
                case 3:
                    menuActions.atualizarInformacoes(currentUser);  // Permite o administrador alterar suas próprias informações
                    break;
                case 4:
                    menuActions.removerConteudoProprio(currentUser);
                    break;
                case 5:
                    menuActions.editarConteudoProprio(currentUser);
                    break;
                case 6:
                    ConsultaBancoDados consulta = new ConsultaBancoDados();
                    consulta.menuConsulta();
                    break;
                case 7:
                    menuActions.atualizarInformacoesAdministrador();  // Administrador pode alterar informações de outros usuários
                    break;
                case 8:
                    menuActions.removerConteudo();
                    break;
                case 9:
                    menuActions.editarConteudo();
                    break;
                case 10:
                    menuActions.removerUsuario();
                    break;
                case 11:
                    System.out.println("Saindo...");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 11);
        return currentUser;
    }

    private String lerInfo(String label) {
        System.out.println(label + ": ");
        return scan.nextLine().trim();
    }
}
