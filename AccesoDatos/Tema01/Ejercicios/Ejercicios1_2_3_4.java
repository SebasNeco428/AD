package Ejercicios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicios1_2_3_4 {
    static void main(String[] args) {
        try {
            File fichero = new File("datos.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
            }
            System.out.println("Nombre del fichero: " + fichero.getName());

            File ficheroInfo = new File("datos.txt");
            System.out.println("Nombre: " + ficheroInfo.getName());
            System.out.println("Ruta: " + ficheroInfo.getPath());
            System.out.println("Ruta absoluta: " + ficheroInfo.getAbsolutePath());
            System.out.println("Directorio padre: " + ficheroInfo.getParent());
            System.out.println("Si existe: " + ficheroInfo.exists());
            System.out.println("Si se puede leer: " + ficheroInfo.canRead());
            System.out.println("Si se puede escribir: " + ficheroInfo.canWrite());

            File directorio = new File("backup");
            if (!directorio.exists()) {
                directorio.mkdir();
            }
            if (directorio.exists()) {
                System.out.println("El directorio backup existe correctamente.");
            }
            File ficheroMover = new File("datos.txt");
            File ficheroDestino = new File("backup/datos.txt");
            ficheroMover.renameTo(ficheroDestino);

            FileWriter escritor = new FileWriter("alumnos.txt");
            escritor.write("Juan\n");
            escritor.write("María\n");
            escritor.write("Pedro\n");
            escritor.write("Ana\n");
            escritor.close();


        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
