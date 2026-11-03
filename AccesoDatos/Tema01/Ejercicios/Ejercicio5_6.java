package Ejercicios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio5_6 {
    static void main(String[] args) {
        try {
            // Asegúrate de haber creado previamente el fichero "mensaje.txt" con la palabra "Hola"
            File fichero = new File("mensaje.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
            }
            FileWriter fw = new FileWriter("mensaje.txt");
            fw.write("Hola");
            fw.close();

            FileReader lector = new FileReader("mensaje.txt");
            int caracter = lector.read();
            System.out.println((char) caracter);
            lector.close();

            // A) ACCESO SECUENCIAL - Porque necesitamos recorrer los elementos uno detrás de otro desde el inicio hasta el final.
// B) ACCESO ALEATORIO / DIRECTO - Porque permite saltar directamente a una posición concreta sin leer todo lo anterior.
// C) ACCESO SECUENCIAL - Porque se lee el fichero entero de principio a fin para duplicarlo.
// D) ACCESO ALEATORIO / DIRECTO - Porque necesitamos posicionarnos exactamente en el byte o registro a modificar.
// E) ACCESO SECUENCIAL - Porque el texto se lee de forma ordenada desde el primer carácter hasta el último.
        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
    }
}
