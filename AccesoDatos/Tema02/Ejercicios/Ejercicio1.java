package Ejercicios;


import java.io.*;

public class Ejercicio1 {
    public static void main(String[] args) {
        String nombreFichero = "fichero_origen.txt";

        // Creamos un fichero de prueba si no existe para que el programa funcione
        crearFicheroPrueba(nombreFichero);

        try {
            // 1. Crear las tuberías conectadas

            PipedOutputStream pout = new PipedOutputStream();
            PipedInputStream pin = new PipedInputStream(pout);

            // 2. Hilo Escritor: Lee del fichero y escribe en el Pipe

            Thread hiloEscritor = new Thread(() -> {
                try (FileInputStream fis = new FileInputStream(nombreFichero)) {
                    int dato;
                    while ((dato = fis.read()) != -1) {
                        pout.write(dato); // Enviar byte por el pipe
                    }
                    pout.close(); // Cerramos al terminar de enviar todo
                } catch (IOException e) {
                    System.out.println("Error en hilo escritor: " + e.getMessage());
                }
            });

            // 3. Hilo Lector: Lee del Pipe y muestra por pantalla
            Thread hiloLector = new Thread(() -> {
                try {
                    int dato;
                    while ((dato = pin.read()) != -1) {
                        System.out.print((char) dato); // Muestra el carácter por pantalla
                    }
                    pin.close();
                } catch (IOException e) {
                    System.out.println("Error en hilo lector: " + e.getMessage());
                }
            });

            // 4. Lanzar ambos hilos
            hiloEscritor.start();
            hiloLector.start();

            // Esperar a que terminen ambos hilos
            hiloEscritor.join();
            hiloLector.join();

            System.out.println("\n--- Proceso de lectura mediante Pipe finalizado ---");

        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

    private static void crearFicheroPrueba(String ruta) {
        try {
            File f = new File(ruta);
            if (!f.exists()) {
                try (FileWriter fw = new FileWriter(f)) {
                    fw.write("Hola, este es un texto de prueba\npara la evaluacion de hilos y pipes.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
