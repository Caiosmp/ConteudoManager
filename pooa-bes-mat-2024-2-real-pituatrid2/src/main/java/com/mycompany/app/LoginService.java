package com.mycompany.app;

import java.util.List;

public class LoginService implements ILoginService {
    private List<Pessoa> pessoas;

    public LoginService(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    @Override
    public boolean login(String nome, String senha) {
        for (Pessoa p : pessoas) {
            if (p.getNome().equals(nome) && p.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
