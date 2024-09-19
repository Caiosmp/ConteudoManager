package com.mycompany.app;

import java.util.List;
import java.util.Scanner;

public class MenuActions {

    private UsuarioHSQL usuarioHSQL;
    private ConteudoService conteudoService;
    private Scanner scan = new Scanner(System.in);

    public MenuActions(UsuarioHSQL usuarioHSQL, ConteudoService conteudoService) {
        this.usuarioHSQL = usuarioHSQL;
        this.conteudoService = conteudoService;
    }

    // Método utilitário para ler informações com opção de cancelar
    public String lerInfo(String label) {
        System.out.println(label + ": ");
        String input = scan.nextLine().trim();
        if (input.equalsIgnoreCase("cancelar")) {
            System.out.println("Operação cancelada.");
            return null; // Retorna null em caso de cancelamento
        }
        return input;
    }

    public void cadastrarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(lerInfo("Digite seu nome").toLowerCase());
        if (pessoa.getNome() == null) return; // Cancela a operação se necessário
        pessoa.setSenha(lerInfo("Digite sua senha"));
        if (pessoa.getSenha() == null) return; // Cancela a operação se necessário
        usuarioHSQL.salvarUsuario(pessoa);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void listarConteudos() {
        List<Conteudo> conteudos = conteudoService.listarConteudos();
        if (conteudos.isEmpty()) {
            System.out.println("Ops, sem conteúdos, crie já o seu!");
        } else {
            for (Conteudo c : conteudos) {
                System.out.println(c.toString());
            }
        }
    }

    public void criarConteudo(Pessoa currentUser) {
        String nome = lerInfo("Digite o nome do conteúdo");
        if (nome == null) return; // Cancela a operação se necessário
        String descricao = lerInfo("Digite a descrição do conteúdo");
        if (descricao == null) return; // Cancela a operação se necessário
        Conteudo conteudo = new Conteudo(null, nome, descricao, currentUser);
        conteudoService.save(conteudo);
        System.out.println("Conteúdo criado!");
    }

