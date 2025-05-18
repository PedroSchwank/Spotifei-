package dao;

import model.Historico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    public void registrar(int usuarioId, int musicaId, String acao) {
        String sql = "INSERT INTO historico (usuario_id, musica_id, acao) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.setString(3, acao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao registrar histórico: " + e.getMessage());
        }
    }

    public List<Historico> listarHistoricoDoUsuario(int usuarioId) {
        List<Historico> lista = new ArrayList<>();
        String sql = """
            SELECT h.acao, h.data, m.titulo
            FROM historico h
            JOIN musicas m ON m.id = h.musica_id
            WHERE h.usuario_id = ?
            ORDER BY h.data DESC
        """;

        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Historico h = new Historico();
                h.setAcao(rs.getString("acao"));
                h.setData(rs.getTimestamp("data").toLocalDateTime());
                h.setNomeMusica(rs.getString("titulo"));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar histórico: " + e.getMessage());
        }

        return lista;
    }
}
