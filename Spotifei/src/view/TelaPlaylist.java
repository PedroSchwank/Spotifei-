package view;

import controller.MainController;
import dao.PlaylistDAO;
import model.Musica;
import model.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPlaylist extends JFrame {
    public TelaPlaylist() {
        setTitle("Minhas Playlists - Spotifei");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(35, 35, 35);
        Color texto = Color.WHITE;
        Color verde = new Color(30, 215, 96);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(fundo);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("🎧 Gerenciar Playlists");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(verde);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaPlaylists = new JList<>(modeloLista);
        listaPlaylists.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaPlaylists.setBackground(new Color(50, 50, 50));
        listaPlaylists.setForeground(texto);
        JScrollPane scroll = new JScrollPane(listaPlaylists);

        JTextField campoNova = new JTextField();
        campoNova.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoNova.setBorder(BorderFactory.createTitledBorder("Nova Playlist"));

        JButton btnCriar = new JButton("Criar Playlist");
        btnCriar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCriar.setBackground(verde);
        btnCriar.setForeground(Color.BLACK);
        btnCriar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnVerCurtidas = new JButton("🎧 Ver Curtidas");
        btnVerCurtidas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerCurtidas.setBackground(verde);
        btnVerCurtidas.setForeground(Color.BLACK);
        btnVerCurtidas.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton btnVoltar = new JButton("⬅ Voltar");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setBackground(Color.LIGHT_GRAY);
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnVoltar.setFocusPainted(false);

        PlaylistDAO dao = new PlaylistDAO();
        int userId = MainController.getUsuarioLogado().getId();
        List<Playlist> playlists = dao.listarPorUsuario(userId);
        for (Playlist p : playlists) {
            modeloLista.addElement("📁 " + p.getNome());
        }

        // 🔥 Clique duplo para abrir qualquer playlist
        listaPlaylists.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selecionada = listaPlaylists.getSelectedValue();
                    if (selecionada != null) {
                        String nomeLimpo = selecionada.replace("📁 ", "").trim();

                        for (Playlist p : playlists) {
                            if (p.getNome().equalsIgnoreCase(nomeLimpo)) {
                                List<Musica> musicas = dao.listarMusicasDaPlaylist(p.getId());
                                if (musicas.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Essa playlist está vazia.");
                                } else {
                                    StringBuilder msg = new StringBuilder("🎵 Músicas da playlist \"" + p.getNome() + "\":\n\n");
                                    for (Musica m : musicas) {
                                        msg.append("- ").append(m.getTitulo()).append(" (").append(m.getArtista()).append(")\n");
                                    }
                                    JTextArea area = new JTextArea(msg.toString());
                                    area.setEditable(false);
                                    area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                    area.setBackground(new Color(40, 40, 40));
                                    area.setForeground(Color.WHITE);
                                    JScrollPane scrollArea = new JScrollPane(area);
                                    scrollArea.setPreferredSize(new Dimension(400, 250));
                                    JOptionPane.showMessageDialog(null, scrollArea, p.getNome(), JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });

        btnCriar.addActionListener(e -> {
            String nome = campoNova.getText().trim();
            if (!nome.isEmpty()) {
                boolean jaExiste = playlists.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
                if (jaExiste) {
                    JOptionPane.showMessageDialog(this, "Você já tem uma playlist com esse nome.");
                    return;
                }

                Playlist nova = new Playlist(nome, userId);
                boolean sucesso = dao.criar(nova);
                if (sucesso) {
                    modeloLista.addElement("📁 " + nome);
                    playlists.add(nova);
                    campoNova.setText("");
                    JOptionPane.showMessageDialog(this, "Playlist criada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao criar playlist.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Digite um nome para a playlist.");
            }
        });

        btnVerCurtidas.addActionListener(e -> {
            int idCurtidas = dao.obterOuCriarPlaylistCurtidas(userId);
            List<Musica> musicas = dao.listarMusicasDaPlaylist(idCurtidas);

            if (musicas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma música curtida ainda.");
            } else {
                StringBuilder msg = new StringBuilder("Músicas curtidas:\n\n");
                for (Musica m : musicas) {
                    msg.append("- ").append(m.getTitulo()).append(" (").append(m.getArtista()).append(")\n");
                }
                JTextArea area = new JTextArea(msg.toString());
                area.setEditable(false);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                area.setBackground(new Color(40, 40, 40));
                area.setForeground(Color.WHITE);
                JScrollPane scrollArea = new JScrollPane(area);
                scrollArea.setPreferredSize(new Dimension(400, 250));
                JOptionPane.showMessageDialog(this, scrollArea, "Curtidas", JOptionPane.PLAIN_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> dispose());

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(campoNova);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnCriar);
        painel.add(Box.createVerticalStrut(20));
        painel.add(scroll);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnVerCurtidas);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnVoltar);

        add(painel);
    }
}
