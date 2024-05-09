/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojosbotanica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class Respuesta implements Serializable{
    Integer IdOperacion;
    Integer cantidad;
    Object entidad;
    
    ExcepcionBotanica eh;
    
    public static final long serialVersionUID = 9L;
    
    public Respuesta() {
    }

    public Respuesta(Integer IdOperacion, Integer cantidad, Object entidad, ArrayList<Object> listaEntidades, ExcepcionBotanica eh) {
        this.IdOperacion = IdOperacion;
        this.cantidad = cantidad;
        this.entidad = entidad;
       
        this.eh = eh;
    }

    public Integer getIdOperacion() {
        return IdOperacion;
    }

    public void setIdOperacion(Integer IdOperacion) {
        this.IdOperacion = IdOperacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }

    

    public ExcepcionBotanica getEh() {
        return eh;
    }

    public void setEh(ExcepcionBotanica eh) {
        this.eh = eh;
    }

    @Override
    public String toString() {
        return "Respuesta{" + "IdOperacion=" + IdOperacion + ", cantidad=" + cantidad + ", entidad=" + entidad + ", eh=" + eh + '}';
    }
    
    
}
