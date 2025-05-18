package view;

import controller.MainController;
import dao.HistoricoDAO;
import dao.MusicaDAO;
import dao.PlaylistDAO;
import model.Musica;
import model.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaBusca extends JFrame {
    public TelaBusca() {
        setTitle("Explorar músicas - Spotifei");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color corPrincipal = new Color(25, 20, 20);
        Color corTexto = new Color(240, 240, 240);
        Color corVerde = new Color(30, 215, 96);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(corPrincipal);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("🔍 Buscar Música");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(corVerde);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField campoBusca = new JTextField();
        campoBusca.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoBusca.setBorder(BorderFactory.createTitledBorder("Digite nome, artista ou gênero"));

        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoBuscar.setBackground(corVerde);
        botaoBuscar.setForeground(Color.BLACK);
        botaoBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextArea areaResultados = new JTextArea(10, 30);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Courier New", Font.PLAIN, 14));
        areaResultados.setBackground(new Color(40, 40, 40));
        areaResultados.setForeground(corTexto);
        JScrollPane scroll = new JScrollPane(areaResultados);

        JButton botaoVoltar = new JButton("⬅ Voltar");
        botaoVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoVoltar.setBackground(Color.LIGHT_GRAY);
        botaoVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        botaoVoltar.setFocusPainted(false);

        botaoBuscar.addActionListener(e -> {
            String termo = campoBusca.getText().trim();
            if (termo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite algo para buscar.");
                return;
            }

            MusicaDAO dao = new MusicaDAO();
            List<Musica> resultados = dao.buscarPorTermo(termo);
            areaResultados.setText("");

            if (resultados.isEmpty()) {
                areaResultados.append("Nenhum resultado encontrado.");
            } else {
                int userId = MainController.getUsuarioLogado().getId();
                PlaylistDAO pdao = new PlaylistDAO();
                HistoricoDAO hdao = new HistoricoDAO();

                for (Musica m : resultados) {
                    areaResultados.append("🎵 " + m.getTitulo() + " - " + m.getArtista() + " [" + m.getGenero() + "]\n");

                    hdao.registrar(userId, m.getId(), "buscar"); // registra busca

                    int escolha = JOptionPane.showConfirmDialog(this,
                            "Você deseja adicionar \"" + m.getTitulo() + "\" a uma playlist?",
                            "Adicionar Música",
                            JOptionPane.YES_NO_OPTION);

                    if (escolha == JOptionPane.YES_OPTION) {
                        List<Playlist> playlists = pdao.listarPorUsuario(userId);

                        if (playlists.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Você ainda não criou playlists.");
                        } else {
                            String[] nomes = playlists.stream().map(Playlist::getNome).toArray(String[]::new);
                            String escolhida = (String) JOptionPane.showInputDialog(
                                    this,
                                    "Selecione a playlist:",
                                    "Adicionar à Playlist",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    nomes,
                                    nomes[0]
                            );

                            if (escolhida != null) {
                                int idPlaylist = -1;
                                for (Playlist p : playlists) {
                                    if (p.getNome().equals(escolhida)) {
                                        idPlaylist = p.getId();
                                        break;
                                    }
                                }

                                if (idPlaylist != -1) {
                                    pdao.adicionarMusicaNaPlaylist(idPlaylist, m.getId());
                                    hdao.registrar(userId, m.getId(), "curtir");
                                    JOptionPane.showMessageDialog(this, "🎶 Música adicionada à playlist \"" + escolhida + "\"!");
                                }
                            }
                        }
                    }
                }
            }
        });

        botaoVoltar.addActionListener(e -> dispose());

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(campoBusca);
        painel.add(Box.createVerticalStrut(10));
        painel.add(botaoBuscar);
        painel.add(Box.createVerticalStrut(20));
        painel.add(scroll);
        painel.add(Box.createVerticalStrut(15));
        painel.add(botaoVoltar);

        add(painel);
    }
}
