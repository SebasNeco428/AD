package Ejercicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio12 {
    static void main(String[] args) throws IOException {
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

            FileReader fr = new FileReader(fichero);
            int codigo = fr.read();
            char caracter = (char) codigo;
            System.out.println("El primer caracter es :" + caracter);

            File directorio = new File("Datos_tema01/backup2");
            if (!directorio.exists()){
                directorio.mkdir();
            }

            File fileOrigen = new File("Datos_Tema01/ficheros.txt");
            File fileDestino = new File("Datos_Tema01/backup2/ficheros.txt");

            if (fileOrigen.exists()) {
                boolean mv = fichero.renameTo(fileDestino);
                if (mv) {
                    System.out.println("Fichero movido con exito");
                } else {
                    System.out.println("no se pudo mover");
                }
            } else {
                System.out.println("El archivo a moverse no existe");
            }

            System.out.println("Ultima modificacion :" + fichero.lastModified());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
