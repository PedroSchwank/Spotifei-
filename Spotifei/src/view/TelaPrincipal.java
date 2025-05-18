package view;

import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal(Usuario u) {
        setTitle("Spotifei - Painel Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color corFundo = new Color(20, 20, 20);
        Color corTexto = new Color(240, 240, 240);
        Color corVerde = new Color(30, 215, 96);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(corFundo);
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("🎵 Bem-vindo, " + u.getNome());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(corVerde);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Email logado: " + u.getEmail());
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(corTexto);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnBuscar = new JButton("Buscar Músicas");
        JButton btnPlaylist = new JButton("Minhas Playlists");
        JButton btnHistorico = new JButton("Histórico");

        JButton[] botoes = {btnBuscar, btnPlaylist, btnHistorico};
        for (JButton botao : botoes) {
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            botao.setBackground(corVerde);
            botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
            botao.setFocusPainted(false);
        }

        btnBuscar.addActionListener(e -> new TelaBusca().setVisible(true));
        btnPlaylist.addActionListener(e -> new TelaPlaylist().setVisible(true));
        btnHistorico.addActionListener(e -> new TelaHistorico().setVisible(true));

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(10));
        painel.add(sub);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnBuscar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnPlaylist);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnHistorico);

        add(painel);
    }
}
