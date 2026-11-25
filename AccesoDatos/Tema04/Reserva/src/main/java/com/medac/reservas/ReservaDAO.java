package com.medac.reservas;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservaDAO {

    public boolean insertar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reserva (alumno, aula, fecha) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reserva.getAlumno());
            ps.setString(2, reserva.getAula());
            ps.setDate(3, Date.valueOf(reserva.getFecha()));
            return ps.executeUpdate() > 0;
        }
    }

    public List<Reserva> listarTodas() throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva ORDER BY id ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
        }
        return lista;
    }

    public List<Reserva> buscarPorAula(String aula) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE aula = ? ORDER BY fecha ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public boolean eliminarPorId(long id) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Reserva buscarPorId(long id) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReserva(rs);
                }
            }
        }
        return null;
    }

    public boolean actualizar(long id, String alumno, String aula, LocalDate fecha) throws SQLException {
        String sql = "UPDATE reserva SET alumno = ?, aula = ?, fecha = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alumno);
            ps.setString(2, aula);
            ps.setDate(3, Date.valueOf(fecha));
            ps.setLong(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Reserva> buscarPorNombreParcial(String texto) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE alumno ILIKE ? ORDER BY fecha ASC";
        String patron = "%" + texto + "%";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patron);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public List<Reserva> buscarEntreFechas(LocalDate desde, LocalDate hasta) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE fecha BETWEEN ? AND ? ORDER BY fecha ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(desde));
            ps.setDate(2, Date.valueOf(hasta));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public List<Reserva> buscarPorAulaYFecha(String aula, LocalDate fecha) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE aula = ? AND fecha = ?";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aula);
            ps.setDate(2, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public List<Reserva> buscarDosAulas(String aula1, String aula2) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE aula = ? OR aula = ? ORDER BY fecha ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aula1);
            ps.setString(2, aula2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public List<Reserva> buscarExcluyendoAula(String aulaExcluida) throws SQLException {
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE NOT (aula = ?) ORDER BY fecha ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aulaExcluida);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public List<Reserva> buscarEnVariasAulas(List<String> aulas) throws SQLException {
        if (aulas == null || aulas.isEmpty()) {
            return List.of();
        }
        String placeholders = String.join(", ", Collections.nCopies(aulas.size(), "?"));
        String sql = "SELECT id, alumno, aula, fecha FROM reserva WHERE aula IN (" + placeholders + ") ORDER BY fecha ASC";
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < aulas.size(); i++) {
                ps.setString(i + 1, aulas.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReserva(rs));
                }
            }
        }
        return lista;
    }

    public void probarTransaccionConSavepoint(long id, LocalDate nuevaFecha, String aulaTemporal, boolean cancelarCambioAula) throws SQLException {
        String sqlFecha = "UPDATE reserva SET fecha = ? WHERE id = ?";
        String sqlAula = "UPDATE reserva SET aula = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConnection()) {
            boolean autoCommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement psFecha = conn.prepareStatement(sqlFecha)) {
                    psFecha.setDate(1, Date.valueOf(nuevaFecha));
                    psFecha.setLong(2, id);
                    if (psFecha.executeUpdate() == 0) {
                        throw new SQLException("No existe la reserva con ID " + id);
                    }
                }

                Savepoint punto1 = conn.setSavepoint("punto_1");

                try (PreparedStatement psAula = conn.prepareStatement(sqlAula)) {
                    psAula.setString(1, aulaTemporal);
                    psAula.setLong(2, id);
                    psAula.executeUpdate();
                }

                if (cancelarCambioAula) {
                    conn.rollback(punto1);
                    System.out.println("Se revirtió el cambio de aula mediante ROLLBACK al savepoint 'punto_1'.");
                }

                conn.commit();
                System.out.println("Transacción confirmada con COMMIT.");
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Error en la transacción. Se ejecutó ROLLBACK completo: " + ex.getMessage());
                throw ex;
            } finally {
                conn.setAutoCommit(autoCommitOriginal);
            }
        }
    }

    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        return new Reserva(
                rs.getLong("id"),
                rs.getString("alumno"),
                rs.getString("aula"),
                rs.getDate("fecha").toLocalDate()
        );
    }
}