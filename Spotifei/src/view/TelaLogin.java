package view;

import dao.UsuarioDAO;
import model.Usuario;
import controller.MainController; 

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login - Spotifei");
        setSize(400, 360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Bem-vindo ao Spotifei");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(90, 20, 250, 25);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBounds(50, 70, 80, 25);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(130, 70, 200, 25);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setBounds(50, 110, 80, 25);
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(130, 110, 200, 25);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(130, 160, 200, 35);
        btnLogin.setBackground(new Color(30, 215, 96));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnCadastro = new JButton("Cadastrar-se");
        btnCadastro.setBounds(130, 210, 200, 30);
        btnCadastro.setBackground(Color.LIGHT_GRAY);
        btnCadastro.setFont(new Font("Arial", Font.PLAIN, 13));
        btnCadastro.setFocusPainted(false);

        add(titulo);
        add(lblEmail); add(txtEmail);
        add(lblSenha); add(txtSenha);
        add(btnLogin);
        add(btnCadastro);

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.login(email, senha);
            if (u != null) {
                MainController.logar(u); // ⬅️ ESSA LINHA AQUI É FUNDAMENTAL
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + u.getNome());
                new TelaPrincipal(u).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Login inválido");
            }
        });

        btnCadastro.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}
