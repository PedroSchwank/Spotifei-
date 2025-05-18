package dao;

import model.Playlist;
import model.Musica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    public boolean criar(Playlist p) {
        String sql = "INSERT INTO playlists (nome, usuario_id) VALUES (?, ?)";
        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getUsuarioId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao criar playlist: " + e.getMessage());
            return false;
        }
    }

    public List<Playlist> listarPorUsuario(int usuarioId) {
        List<Playlist> lista = new ArrayList<>();
        String sql = "SELECT * FROM playlists WHERE usuario_id = ?";
        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setUsuarioId(rs.getInt("usuario_id"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar playlists: " + e.getMessage());
        }
        return lista;
    }

    public int obterOuCriarPlaylistCurtidas(int usuarioId) {
        String sqlBusca = "SELECT id FROM playlists WHERE nome = 'Curtidas' AND usuario_id = ?";
        String sqlCria = "INSERT INTO playlists (nome, usuario_id) VALUES ('Curtidas', ?) RETURNING id";
        try (Connection conn = ConexaoDAO.conectar()) {
            PreparedStatement busca = conn.prepareStatement(sqlBusca);
            busca.setInt(1, usuarioId);
            ResultSet rs = busca.executeQuery();
            if (rs.next()) return rs.getInt("id");

            PreparedStatement cria = conn.prepareStatement(sqlCria);
            cria.setInt(1, usuarioId);
            ResultSet rsCria = cria.executeQuery();
            if (rsCria.next()) return rsCria.getInt("id");

        } catch (SQLException e) {
            System.out.println("Erro ao obter/criar playlist Curtidas: " + e.getMessage());
        }
        return -1;
    }

    public void adicionarMusicaNaPlaylist(int playlistId, int musicaId) {
        String sql = "INSERT INTO playlist_musicas (playlist_id, musica_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar música na playlist: " + e.getMessage());
        }
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        List<Musica> lista = new ArrayList<>();
        String sql = """
            SELECT m.id, m.titulo, m.artista, m.genero
            FROM playlist_musicas pm
            JOIN musicas m ON m.id = pm.musica_id
            WHERE pm.playlist_id = ?
        """;

        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Musica m = new Musica();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setArtista(rs.getString("artista"));
                m.setGenero(rs.getString("genero"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar músicas da playlist: " + e.getMessage());
        }

        return lista;
    }
}
