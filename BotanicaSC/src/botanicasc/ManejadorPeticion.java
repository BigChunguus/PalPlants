/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botanicasc;

import cadbotanica.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojosbotanica.*;


/**
 *
 * @author usuario
 */
public class ManejadorPeticion extends Thread{
    
    public Socket clienteConectado;

    public ManejadorPeticion(Socket clienteConectado) throws IOException {
        this.clienteConectado = clienteConectado;
    }
    
    
    @Override
    public void run(){
        ObjectInputStream ois = null;
        try {    
            
            ois = new ObjectInputStream(clienteConectado.getInputStream());
            Peticion p = (Peticion) ois.readObject();
            
            if(p.getIdOperacion() == Operaciones.LEER_USUARIOS){
                leerUsuarios(p);
            }
            
            switch (p.getIdOperacion()) {
                case Operaciones.INSERTAR_USUARIO:
                    insertarUsuario(p);
                    break;
                case Operaciones.ELIMINAR_USUARIO:
                    eliminarUsuario(p);
                    break;
                case Operaciones.MODIFICAR_USUARIO:
                    modificarUsuario(p);
                    break;
                case Operaciones.LEER_USUARIO:
                    leerUsuario(p);
                    break;
                case Operaciones.LEER_USUARIOS:
                    leerUsuarios(p);
                    break;
                case Operaciones.INSERTAR_RESENA:
                    insertarResena(p);
                    break;
                case Operaciones.ELIMINAR_RESENA:
                    eliminarResena(p);
                    break;
                case Operaciones.MODIFICAR_RESENA:
                    modificarResena(p);
                    break;      
                case Operaciones.LEER_RESENAS:
                    leerResenas(p);
                    break;
                case Operaciones.LEER_INSECTOS:
                    leerInsectos(p);
                    break;
                case Operaciones.LEER_PLANTA:
                    leerPlanta(p);
                    break;
                case Operaciones.LEER_PLANTAS:
                    leerPlantas(p);
                    break;
                case Operaciones.INSERTAR_GUIA:
                    insertarGuia(p);
                    break;
                case Operaciones.ELIMINAR_GUIA:
                    eliminarGuia(p);
                    break;
                case Operaciones.MODIFICAR_GUIA:
                    modificarGuia(p);
                    break;
                case Operaciones.LEER_GUIAS:
                    leerGuias(p);
                    break;
                case Operaciones.LEER_INTERESES:
                    leerIntereses(p);
                    break;
                case Operaciones.INSERTAR_USUARIO_PLANTA:
                    insertarUsuarioPlanta(p);
                    break;
                case Operaciones.ELIMINAR_USUARIO_PLANTA:
                    eliminarUsuarioPlanta(p);
                    break;
                case Operaciones.LEER_USUARIOS_PLANTA:
                    leerUsuariosPlantas(p);
                    break;
                default:
                    break;
            }

            clienteConectado.close();
            
        } catch (IOException ex) {
            manejadorIOExceptionOIS(ex, ois);
            
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }          
    }
    
