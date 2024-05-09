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
                case Operaciones.LEER_RESENA:
                    leerResena(p);
                    break;
                case Operaciones.LEER_RESENAS:
                    leerResenas(p);
                    break;
                case Operaciones.INSERTAR_INSECTO:
                    insertarInsecto(p);
                    break;
                case Operaciones.ELIMINAR_INSECTO:
                    eliminarInsecto(p);
                    break;
                case Operaciones.MODIFICAR_INSECTO:
                    modificarInsecto(p);
                    break;
                case Operaciones.LEER_INSECTO:
                    leerInsecto(p);
                    break;
                case Operaciones.LEER_INSECTOS:
                    leerInsectos(p);
                    break;
                case Operaciones.INSERTAR_PLANTA:
                    insertarPlanta(p);
                    break;
                case Operaciones.ELIMINAR_PLANTA:
                    eliminarPlanta(p);
                    break;
                case Operaciones.MODIFICAR_PLANTA:
                    modificarPlanta(p);
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
                case Operaciones.LEER_GUIA:
                    leerGuia(p);
                    break;
                case Operaciones.LEER_GUIAS:
                    leerGuias(p);
                    break;
                case Operaciones.INSERTAR_INTERES:
                    insertarInteres(p);
                    break;
                case Operaciones.ELIMINAR_INTERES:
                    eliminarInteres(p);
                    break;
                case Operaciones.MODIFICAR_INTERES:
                    modificarInteres(p);
                    break;
                case Operaciones.LEER_INTERES:
                    leerInteres(p);
                    break;
                case Operaciones.LEER_INTERESES:
                    leerIntereses(p);
                    break;
                case Operaciones.INSERTAR_INSECTO_PLANTA:
                    insertarInsectoPlanta(p);
                    break;
                case Operaciones.ELIMINAR_INSECTO_PLANTA:
                    eliminarInsectoPlanta(p);
                    break;
                case Operaciones.LEER_INSECTOS_PLANTA:
                    leerInsectosPlantas(p);
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
            Usuario u = (Usuario) p.getEntidad();
            String emailUsuario = u.getEmail(); 
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarUsuario(emailUsuario);
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
    private void modificarUsuario(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Usuario u = (Usuario) p.getEntidad();
            String emailUsuario = u.getEmail(); 
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarUsuario(emailUsuario, u);
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
            Resena resena = (Resena) p.getEntidad();
            int resenaId = resena.getResenaId();
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
            Resena resena = (Resena) p.getEntidad();
            int resenaId = resena.getResenaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarResena(resenaId,resena);
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

    private void leerResena(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Resena resena = (Resena) p.getEntidad();
            int resenaId = resena.getResenaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerResena(resenaId));

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
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerResenas());

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }

    private void insertarInsecto(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Insecto insecto = (Insecto) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.insertarInsecto(insecto);
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

    private void eliminarInsecto(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Insecto insecto = (Insecto) p.getEntidad();
            int insectoId = insecto.getInsectoId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarInsecto(insectoId);
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

    private void modificarInsecto(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Insecto insecto = (Insecto) p.getEntidad();
            int insectoId = insecto.getInsectoId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarInsecto(insectoId, insecto);
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


    private void leerInsecto(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Insecto insecto = (Insecto) p.getEntidad();
            int insectoId = insecto.getInsectoId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerInsecto(insectoId));

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
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerInsectos());

            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);

            oos.close();
        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionBotanica ex) {
            manejadorExcepcionBotanica(ex);
        }
    }
    private void insertarPlanta(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Planta planta = (Planta) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.insertarPlanta(planta);
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
    private void eliminarPlanta(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Planta planta = (Planta) p.getEntidad();
            int plantaId = planta.getPlantaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.eliminarPlanta(plantaId);
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
    private void modificarPlanta(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Planta planta = (Planta) p.getEntidad();
            int plantaId = planta.getPlantaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarPlanta(plantaId, planta);
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
    private void leerPlanta(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Planta planta = (Planta) p.getEntidad();
            int plantaId = planta.getPlantaId();
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
            Guia guia = (Guia) p.getEntidad();
            int guiaId = guia.getGuiaId();
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
            Guia guia = (Guia) p.getEntidad();
            int guiaId = guia.getGuiaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarGuia(guiaId, guia);
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
    
    private void leerGuia(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            Guia guia = (Guia) p.getEntidad();
            int guiaId = guia.getGuiaId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerGuia(guiaId));
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
        try {
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            ArrayList<Guia> guias = cad.leerGuias();
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
    private void insertarInteres(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            InteresBotanico interes = (InteresBotanico) p.getEntidad();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.insertarInteresBotanico(interes);
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
    private void eliminarInteres(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        InteresBotanico interes = (InteresBotanico) p.getEntidad();
        int interesId = interes.getInteresId();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.eliminarInteresBotanico(interesId);
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

    private void modificarInteres(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            InteresBotanico interes = (InteresBotanico) p.getEntidad();
            int interesId = interes.getInteresId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());

            int cantidad = cad.modificarInteresBotanico(interesId, interes);
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
    private void leerInteres(Peticion p) {
        ObjectOutputStream oos = null;
        try {
            InteresBotanico interes = (InteresBotanico) p.getEntidad();
            int interesId = interes.getInteresId();
            CadBotanica cad = new CadBotanica();
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdOperacion());
            r.setEntidad(cad.leerInteresBotanico(interesId));
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
    private void insertarInsectoPlanta(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        InsectoPlanta insectoPlanta = (InsectoPlanta) p.getEntidad();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.insertarInsectoPlanta(insectoPlanta);
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
private void eliminarInsectoPlanta(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        InsectoPlanta insectoPlanta = (InsectoPlanta) p.getEntidad();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.eliminarInsectoPlanta(insectoPlanta);
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
private void leerInsectosPlantas(Peticion p) {
    ObjectOutputStream oos = null;
    try {
        Planta planta = (Planta) p.getEntidad();
        int plantaId = planta.getPlantaId();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());
        r.setEntidad(cad.leerInsectosPlantas(plantaId));
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
        UsuarioPlanta usuarioPlanta = (UsuarioPlanta) p.getEntidad();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());

        int cantidad = cad.insertarUsuarioPlanta(usuarioPlanta);
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
        Usuario usuario = (Usuario) p.getEntidad();
        int usuarioId = usuario.getUsuarioID();
        CadBotanica cad = new CadBotanica();
        Respuesta r = new Respuesta();
        r.setIdOperacion(p.getIdOperacion());
        r.setEntidad(cad.leerUsuariosPlantas(usuarioId));
        oos = new ObjectOutputStream(clienteConectado.getOutputStream());
        oos.writeObject(r);

        oos.close();
    } catch (IOException ex) {
        manejadorIOExceptionOOS(ex, oos);
    } catch (ExcepcionBotanica ex) {
        manejadorExcepcionBotanica(ex);
    }
}















//    public void gestionarDialogo(Socket clienteSocket){
//        try{
//            System.out.println("Servidor.Consola - El servidor recibe un objeto Coche del cliente");
//            ObjectInputStream ois = new ObjectInputStream(clienteConectado.getInputStream());
//            String nombre = (String) ois.readObject();
//            System.out.println("Nombre recibido del cliente" + nombre);
//            
//            System.out.println("El servidor responde al cliente");
//            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
//            oos.writeObject("Hola Don " + nombre);
//            Thread.sleep(5000);
//            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
//            oos.writeObject("Adios Don " + nombre);
//            ois.close();
//            oos.close();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        } catch (ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BotanicaSC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
