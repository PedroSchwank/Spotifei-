package view;

import controller.MainController;
import dao.HistoricoDAO;
import model.Historico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaHistorico extends JFrame {
    public TelaHistorico() {
        setTitle("Histórico de Atividades - Spotifei");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(20, 20, 20);
        Color corTexto = new Color(230, 230, 230);
        Color verde = new Color(30, 215, 96);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(fundo);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("📜 Histórico de Músicas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(verde);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea area = new JTextArea(15, 40);
        area.setEditable(false);
        area.setBackground(new Color(40, 40, 40));
        area.setForeground(corTexto);
        area.setFont(new Font("Courier New", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);

        JButton btnVoltar = new JButton("⬅ Voltar");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setBackground(Color.LIGHT_GRAY);
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());

        // Carrega o histórico do banco
        int userId = MainController.getUsuarioLogado().getId();
        HistoricoDAO hdao = new HistoricoDAO();
        List<Historico> registros = hdao.listarHistoricoDoUsuario(userId);

        if (registros.isEmpty()) {
            area.setText("Nenhuma ação registrada ainda.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Historico h : registros) {
                sb.append(String.format("[%s] %s → %s\n",
                        h.getDataFormatada(),
                        h.getAcao().toUpperCase(),
                        h.getNomeMusica()));
            }
            area.setText(sb.toString());
        }

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(scroll);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnVoltar);

        add(painel);
    }
}
