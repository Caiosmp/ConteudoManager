package com.mycompany.app;

import java.util.List;

public interface IConteudoManager {
    void criarConteudo();
    List<Conteudo> listarConteudos();
    void atualizarConteudo(int id, String nome, String descricao);
    boolean apagarConteudo(int id);
}
