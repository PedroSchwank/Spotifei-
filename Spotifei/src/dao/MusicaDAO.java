package dao;

import model.Musica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAO {

    public List<Musica> buscarPorTermo(String termo) {
        List<Musica> lista = new ArrayList<>();
        String sql = "SELECT * FROM musicas WHERE titulo ILIKE ? OR artista ILIKE ? OR genero ILIKE ?";

        try (Connection conn = ConexaoDAO.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + termo + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Musica m = new Musica();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setArtista(rs.getString("artista"));
                m.setGenero(rs.getString("genero"));
                lista.add(m);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar músicas: " + e.getMessage());
        }

        return lista;
    }
}