    public void atualizarInformacoes(Pessoa currentUser) {
        int opcao;
        do {
            System.out.println("Escolha o que deseja alterar:");
            System.out.println("[1] Alterar Nome");
            System.out.println("[2] Alterar Senha");
            System.out.println("[3] Sair");

            opcao = scan.nextInt();
            scan.nextLine();  // Consumir a linha extra

            switch (opcao) {
                case 1:
                    atualizarNome(currentUser);
                    break;
                case 2:
                    atualizarSenha(currentUser);
                    break;
                case 3:
                    System.out.println("Saindo do menu de alteração de informações.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 3);
    }

    public void atualizarSenha(Pessoa currentUser) {
        String senhaAtual = lerInfo("Digite sua senha atual");
        if (senhaAtual == null) return; // Cancela a operação se necessário

        if (senhaAtual.equals(currentUser.getSenha())) {
            String novaSenha = lerInfo("Digite sua nova senha");
            if (novaSenha == null) return; // Cancela a operação se necessário
            String repetirSenha = lerInfo("Repita a nova senha");
            if (repetirSenha == null) return; // Cancela a operação se necessário

            if (novaSenha.equals(repetirSenha)) {
                currentUser.setSenha(novaSenha);
                usuarioHSQL.atualizarSenha(currentUser);
                System.out.println("Senha atualizada com sucesso!");
            } else {
                System.out.println("As senhas não coincidem.");
            }
        } else {
            System.out.println("Senha atual incorreta.");
        }
    }

    public void atualizarNome(Pessoa currentUser) {
        String senhaAtual = lerInfo("Digite sua senha atual");
    
        if (senhaAtual.equals(currentUser.getSenha())) {
            String novoNome = lerInfo("Digite seu novo nome").toLowerCase();
            String nomeAntigo = currentUser.getNome();  // Guarda o nome antigo
    
            currentUser.setNome(novoNome);  // Define o novo nome no objeto `Pessoa`
            usuarioHSQL.atualizarPessoa(currentUser, nomeAntigo);  // Atualiza o nome do usuário no banco de dados
    
            // Atualizar o nome do autor nos conteúdos que ele criou
            conteudoService.atualizarNomeAutor(nomeAntigo, novoNome);  // Atualiza o nome do autor nos conteúdos
    
            System.out.println("Nome atualizado com sucesso!");
        } else {
            System.out.println("Senha atual incorreta.");
        }
    }
    
    

    public void atualizarInformacoesAdministrador() {
        List<Pessoa> pessoas = usuarioHSQL.listarUsuarios();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.println("Usuários existentes:");
        for (Pessoa pessoa : pessoas) {
            System.out.println("Nome: " + pessoa.getNome());
        }

        String nomeUsuario = lerInfo("Digite o nome do usuário que deseja alterar (ou 'cancelar' para voltar)");
        if (nomeUsuario == null) return; // Cancela a operação se necessário

        Pessoa usuario = usuarioHSQL.loginPessoa(nomeUsuario, lerInfo("Digite a senha atual do usuário"));
        if (usuario == null) return; // Cancela a operação se necessário

        if (usuario != null) {
            System.out.println("Escolha o que deseja alterar:");
            System.out.println("[1] Alterar Nome");
            System.out.println("[2] Alterar Senha");

            int opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    alterarNomeAdministrador(usuario);
                    break;
                case 2:
                    alterarSenhaAdministrador(usuario);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Usuário ou senha incorretos.");
        }
    }

    public void alterarSenhaAdministrador(Pessoa usuario) {
        String novaSenha = lerInfo("Digite a nova senha");
        if (novaSenha == null) return; // Cancela a operação se necessário
        String repetirSenha = lerInfo("Repita a nova senha");
        if (repetirSenha == null) return; // Cancela a operação se necessário

        if (novaSenha.equals(repetirSenha)) {
            usuario.setSenha(novaSenha);
            usuarioHSQL.atualizarSenha(usuario);
            System.out.println("Senha do usuário " + usuario.getNome() + " alterada com sucesso!");
        } else {
            System.out.println("As senhas não coincidem.");
        }
    }

    public void alterarNomeAdministrador(Pessoa usuario) {
        String novoNome = lerInfo("Digite o novo nome do usuário");
        if (novoNome == null) return; // Cancela a operação se necessário
        String nomeAntigo = usuario.getNome();
        usuario.setNome(novoNome);
        usuarioHSQL.atualizarPessoa(usuario, nomeAntigo);

        conteudoService.atualizarNomeAutor(nomeAntigo, novoNome);
        System.out.println("Nome do usuário " + nomeAntigo + " alterado para " + novoNome + " com sucesso!");
    }

    public void removerConteudoProprio(Pessoa currentUser) {
        listarConteudos();
        String ids = lerInfo("Digite o ID do conteúdo para remover (ou 'cancelar' para voltar)");
        if (ids == null) return; // Cancela a operação se necessário
        int id;
        try {
            id = Integer.parseInt(ids);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Operação cancelada.");
            return;
        }
        Conteudo conteudo = conteudoService.buscarConteudoPorId(id);

        if (conteudo != null && conteudo.getAutor().getNome().equals(currentUser.getNome())) {
            boolean sucesso = conteudoService.removerConteudo(id);
            if (sucesso) {
                System.out.println("Conteúdo removido com sucesso.");
            } else {
                System.out.println("Falha ao remover o conteúdo.");
            }
        } else {
            System.out.println("Conteúdo não encontrado ou você não é o autor.");
        }
    }

    public void editarConteudoProprio(Pessoa currentUser) {
        listarConteudos();
        String ids = lerInfo("Digite o ID do conteúdo para editar (ou 'cancelar' para voltar)");
        if (ids == null) return; // Cancela a operação se necessário
        int id;
        try {
            id = Integer.parseInt(ids);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Operação cancelada.");
            return;
        }
        Conteudo conteudo = conteudoService.buscarConteudoPorId(id);

        if (conteudo != null && conteudo.getAutor().getNome().equals(currentUser.getNome())) {
            String novoNome = lerInfo("Digite o novo título do conteúdo (ou 'cancelar' para voltar)");
            if (novoNome == null) return; // Cancela a operação se necessário

            String novaDescricao = lerInfo("Digite a nova descrição do conteúdo (ou 'cancelar' para voltar)");
            if (novaDescricao == null) return; // Cancela a operação se necessário

            conteudoService.atualizarConteud(id, novoNome, novaDescricao, currentUser);
            System.out.println("Conteúdo atualizado com sucesso.");
        } else {
            System.out.println("Conteúdo não encontrado ou você não é o autor.");
        }
    }

    public void removerUsuario() {
        List<Pessoa> pessoas = usuarioHSQL.listarUsuarios();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.println("Usuários existentes:");
            for (Pessoa pessoa : pessoas) {
                System.out.println("Nome: " + pessoa.getNome());
            }
            String nome = lerInfo("Digite o nome do usuário para remover (ou 'cancelar' para voltar)");
            if (nome == null) return; // Cancela a operação se necessário
            boolean sucesso = usuarioHSQL.removerUsuario(nome);
            if (sucesso) {
                System.out.println("Usuário removido com sucesso.");
            } else {
                System.out.println("Falha ao remover o usuário, confira se ele existe.");
            }
        }
    }

    public void removerConteudo() {
        List<Conteudo> conteudos = conteudoService.listarConteudos();
        if (conteudos.isEmpty()) {
            System.out.println("Nenhum conteúdo encontrado.");
        } else {
            System.out.println("Conteúdos existentes:");
            for (Conteudo conteudo : conteudos) {
                System.out.println("ID: " + conteudo.getId() + " | Título: " + conteudo.getNome() + " | Autor: " + conteudo.getAutor().getNome());
            }
            String ids = lerInfo("Digite o ID do conteúdo para remover (ou 'cancelar' para voltar)");
            if (ids == null) return; // Cancela a operação se necessário
            int id;
            try {
                id = Integer.parseInt(ids);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Operação cancelada.");
                return;
            }
            boolean sucesso = conteudoService.removerConteudo(id);
            if (sucesso) {
                System.out.println("Conteúdo removido com sucesso.");
            } else {
                System.out.println("Falha ao remover o conteúdo.");
            }
        }
    }

    public void editarConteudo() {
        List<Conteudo> conteudos = conteudoService.listarConteudos();
        if (conteudos.isEmpty()) {
            System.out.println("Nenhum conteúdo encontrado.");
        } else {
            System.out.println("Conteúdos existentes:");
            for (Conteudo conteudo : conteudos) {
                System.out.println("ID: " + conteudo.getId() + " | Título: " + conteudo.getNome() + " | Autor: " + conteudo.getAutor().getNome());
            }
            String ids = lerInfo("Digite o ID do conteúdo para editar (ou 'cancelar' para voltar)");
            if (ids == null) return; // Cancela a operação se necessário
            int id;
            try {
                id = Integer.parseInt(ids);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Operação cancelada.");
                return;
            }
            Conteudo conteudo = conteudoService.buscarConteudoPorId(id);

            if (conteudo != null) {
                String novoNome = lerInfo("Digite o novo título do conteúdo (ou 'cancelar' para voltar)");
                if (novoNome == null) return; // Cancela a operação se necessário
                String novaDescricao = lerInfo("Digite a nova descrição do conteúdo (ou 'cancelar' para voltar)");
                if (novaDescricao == null) return; // Cancela a operação se necessário
                conteudoService.atualizarConteud(id, novoNome, novaDescricao, conteudo.getAutor());
                System.out.println("Conteúdo atualizado com sucesso.");
            } else {
                System.out.println("Conteúdo não encontrado.");
            }
        }
    }
}
