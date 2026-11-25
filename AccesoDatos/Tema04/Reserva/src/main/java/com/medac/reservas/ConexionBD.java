package com.medac.reservas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:postgresql://localhost:5432/reservas"
    );
    private static final String USER = System.getenv().getOrDefault(
            "DB_USER",
            "alumno"
    );
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "Configura la variable de entorno DB_PASSWORD antes de ejecutar la aplicación."
            );
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}