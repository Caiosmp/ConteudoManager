package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConteudoHSQL implements Persistencia<Conteudo>{

    private static final String DB_URL = "jdbc:hsqldb:mem:conteudoDB";
    // Arquivo
    // private static final String DB_URL = "jdbc:hsqldb:file:database/conteudoDB";
    // Servidor
    // private static final String DB_URL = "jdbc:hsqldb:hsql://localhost/conteudoDB";
    // HTTP server
    // private static final String DB_URL = "jdbc:hsqldb:http://localhost/conteudoDB";

    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection = null;

    public ConteudoHSQL() {
        criarTabelaConteudo();
        criarTabelaUsuario();
    }

    // Obtém a conexão com o banco de dados
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); 
            }
        }
        return connection;
    }

    public void criarTabelaConteudo() {
        String sql = "CREATE TABLE IF NOT EXISTS Conteudo (" +
                     "id INTEGER IDENTITY PRIMARY KEY, " +
                     "titulo VARCHAR(255), " +
                     "texto VARCHAR(10000), " +
                     "autor VARCHAR(255))";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criarTabelaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                     "id INTEGER IDENTITY PRIMARY KEY, " +
                     "nome VARCHAR(255), " +
                     "senha VARCHAR(255))";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Conteudo conteudo) {
        String sql = "INSERT INTO Conteudo (titulo, texto, autor) VALUES (?, ?, ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, conteudo.getNome());
            stmt.setString(2, conteudo.getDescricao());
            stmt.setString(3, conteudo.getAutor().getNome());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Conteudo> listar() {
        List<Conteudo> conteudos = new ArrayList<>();
        String sql = "SELECT * FROM Conteudo";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Conteudo conteudo = new Conteudo();
                conteudo.setId(rs.getInt("id"));
                conteudo.setNome(rs.getString("titulo"));
                conteudo.setDescricao(rs.getString("texto"));

                // Carregar o autor corretamente
                Pessoa autor = new Pessoa();
                autor.setNome(rs.getString("autor"));
                conteudo.setAutor(autor);

                conteudos.add(conteudo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conteudos;
    }

    public void atualizar(Conteudo conteudo) {
        String sql = "UPDATE Conteudo SET titulo = ?, texto = ?, autor = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Definir os parâmetros
            stmt.setString(1, conteudo.getNome());     // Título do conteúdo
            stmt.setString(2, conteudo.getDescricao()); // Descrição do conteúdo
            stmt.setString(3, conteudo.getAutor().getNome()); // Nome do autor
            
            stmt.setInt(4, conteudo.getId()); // ID do conteúdo que está sendo atualizado
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM Conteudo WHERE id = ?";
        boolean delete = false;
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            delete = true;
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }

    public void salvarUsuario(Pessoa pessoa) {
        String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSenha());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pessoa> listarUsuarios() {
        List<Pessoa> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try {
            Connection conn = getConnection();
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
}
