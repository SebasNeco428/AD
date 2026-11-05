package Ejercicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio9 {
    static void main() {
        try {
            File fichero = new File("Datos_Tema01/alumnos.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
                System.out.println("Se creo : " + fichero);
            }

            System.out.println("Conseguimos el name : " + fichero.getName());
            System.out.println("El ruta :" + fichero.getPath());
            System.out.println("El ruta absoluta :" + fichero.getAbsolutePath());
            System.out.println("El directorio padre :" + fichero.getParent());
            System.out.println("Obtener el :" + fichero.mkdir());
            System.out.println("Obter el :" + fichero.canWrite());

            FileWriter fw = new FileWriter(fichero);
            fw.write("Juan\nAna\nPedro\nLaura");
            fw.close();

            FileReader fr = new FileReader(fichero);
            int codigo = fr.read();
            char caracter = (char) codigo;
            fr.close();
            System.out.println("El caracter es : " + caracter);

            File directorio = new File("Datos_Tema01/backup1");
            if (!directorio.exists()) {
                directorio.mkdir();
            }

            File archivoOrigen = new File("Datos_Tema01/alumnos.txt");
            File archivoDestino = new File("Datos_Tema01/backup/alumnos.txt");

            if (archivoOrigen.exists()) {
                boolean mv = fichero.renameTo(archivoDestino);
                if (mv) {
                    System.out.println("Fichero movido con exito");
                } else {
                    System.out.println("no se pudo mover");
                }
            } else {
                System.out.println("El archivo a moverse no existe");
            }

            System.out.println("ss" + fichero.lastModified());

        } catch (Exception e) {
            System.out.println("ERROR :" + e.getMessage());
        }

    }
}