    private void manejadorIOExceptionOIS(IOException ex, ObjectInputStream ois){
        System.out.println(ex);
//        if(ois != null){
//            ExcepcionBotanica eh = new ExcepcionBotanica();
//            eh.setMensajeUsuario("Error en la comunicacion. Consulte con el administrador");
//            eh.setMensajeErrorBd(ex.getMessage());
//            Respuesta r = new Respuesta();
//            r.setEh(eh);
//            
//            ObjectOutputStream oos2 = null;
//            try{
//                oos2 = new ObjectOutputStream(clienteConectado.getOutputStream());
//                oos2.writeObject(r);
//                oos2.close();
//            } catch (IOException ex2) {
//                manejadorIOExceptionOOS(ex2, oos2);
//            }
//        }
    }
    private void manejadorIOExceptionOOS(IOException ex, ObjectOutputStream oos){
        System.out.println(ex);
//        if(oos != null){
//            ExcepcionBotanica eh = new ExcepcionBotanica();
//            eh.setMensajeUsuario("Error en la comunicacion. Consulte con el administrador");
//            eh.setMensajeErrorBd(ex.getMessage());
//            Respuesta r = new Respuesta();
//            r.setEh(eh);
//            
//            try{
//                ObjectOutputStream oos2 = new ObjectOutputStream(clienteConectado.getOutputStream());
//                oos2.writeObject(r);
//                oos2.close();
//            } catch (IOException ex2) {
//                System.out.println(ex2);
//            }
//        }
    }
    private void manejadorClassNotFoundException(ClassNotFoundException ex){
        System.out.println(ex);
        
//        ExcepcionBotanica eh = new ExcepcionBotanica();
//        eh.setMensajeUsuario("Error en la comunicacion. Consulte con el administrador");
//        eh.setMensajeErrorBd(ex.getMessage());
//        Respuesta r = new Respuesta();
//        r.setEh(eh);
//        ObjectOutputStream oos = null;
//        try{
//            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
//            oos.writeObject(r);
//            oos.close();
//        } catch (IOException ex2) {
//            manejadorIOExceptionOOS(ex2, oos);
//        }
        
    }
    private void manejadorExcepcionBotanica(ExcepcionBotanica eh){
        System.out.println(eh);
        Respuesta r = new Respuesta();
        r.setEh(eh);
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            clienteConectado.close();
        } catch (IOException ex2) {
            manejadorIOExceptionOOS(ex2, oos);
        }
    }
    
    private void insertarUsuario(Peticion p){
        ObjectOutputStream oos = null;
        try { 
            Usuario u = (Usuario) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            
            int cantidad = cad.insertarUsuario(u);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            
            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        } 
    }
    private void eliminarUsuario(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            String nombreUsuario = (String) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarUsuario(nombreUsuario);
            r.setCantidad(cantidad);

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void modificarUsuario(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Object[] entidadArray = (Object[]) p.getEntidad();
            String stringRecuperado = (String) entidadArray[0];
            Usuario usuarioRecuperado = (Usuario) entidadArray[1];
            CadBotanica cad = new CadBotanica();
           
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarUsuario(stringRecuperado, usuarioRecuperado);
            r.setEntidad(cantidad);

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerUsuario(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            String nombreUsuario = (String) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            Usuario usuario = cad.leerUsuario(nombreUsuario);
            r.setEntidad(usuario);

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerUsuarios(Peticion p){
        ObjectOutputStream oos = null;
        try { 
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerUsuarios());
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            
            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        } 
    } 
    

    private void insertarResena(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Resena resena = (Resena) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.insertarResena(resena);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }

    private void eliminarResena(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            int resenaId = (int) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarResena(resenaId);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }

    private void modificarResena(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Object[] entidadArray = (Object[]) p.getEntidad();
            int intRecuperado = (int) entidadArray[0];
            Resena resenaRecuperado = (Resena) entidadArray[1];
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarResena(intRecuperado,resenaRecuperado);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerResenas(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            int idGuiaResena = (int) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerResenasGuia(idGuiaResena));

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerInsectos(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            int plantaId = (int) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerInsectos(plantaId));

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerPlanta(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            int plantaId = (int) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerPlanta(plantaId));

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerPlantas(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerPlantas());

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void insertarGuia(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Guia guia = (Guia) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.insertarGuia(guia);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    
    private void eliminarGuia(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            System.out.println(p.getEntidad().toString());
            int guiaId = (int) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarGuia(guiaId);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }

    private void modificarGuia(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Object[] entidadArray = (Object[]) p.getEntidad();
            int intRecuperado = (int) entidadArray[0];
            Guia guiaRecuperado = (Guia) entidadArray[1];
            System.out.println(intRecuperado + ""+guiaRecuperado);
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarGuia(intRecuperado, guiaRecuperado);
            r.setCantidad(cantidad);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    
    private void leerGuias(Peticion p) {
        ObjectOutputStream oos = null;
        int plantaIdGuia = (Integer) p.getEntidad();
        try {
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            ArrayList<Guia> guias = cad.leerGuias(plantaIdGuia);
            r.setEntidad(guias);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void leerIntereses(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            ArrayList<InteresBotanico> intereses = cad.leerInteresesBotanicos();
            r.setEntidad(intereses);
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }

private void insertarUsuarioPlanta(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        Object[] entidadArray = (Object[]) p.getEntidad();
        Integer usuarioId = (Integer) entidadArray[0];
        Integer plantaId = (Integer) entidadArray[1];
        System.out.println(usuarioId + "" + plantaId);
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.insertarUsuarioPlanta(usuarioId, plantaId);
        r.setCantidad(cantidad);
        oos = new ObjectOutputStream(clienteConectado.getOutputStream());
        oos.writeObject(r);

        oos.close();
    } catch (IOException ex) {
        manejadorIOExceptionOOS(ex, oos);
    } catch (ExcepcionBotanica ex) {
        manejadorExcepcionBotanica(ex);
    }
}

private void eliminarUsuarioPlanta(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        Object[] entidadArray = (Object[]) p.getEntidad();
        Integer usuarioId = (Integer) entidadArray[0];
        Integer plantaId = (Integer) entidadArray[1];

        System.out.println(usuarioId + "" + plantaId);
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.eliminarUsuarioPlanta(usuarioId, plantaId);
        r.setCantidad(cantidad);
        oos = new ObjectOutputStream(clienteConectado.getOutputStream());
        oos.writeObject(r);

        oos.close();
    } catch (IOException ex) {
        manejadorIOExceptionOOS(ex, oos);
    } catch (ExcepcionBotanica ex) {
        manejadorExcepcionBotanica(ex);
    }
}
private void leerUsuariosPlantas(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        String nombreUsuario = (String) p.getEntidad();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());
        r.setEntidad(cad.leerUsuariosPlantas(nombreUsuario));
        oos = new ObjectOutputStream(clienteConectado.getOutputStream());
        oos.writeObject(r);

        oos.close();
    } catch (IOException ex) {
        manejadorIOExceptionOOS(ex, oos);
    } catch (ExcepcionBotanica ex) {
        manejadorExcepcionBotanica(ex);
    }
}
}
