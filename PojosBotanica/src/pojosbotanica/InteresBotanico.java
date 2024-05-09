/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojosbotanica;

import java.io.Serializable;

/**
 *
 * @author Alejandro
 */
public class InteresBotanico implements Serializable{
    private Integer interesId;
    private String nombreInteres;
    public static final long serialVersionUID = 5L;
     
    public InteresBotanico() {
        this.interesId = 0;
        this.nombreInteres = "";
    }

    public InteresBotanico(Integer interesId, String nombreInteres) {
        this.interesId = interesId;
        this.nombreInteres = nombreInteres;
    }

    public Integer getInteresId() {
        return interesId;
    }

    public void setInteresId(Integer interesId) {
        this.interesId = interesId;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public void setNombreInteres(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }

    @Override
    public String toString() {
        return "InteresBotanico{" + "interesId=" + interesId + ", nombreInteres=" + nombreInteres + '}';
    }
    
    
}
