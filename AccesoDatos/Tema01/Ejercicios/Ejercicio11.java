package Ejercicios;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;

public class Ejercicio11 {
    static void main(String[] args) {
        try {
            //Creamos el fichero
            File fichero = new File("Datos_Tema01/fichero.txt");

            FileWriter fw = new FileWriter(fichero);
            fw.write("Sebastian Cristhian");
            fw.close();
            RandomAccessFile raf = new RandomAccessFile("Datos_Tema01/fichero.txt" , "rw");

            byte[] buffer = new byte[10];

            int leerbytes= raf.read(buffer,0 ,10);

            System.out.println("Los bytes leidos son :" + leerbytes);

            if (leerbytes > 0) {
                String textoLeido = new String(buffer, 0, leerbytes);
                System.out.println("Contenido del buffer: " + textoLeido);

            }

        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

}
