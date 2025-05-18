package controller;

import model.Usuario;
import view.TelaLogin;

public class MainController {
    public static Usuario usuarioLogado;

    public static void main(String[] args) {
        // Inicia com login
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }

    public static void logar(Usuario u) {
        usuarioLogado = u;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
