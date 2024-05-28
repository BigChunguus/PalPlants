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
//            Usuario u  = new Usuario();
//            u.setNombreUsuario("PruebaInsertar2");
//            u.setContrasena("1234567890123456");
//            u.setEmail("pruebainsertar2@gmail.com");
//            System.out.println(c.insertarUsuario(u));
            //System.out.println(c.leerUsuariosPlantas("BigChungus"));
            //System.out.println(c.eliminarUsuario("PruebaAndroid"));
            //System.out.println(c.leerUsuario("PruebaInsertar"));
            System.out.println(c.insertarUsuarioPlanta(2, 3));
            //System.out.println(c.leerPlanta(1));
            //System.out.println(c.eliminarUsuarioPlanta(1,2));
            //Usuario u = new Usuario();
            //u.setNombre("pruebaupdate");
            //u.setApellido1("pruebaupdate");
            //u.setApellido2("pruebaupdate");
            //u.setDni("34092242D");
            //InteresBotanico ib = new InteresBotanico(1,"");
            //u.setInteres(ib);
            //System.out.println(c.modificarUsuario("Prueba2",u));
            //System.out.println(c.leerPlantas());
            //System.out.println(c.leerGuias(1));
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
