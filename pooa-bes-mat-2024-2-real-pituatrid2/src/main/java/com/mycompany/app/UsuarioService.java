package com.mycompany.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UsuarioService {

    private Scanner scan = new Scanner(System.in);
    private ConteudoHSQL db = new ConteudoHSQL(); 


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

    public void cadastrarPessoa() {
        Pessoa pessoa = new Pessoa();
        System.out.println("Qual o seu nome? ");
        pessoa.setNome(scan.nextLine().toLowerCase());

        System.out.println("Crie uma senha:");
        pessoa.setSenha(scan.nextLine());

        // Salvar no banco de dados
        String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSenha());
            stmt.executeUpdate();
            stmt.close();

            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
