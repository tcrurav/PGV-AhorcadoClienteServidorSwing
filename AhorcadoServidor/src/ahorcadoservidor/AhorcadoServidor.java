/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahorcadoservidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiburcio
 */
public class AhorcadoServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PUERTO = 45000; //Son válidos desde el 1 al 65536 (2 bytes)
        ServerSocket socketServer = null;
        Socket socket = null;
        
        try {
            socketServer = new ServerSocket(PUERTO);
            
            System.out.println("Servidor Esperando a que el cliente se conecte");
            
            socket = socketServer.accept();
            
            System.out.println("Cliente se ha conectado.");
            
            InputStream is = socket.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isw);
            
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            // Aquí empieza el trasiego de información
            
            final String palabra = "colcheneta";
            final String FIN = "FIN";
            String palabraAdivinadaHastaElMomento = inicializarPalabraAdivinadaHastaElMomento(palabra);
            String letra;
            
            do {
                bw.write(palabraAdivinadaHastaElMomento);
                bw.newLine();
                bw.flush();
                
                letra = br.readLine();
                
                palabraAdivinadaHastaElMomento = actualizarPalabraAdivinadaHastaElMomento(palabra, palabraAdivinadaHastaElMomento, letra.charAt(0));
                
            } while(!palabraAdivinadaHastaElMomento.equals(palabra));
            
            bw.write(FIN);
            bw.newLine();
            bw.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(AhorcadoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static String inicializarPalabraAdivinadaHastaElMomento(String palabra){
        String resultado = "";
        for (int i = 0; i < palabra.length(); i++) {
            resultado += "-";
        }
        return resultado;
    }
    
    static String actualizarPalabraAdivinadaHastaElMomento(String palabra, String palabraAdivinadaHastaElMomento, char letra){
        String resultado = "";
        for (int i = 0; i < palabra.length(); i++) {
            if(palabra.charAt(i) == letra || palabra.charAt(i) == palabraAdivinadaHastaElMomento.charAt(i)){
                resultado += palabra.charAt(i);
            } else {
                resultado += "-";
            }
        }
        return resultado;
    }
    
}
