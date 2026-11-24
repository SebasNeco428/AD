package ActividadXML;

import java.io.*;

public class Hilos {
    static void main(String[] args) {
        File archivoOrigen = new File("Origen.txt");
        File archivoDestino = new File("Destino.txt");
        try {
            PipedOutputStream salida = new PipedOutputStream();
            PipedInputStream entrada = new PipedInputStream(salida);

            Thread HiloProductor = new Thread(()-> {
                try {
                    FileInputStream fis = new FileInputStream("Origen.txt");
                    int dato;
                    while ((dato = fis.read()) != -1) {
                        salida.write(dato);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Thread HiloConsumidor = new Thread(()-> {
                try {
                    FileOutputStream fos = new FileOutputStream("Destino.txt");
                    PipedInputStream input = entrada;
                    int dato;
                    int caracteres = 0;
                    int mensajes = 0;
                    while ((dato == input.read()) != -1) {
                        fos.write(dato);
                        System.out.println((char) dato);
                        caracteres++;
                    }
                    if {

                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });


        }
    }

}
