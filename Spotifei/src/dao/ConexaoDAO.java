package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/spotifei";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
