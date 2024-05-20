/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botanicasc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojosbotanica.*;


/**
 *
 * @author usuario
 */
public class BotanicaSC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, ExcepcionBotanica {
         try {
            int puertoServidor = 30500;
            ServerSocket socketServidor = new ServerSocket(puertoServidor);
            System.out.println("Servidor ejecutado");
            int i = 1;
            while (true) {
                System.out.println("Petición número: " + i);
                Socket clienteConectado = socketServidor.accept();
                ManejadorPeticion mp = new ManejadorPeticion(clienteConectado);
                mp.start();
                i+=1;
            }
//            socketServidor.close();
        } catch (IOException ex) {
             System.out.println(ex);
        }
    }
    
    
}
