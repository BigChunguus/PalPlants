/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botanicacc;

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
 * @author Alejandro
 */
public class BotanicaCC {

    /**
     * @param args the command line arguments
     */
    Socket socketCliente;
    
    private void manejadorIOException(IOException ex) throws ExcepcionBotanica{
        ExcepcionBotanica eb = new ExcepcionBotanica();
        eb.setMensajeUsuario("Fallo en las comunicaciones. Consulte con el administrador");
        eb.setMensajeErrorBd(ex.getMessage());
        throw eb;
    }
    private void manejadorClassNotFoundException(ClassNotFoundException ex) throws ExcepcionBotanica{
        ExcepcionBotanica eb = new ExcepcionBotanica();
        eb.setMensajeUsuario("Error general en el sistema. Consulte con el administrador");
        eb.setMensajeErrorBd(ex.getMessage());
        throw eb;
    }
    
    
    public BotanicaCC() throws ExcepcionBotanica {
        try {
            String ip = "192.168.0.207";
            int puertoServidor = 30500;
            socketCliente = new Socket(ip, puertoServidor);
            socketCliente.setSoTimeout(5000);
        } catch (IOException ex) {
            manejadorIOException(ex);
        }
    }
    
    public InteresBotanico leerInteres(String nombreInteres) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_USUARIO);
    p.setEntidad(nombreInteres);
    
    Respuesta r = null;
    InteresBotanico interes = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        r = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (r.getEntidad() != null)
            interes = (InteresBotanico) r.getEntidad();
        else if (r.getEh() != null)
            throw r.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return interes;
}
    public ArrayList<InteresBotanico> leerIntereses() throws ExcepcionBotanica {
        
        Peticion p = new Peticion();
        p.setIdOperacion(Operaciones.LEER_INTERESES);
        
        Respuesta r = null;
        ArrayList<InteresBotanico> listaIntereses = null;
        
        try{
            
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();
            
            ois.close();
            oos.close();
            socketCliente.close();
            
            if(r.getEntidad() != null)
                listaIntereses = (ArrayList<InteresBotanico>) r.getEntidad();    
            else if (r.getEh() != null)
                throw r.getEh();
            
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        } catch(IOException ex){
            manejadorIOException(ex);
        }
        return listaIntereses;
    }
    
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionBotanica {
        
        
        Peticion p = new Peticion();
        p.setIdOperacion(Operaciones.LEER_USUARIOS);
        
        
        Respuesta r = null;
        ArrayList<Usuario> listaUsuarios = null;
        
        try{
            
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();
            
            ois.close();
            oos.close();
            socketCliente.close();
            
            if(r.getEntidad() != null)
                listaUsuarios = (ArrayList<Usuario>) r.getEntidad();    
            else if (r.getEh() != null)
                throw r.getEh();
            
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        } catch(IOException ex){
            manejadorIOException(ex);
        }
        return listaUsuarios;
    }
    
    public int insertarUsuario(Usuario u) throws ExcepcionBotanica{
        
        Peticion p = new Peticion();
        p.setIdOperacion(Operaciones.INSERTAR_USUARIO);
        p.setEntidad(u);
        Respuesta r = null;
        int cambios = 0;
        
        try{
            
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();
            
            ois.close();
            oos.close();
            socketCliente.close();
            
            if(r.getCantidad() != null)
                cambios = (int) r.getCantidad();    
            else if (r.getEh() != null)
                throw r.getEh();
            
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        } catch(IOException ex){
            manejadorIOException(ex);
        }
        return cambios;
    }
    public int eliminarUsuario(String nombreUsuario) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.ELIMINAR_USUARIO);
    p.setEntidad(nombreUsuario);
    
    Respuesta r = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        r = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (r.getCantidad() != null)
            cambios = (int) r.getCantidad();
        else if (r.getEh() != null)
            throw r.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int modificarUsuario(String nombreUsuario, Usuario u) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.MODIFICAR_USUARIO);
    
    System.out.println(nombreUsuario + u);
    Object[] parametros = new Object[2];
    parametros[0] = nombreUsuario;
    parametros[1] = u;
    
    p.setEntidad(parametros);
    
    Respuesta r = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        r = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (r.getCantidad() != null)
            cambios = (int) r.getCantidad();
        else if (r.getEh() != null)
            throw r.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public Usuario leerUsuario(String nombreUsuario) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_USUARIO);
    p.setEntidad(nombreUsuario);
    
    Respuesta r = null;
    Usuario usuario = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        r = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (r.getEntidad() != null)
            usuario = (Usuario) r.getEntidad();
        else if (r.getEh() != null)
            throw r.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return usuario;
}

public int insertarResena(Resena r) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.INSERTAR_RESENA);
    p.setEntidad(r);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarResena(int idResena) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.ELIMINAR_RESENA);
    p.setIdEntidad(idResena);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int modificarResena(Resena r) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.MODIFICAR_RESENA);
    p.setEntidad(r);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public Resena leerResena(int idResena) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_RESENA);
    p.setEntidad(idResena);
    
    Respuesta respuesta = null;
    Resena resena = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            resena = (Resena) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return resena;
}

