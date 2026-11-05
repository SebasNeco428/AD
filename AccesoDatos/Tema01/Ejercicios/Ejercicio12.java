package Ejercicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio11 {
    static void main(String[] args) {
        try {
            File fichero = new File("Datos_Tema01/ficheros.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
                System.out.println("Se creo con exito :" + fichero.getName());
            }
            System.out.println("Su nombre es :" + fichero.getName());
            System.out.println("Su ruta es :" + fichero.getPath());
            System.out.println("Su ruta absoluta :" + fichero.getAbsolutePath());
            System.out.println("Existe realmente ? " + fichero.exists());
            System.out.println("Se puede leer :" + fichero.canRead());
            System.out.println("Se puede escribir :" + fichero.canWrite());

            FileWriter fw = new FileWriter(fichero);
            fw.write("Juab\nAna\nPedro\nLaura");
            fw.close();

            FileReader fr = new FileReader()


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
