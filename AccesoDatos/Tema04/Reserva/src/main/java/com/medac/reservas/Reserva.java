package com.medac.reservas;

import java.time.LocalDate;

public class Reserva {
    private Long id;
    private String alumno;
    private String aula;
    private LocalDate fecha;

    public Reserva() {
    }

    public Reserva(Long id, String alumno, String aula, LocalDate fecha) {
        this.id = id;
        this.alumno = alumno;
        this.aula = aula;
        this.fecha = fecha;
    }

    public Reserva(String alumno, String aula, LocalDate fecha) {
        this(null, alumno, aula, fecha);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAlumno() { return alumno; }
    public void setAlumno(String alumno) { this.alumno = alumno; }
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return String.format("ID: %d | Alumno: %s | Aula: %s | Fecha: %s",
                id, alumno, aula, fecha);
    }
}