public ArrayList<Resena> leerResenas(int idGuia) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_RESENAS);
    p.setEntidad(idGuia);
    Respuesta respuesta = null;
    ArrayList<Resena> listaResenas = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaResenas = (ArrayList<Resena>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaResenas;
}
public int insertarInsecto(Insecto i) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.INSERTAR_INSECTO);
    p.setEntidad(i);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarInsecto(int idInsecto) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.ELIMINAR_INSECTO);
    p.setIdEntidad(idInsecto);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int modificarInsecto(Insecto i) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.MODIFICAR_INSECTO);
    p.setEntidad(i);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public Insecto leerInsecto(int idInsecto) throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_INSECTO);
    p.setIdEntidad(idInsecto);
    
    Respuesta respuesta = null;
    Insecto insecto = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            insecto = (Insecto) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return insecto;
}

public ArrayList<Insecto> leerInsectos() throws ExcepcionBotanica {
    Peticion p = new Peticion();
    p.setIdOperacion(Operaciones.LEER_INSECTOS);
    
    Respuesta respuesta = null;
    ArrayList<Insecto> listaInsectos = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaInsectos = (ArrayList<Insecto>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaInsectos;
}
public int insertarPlanta(Planta p) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.INSERTAR_PLANTA);
    peticion.setEntidad(p);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarPlanta(int idPlanta) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.ELIMINAR_PLANTA);
    peticion.setIdEntidad(idPlanta);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int modificarPlanta(Planta p) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.MODIFICAR_PLANTA);
    peticion.setEntidad(p);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public Planta leerPlanta(int idPlanta) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_PLANTA);
    peticion.setEntidad(idPlanta);
    
    Respuesta respuesta = null;
    Planta planta = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            planta = (Planta) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return planta;
}

public ArrayList<Planta> leerPlantas() throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_PLANTAS);
    
    Respuesta respuesta = null;
    ArrayList<Planta> listaPlantas = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaPlantas = (ArrayList<Planta>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaPlantas;
}
public int insertarGuia(Guia g) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.INSERTAR_GUIA);
    peticion.setEntidad(g);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarGuia(int idGuia) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.ELIMINAR_GUIA);
    peticion.setIdEntidad(idGuia);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int modificarGuia(Guia g) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.MODIFICAR_GUIA);
    peticion.setEntidad(g);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public Guia leerGuia(int idGuia) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_GUIA);
    peticion.setIdEntidad(idGuia);
    
    Respuesta respuesta = null;
    Guia guia = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            guia = (Guia) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return guia;
}

public ArrayList<Guia> leerGuias(int plantaIdGuia) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_GUIAS);
    peticion.setEntidad(plantaIdGuia);
    
    Respuesta respuesta = null;
    ArrayList<Guia> listaGuias = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaGuias = (ArrayList<Guia>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaGuias;
}
public int insertarInsectoPlanta(InsectoPlanta ip) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.INSERTAR_INSECTO_PLANTA);
    peticion.setEntidad(ip);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarInsectoPlanta(int idInsectoPlanta) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.ELIMINAR_INSECTO_PLANTA);
    peticion.setIdEntidad(idInsectoPlanta);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public ArrayList<InsectoPlanta> leerInsectosPlanta() throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_INSECTOS_PLANTA);
    
    Respuesta respuesta = null;
    ArrayList<InsectoPlanta> listaInsectosPlanta = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaInsectosPlanta = (ArrayList<InsectoPlanta>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaInsectosPlanta;
}

public int insertarUsuarioPlanta(int idUsuario, int idPlanta) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.INSERTAR_USUARIO_PLANTA);
    Object[] parametros = new Object[2];
    parametros[0] = idUsuario;
    parametros[1] = idPlanta;
    
    peticion.setEntidad(parametros);
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public int eliminarUsuarioPlanta(int idUsuario, int idPlanta) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.ELIMINAR_USUARIO_PLANTA);
    
    Object[] parametros = new Object[2];
    parametros[0] = idUsuario;
    parametros[1] = idPlanta;
    
    peticion.setEntidad(parametros);
    
    Respuesta respuesta = null;
    int cambios = 0;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getCantidad() != null)
            cambios = (int) respuesta.getCantidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return cambios;
}

public ArrayList<Planta> leerUsuariosPlantas(String nombreUsuario) throws ExcepcionBotanica {
    Peticion peticion = new Peticion();
    peticion.setIdOperacion(Operaciones.LEER_USUARIOS_PLANTA);
    peticion.setEntidad(nombreUsuario);
    
    Respuesta respuesta = null;
    ArrayList<Planta> listaPlantas = null;
    
    try {
        ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
        oos.writeObject(peticion);
        ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
        respuesta = (Respuesta) ois.readObject();
        
        ois.close();
        oos.close();
        socketCliente.close();
        
        if (respuesta.getEntidad() != null)
            listaPlantas = (ArrayList<Planta>) respuesta.getEntidad();
        else if (respuesta.getEh() != null)
            throw respuesta.getEh();
        
    } catch (ClassNotFoundException ex) {
        manejadorClassNotFoundException(ex);
    } catch (IOException ex) {
        manejadorIOException(ex);
    }
    
    return listaPlantas;
}

}
