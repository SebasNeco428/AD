package com.medac.reservas;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final ReservaDAO dao = new ReservaDAO();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero(scanner, "Selecciona una opción: ");

                try {
                    switch (opcion) {
                        case 1 -> registrarReserva(scanner);
                        case 2 -> mostrarTodas();
                        case 3 -> buscarPorAula(scanner);
                        case 4 -> eliminarReserva(scanner);
                        case 5 -> modificarReserva(scanner);
                        case 6 -> buscarPorNombreParcial(scanner);
                        case 7 -> buscarEntreFechas(scanner);
                        case 8 -> menuFiltrosLogicos(scanner);
                        case 9 -> probarSavepoint(scanner);
                        case 0 -> System.out.println("Saliendo del gestor de reservas...");
                        default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error de base de datos: " + e.getMessage());
                }
            } while (opcion != 0);
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=================================");
        System.out.println("     GESTOR DE RESERVAS");
        System.out.println("=================================");
        System.out.println("1. Registrar una reserva");
        System.out.println("2. Mostrar todas las reservas");
        System.out.println("3. Buscar reservas por aula");
        System.out.println("4. Eliminar una reserva");
        System.out.println("5. Modificar una reserva");
        System.out.println("6. Buscar reservas por nombre de alumno (LIKE)");
        System.out.println("7. Buscar reservas por rango de fechas (BETWEEN)");
        System.out.println("8. Consultas con operadores lógicos (AND, OR, NOT, IN)");
        System.out.println("9. Probar transacción con SAVEPOINT");
        System.out.println("0. Salir");
    }

    private static void registrarReserva(Scanner scanner) throws SQLException {
        String alumno = leerTextoNoVacio(scanner, "Nombre del alumno: ");
        String aula = leerTextoNoVacio(scanner, "Aula: ");
        LocalDate fecha = leerFecha(scanner, "Fecha de la reserva (YYYY-MM-DD): ");

        boolean ok = dao.insertar(new Reserva(alumno, aula, fecha));
        System.out.println(ok
                ? "Reserva registrada correctamente."
                : "No se pudo registrar la reserva.");
    }

    private static void mostrarTodas() throws SQLException {
        List<Reserva> reservas = dao.listarTodas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            reservas.forEach(System.out::println);
        }
    }

    private static void buscarPorAula(Scanner scanner) throws SQLException {
        String aula = leerTextoNoVacio(scanner, "Nombre o código del aula: ");
        List<Reserva> reservas = dao.buscarPorAula(aula);
        if (reservas.isEmpty()) {
            System.out.println("No existen reservas para el aula " + aula + ".");
        } else {
            reservas.forEach(System.out::println);
        }
    }

    private static void eliminarReserva(Scanner scanner) throws SQLException {
        long id = leerLong(scanner, "ID de la reserva a eliminar: ");
        boolean eliminada = dao.eliminarPorId(id);
        System.out.println(eliminada
                ? "Reserva eliminada correctamente."
                : "No se ha encontrado ninguna reserva con el ID " + id + ".");
    }

    private static void modificarReserva(Scanner scanner) throws SQLException {
        long id = leerLong(scanner, "ID de la reserva a modificar: ");
        Reserva actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se ha encontrado ninguna reserva con el ID " + id + ".");
            return;
        }
        System.out.println("Reserva actual: " + actual);
        String alumno = leerTextoConDefecto(scanner, "Nuevo alumno [" + actual.getAlumno() + "]: ", actual.getAlumno());
        String aula = leerTextoConDefecto(scanner, "Nueva aula [" + actual.getAula() + "]: ", actual.getAula());
        LocalDate fecha = leerFechaConDefecto(scanner, "Nueva fecha YYYY-MM-DD [" + actual.getFecha() + "]: ", actual.getFecha());

        boolean ok = dao.actualizar(id, alumno, aula, fecha);
        System.out.println(ok
                ? "Reserva modificada correctamente."
                : "No se pudo modificar la reserva.");
    }

    private static void buscarPorNombreParcial(Scanner scanner) throws SQLException {
        String texto = leerTextoNoVacio(scanner, "Texto a buscar en el nombre del alumno: ");
        List<Reserva> reservas = dao.buscarPorNombreParcial(texto);
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas que coincidan con '" + texto + "'.");
        } else {
            reservas.forEach(System.out::println);
        }
    }

    private static void buscarEntreFechas(Scanner scanner) throws SQLException {
        LocalDate desde = leerFecha(scanner, "Fecha inicial (YYYY-MM-DD): ");
        LocalDate hasta = leerFecha(scanner, "Fecha final (YYYY-MM-DD): ");
        List<Reserva> reservas = dao.buscarEntreFechas(desde, hasta);
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas en ese intervalo de fechas.");
        } else {
            reservas.forEach(System.out::println);
        }
    }

    private static void menuFiltrosLogicos(Scanner scanner) throws SQLException {
        System.out.println("a) Aula y fecha (AND)");
        System.out.println("b) Dos aulas (OR)");
        System.out.println("c) Excluir un aula (NOT)");
        System.out.println("d) Varias aulas separadas por coma (IN)");
        String sub = leerTextoNoVacio(scanner, "Elige filtro (a-d): ").toLowerCase();

        List<Reserva> res = switch (sub) {
            case "a" -> dao.buscarPorAulaYFecha(
                    leerTextoNoVacio(scanner, "Aula: "),
                    leerFecha(scanner, "Fecha (YYYY-MM-DD): "));
            case "b" -> dao.buscarDosAulas(
                    leerTextoNoVacio(scanner, "Primera aula: "),
                    leerTextoNoVacio(scanner, "Segunda aula: "));
            case "c" -> dao.buscarExcluyendoAula(
                    leerTextoNoVacio(scanner, "Aula a excluir: "));
            case "d" -> {
                List<String> aulas = Arrays.stream(
                                leerTextoNoVacio(scanner, "Aulas (ej. A101,A102,B201): ").split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                yield dao.buscarEnVariasAulas(aulas);
            }
            default -> {
                System.out.println("Filtro no válido.");
                yield List.of();
            }
        };

        if (res.isEmpty()) {
            System.out.println("Sin resultados.");
        } else {
            res.forEach(System.out::println);
        }
    }

    private static void probarSavepoint(Scanner scanner) throws SQLException {
        long id = leerLong(scanner, "ID de la reserva: ");
        LocalDate nuevaFecha = leerFecha(scanner, "Nueva fecha a confirmar (YYYY-MM-DD): ");
        String aulaTemporal = leerTextoNoVacio(scanner, "Aula temporal a revertir con SAVEPOINT: ");
        dao.probarTransaccionConSavepoint(id, nuevaFecha, aulaTemporal, true);
        Reserva actualizada = dao.buscarPorId(id);
        System.out.println("Estado final de la reserva: " + actualizada);
    }

    private static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("El campo no puede estar vacío.");
        }
    }

    private static String leerTextoConDefecto(Scanner scanner, String mensaje, String defecto) {
        System.out.print(mensaje);
        String linea = scanner.nextLine().trim();
        return linea.isEmpty() ? defecto : linea;
    }

    private static LocalDate leerFecha(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Usa YYYY-MM-DD (ej. 2026-11-26).");
            }
        }
    }

    private static LocalDate leerFechaConDefecto(Scanner scanner, String mensaje, LocalDate defecto) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                return defecto;
            }
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Usa YYYY-MM-DD.");
            }
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private static long leerLong(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un identificador numérico válido.");
            }
        }
    }
}
