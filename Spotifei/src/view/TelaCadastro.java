package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    public TelaCadastro() {
        setTitle("Cadastro - Spotifei");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cores e fontes
        Color verdeSpotifei = new Color(30, 215, 96);
        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 14);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painelPrincipal.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Criar nova conta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.DARK_GRAY);

        // Campos
        JTextField txtNome = new JTextField(20);
        txtNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtNome.setFont(fontePadrao);
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));

        JTextField txtEmail = new JTextField(20);
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtEmail.setFont(fontePadrao);
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtSenha.setFont(fontePadrao);
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(verdeSpotifei);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Ação do botão
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            Usuario u = new Usuario(nome, email, senha);
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.cadastrar(u)) {
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Email pode já estar em uso.");
            }
        });

        // Espaçamento
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        painelPrincipal.add(txtNome);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(txtEmail);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(txtSenha);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        painelPrincipal.add(btnCadastrar);

        add(painelPrincipal);
    }
}
