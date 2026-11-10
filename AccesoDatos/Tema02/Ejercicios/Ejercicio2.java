package Ejercicios;

import java.io.*;

public class Ejercicio2 {
    // Variables globales para las estadísticas compartidas (o controladas)

    static long caracteresProcesados = 0;
    static long lineasProcesadas = 0;

    public static void main(String[] args) throws IOException {

        String origen = "fichero_origen.txt";
        String destino = "fichero_copia.txt";

        File fileOrigen = new File(origen);

        try {
            PipedOutputStream pout = new PipedOutputStream();
            PipedInputStream pin = new PipedInputStream(pout);

            // 1. Hilo Escritor: Lee el fichero origen y lo vuelca al pipe
            Thread hiloEscritor = new Thread(() -> {
                try (FileInputStream fis = new FileInputStream(fileOrigen)) {
                    int b;
                    while ((b = fis.read()) != -1) {
                        pout.write(b);
                    }
                    pout.close();
                } catch (IOException e) {
                    System.out.println("Error en escritor: " + e.getMessage());
                }
            });

            // 2. Hilo Lector/Copiador: Lee del pipe, escribe en destino y cuenta estadísticas
            Thread hiloLector = new Thread(() -> {
                try (FileOutputStream fos = new FileOutputStream(destino)) {
                    int b;
                    boolean ultimaFueSalto = false;

                    while ((b = pin.read()) != -1) {
                        fos.write(b); // Escribimos en el destino
                        caracteresProcesados++; // Contamos carácter

                        if (b == '\n') {
                            lineasProcesadas++;
                            ultimaFueSalto = true;
                        } else {
                            ultimaFueSalto = false;
                        }
                    }

                    // Si el archivo tiene texto pero no termina estrictamente con \n, cuenta la última línea
                    if (caracteresProcesados > 0 && !ultimaFueSalto) {
                        lineasProcesadas++;
                    }

                    pin.close();
                } catch (IOException e) {
                    System.out.println("Error en lector/copiador: " + e.getMessage());
                }
            });

            // Arrancar hilos
            hiloEscritor.start();
            hiloLector.start();

            // Esperar obligatoriamente a que ambos hilos terminen (.join())
            hiloEscritor.join();
            hiloLector.join();

            File fileDestino = new File(destino);

            // Mostrar resultados con el formato solicitado
            System.out.println("--- PROCESO TERMINADO ---");
            System.out.println("Fichero origen: " + fileOrigen.getName());
            System.out.println("Fichero destino: " + fileDestino.getName());
            System.out.println("Tamaño origen: " + fileOrigen.length() + " bytes");
            System.out.println("Tamaño destino: " + fileDestino.length() + " bytes");
            System.out.println("Líneas procesadas: " + lineasProcesadas);
            System.out.println("Caracteres procesados: " + caracteresProcesados);

        } catch (Exception e) {
            System.out.println("Ocurrió un error en el proceso: " + e.getMessage());
        }
    }
}
