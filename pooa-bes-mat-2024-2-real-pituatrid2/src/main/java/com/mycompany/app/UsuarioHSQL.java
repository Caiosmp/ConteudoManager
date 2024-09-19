package com.mycompany.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioHSQL implements UsuarioPersistencia {

    private ConteudoHSQL db = new ConteudoHSQL();

    public UsuarioHSQL() {
        criarAdminSeNaoExistir();
    }

    private void criarAdminSeNaoExistir() {
        String sql = "SELECT * FROM Usuario WHERE nome = ?";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "teste");
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                String insertSql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, "teste");
                insertStmt.setString(2, "123");
                insertStmt.executeUpdate();
                insertStmt.close();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvarUsuario(Pessoa pessoa) {
        String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSenha());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pessoa loginPessoa(String nome, String senha) {
        Pessoa pessoaCadastrada = null;
        String sql = "SELECT * FROM Usuario WHERE nome = ? AND senha = ?";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome.toLowerCase());
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pessoaCadastrada = new Pessoa(rs.getString("nome"), rs.getString("senha"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoaCadastrada;
    }

    @Override
    public boolean removerUsuario(String nome) {
        String sql = "DELETE FROM Usuario WHERE nome = ?";
        boolean sucesso = false;
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome.toLowerCase());
            int rowsAffected = stmt.executeUpdate();
            sucesso = (rowsAffected > 0);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sucesso;
    }

    @Override
    public List<Pessoa> listarUsuarios() {
        List<Pessoa> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(rs.getString("nome"));
                pessoa.setSenha(rs.getString("senha"));
                usuarios.add(pessoa);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public void atualizarPessoa(Pessoa pessoa, String nomeAntigo) {
        String sql = "UPDATE Usuario SET nome = ?, senha = ? WHERE nome = ?";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSenha());
            stmt.setString(3, nomeAntigo); // Usa o nome antigo para a atualização
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarSenha(Pessoa pessoa) {
        String sql = "UPDATE Usuario SET senha = ? WHERE nome = ?";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getSenha());  // Atualiza a senha
            stmt.setString(2, pessoa.getNome());   // Filtra pelo nome do usuário
            stmt.executeUpdate();  // Executa a atualização
            stmt.close();          // Fecha o PreparedStatement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
