/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojosbotanica;

import java.io.Serializable;

/**
 *
 * @author Alejandro
 */
public class Peticion implements Serializable{
    Integer IdOperacion;
    Integer IdEntidad;
    Object entidad;
    public static final long serialVersionUID = 6L;
    
    public Peticion() {
    }

    public Peticion(Integer IdOperacion, Integer IdEntidad, Object entidad) {
        this.IdOperacion = IdOperacion;
        this.IdEntidad = IdEntidad;
        this.entidad = entidad;
    }

    public Integer getIdOperacion() {
        return IdOperacion;
    }

    public void setIdOperacion(Integer IdOperacion) {
        this.IdOperacion = IdOperacion;
    }

    public Integer getIdEntidad() {
        return IdEntidad;
    }

    public void setIdEntidad(Integer IdEntidad) {
        this.IdEntidad = IdEntidad;
    }

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }
    
    
}
