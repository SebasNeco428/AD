package Ejercicios;

import java.io.FileInputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Ejercicio1 {
        PipedInputStream tuboEntrada = new PipedInputStream();
        PipedOutputStream tuboSalida = new PipedOutputStream();

        tuboSalida.connect(tuboEntrada);

        //Hilo numero 1

        Thread hiloProductor = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fis = new FileInputStream("Datos_Tema01/ficheros.txt");
                    int byteleido;
                    while ((byteleido = fis.read()) != -1) {
                        tuboSalida.write(byteleido);
                    }
                    tuboSalida.close();
                    System.out.println("Fichero leido");

                } catch (Exception e) {
                    System.out.println("Error :" + e.getMessage());
                }
            }
        });

        Thread hiloConsumidor = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int dato;
                    while ((dato = tuboEntrada.read()) != -1) {
                        char Caracter = (char) dato;
                        System.out.println("El caracter :" + Caracter);
                     }
                } catch (Exception e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }
        });




}
