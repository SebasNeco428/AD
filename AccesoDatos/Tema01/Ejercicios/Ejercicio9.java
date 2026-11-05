package Ejercicios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio9 {
    static void main(String[] args) throws IOException {
        try {

            File fichero = new File("letras.txt");
            FileWriter fw = new FileWriter("letras.txt");
            fw.write("ABCDEFGHIJ");
            fw.close();

            RandomAccessFile raf = new RandomAccessFile("letras.txt", "rw");
            raf.seek(5);
            int codigo = raf.read();
            char caracter = (char) codigo;
            System.out.println("La letra es : " + caracter + ", Y el puntero es :" + raf.getFilePointer());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
