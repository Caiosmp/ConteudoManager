package com.mycompany.app;

public class Main {
    public static void main(String[] args) {
        TUI ui = new TUI();
        Pessoa currentUser;

        while (true) {
            currentUser = ui.menuInicial(); 
            if (currentUser != null) {
                ui.menuLogado(currentUser);  
            }
        }
    }
}
