/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Android;

import botanicacc.BotanicaCC;
import java.util.ArrayList;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.*;

/**
 *
 * @author Alejandro
 */
public class SimuladorAndroid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            BotanicaCC c = new BotanicaCC();
            System.out.println(c.leerUsuario("BigChungus"));
            //Usuario u = new Usuario(22, "BigChuunguus", "Prueba", "ApellidoPrueba", "Apellido2Prueba", "alexcruzz267@gmail.com", "21141790Y", new InteresBotanico(1,"a"));
            //System.out.println(c.insertarUsuario(u));
//            ArrayList<Usuario> arr = c.leerUsuarios();
//            for (Usuario usuario : arr) {
//                System.out.println(usuario.toString());
//            }
        } catch (ExcepcionBotanica eb){
            System.out.println(eb);
        }
    }
    
}
