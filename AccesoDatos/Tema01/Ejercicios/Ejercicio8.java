package Ejercicios;

import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio8 {
    static void main(String[] args)  throws IOException {
        try {
            //fos nos permite escribir y crear fichero mediante bytes
            FileOutputStream fos = new FileOutputStream("Datos_Tema01/salidabytes.txt");
            fos.write(68);
            fos.close();
            System.out.println("Obtener el byte :" + fos);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
