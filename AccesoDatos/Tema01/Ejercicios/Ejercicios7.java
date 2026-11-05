package Ejercicios;

import java.io.*;

public class Ejercicios7 {
    static void main(String[] args) {
        try {
            File fichero = new File("Datos_Tema01/bytes.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
            }

            FileWriter fw = new FileWriter(fichero);
            fw.write("Hola");
            fw.close();

            FileInputStream fis = new FileInputStream("Datos_Tema01/bytes.txt");
            int codigo = fis.read();
            char caracater = (char) codigo;
            System.out.println("El caracter es :" + caracater + ", El byte es :" + codigo);

        } catch (IOException e) {
            System.out.println("Error :" + e.getMessage());
        }
    }

}
