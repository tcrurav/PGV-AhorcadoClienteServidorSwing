/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahorcadocliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiburcio
 */
public class AhorcadoCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String IP = "192.168.1.15";
        final int PUERTO = 45000;
        
        try {
            Socket socket = new Socket(IP,PUERTO);
        
            System.out.println("Esperando a que el servidor conteste.");

            InputStream is = socket.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isw);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            final String FIN = "FIN";
            String palabraAdivinadaHastaElMomento = "";
            Scanner teclado = new Scanner(System.in);

            while (!palabraAdivinadaHastaElMomento.equals(FIN)){
                System.out.println("Esperando la respuesta del Servidor...");
                palabraAdivinadaHastaElMomento = br.readLine();
                
                if(!palabraAdivinadaHastaElMomento.equals(FIN)){
                    System.out.println("Se ha adivinado hasta el momento:");
                    System.out.println(palabraAdivinadaHastaElMomento);
                    System.out.println("Indique una nueva letra para adivinar la palabra secreta:");
                    String letra = teclado.nextLine();

                    bw.write(letra);
                    bw.newLine();
                    bw.flush();
                }
            } 
        
        } catch (IOException ex) {
            Logger.getLogger(AhorcadoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
