    package com.mycompany.app;

    import java.util.List;

    public interface UsuarioPersistencia {
        void salvarUsuario(Pessoa pessoa);
        Pessoa loginPessoa(String nome, String senha);
        List<Pessoa> listarUsuarios();
        boolean removerUsuario(String nome);
        void atualizarPessoa(Pessoa pessoa, String nomeAntigo);
    }
