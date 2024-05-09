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
public class InsectoPlanta implements Serializable{
    
    private Insecto insecto;
    private Planta planta;
    public static final long serialVersionUID = 2L;
     
    public InsectoPlanta() {
    }

    public InsectoPlanta(Insecto insecto, Planta planta) {
        this.insecto = insecto;
        this.planta = planta;
    }

    public Insecto getInsecto() {
        return insecto;
    }

    public void setInsecto(Insecto insecto) {
        this.insecto = insecto;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        return "InsectoPlanta{" + "insecto=" + insecto + ", planta=" + planta + '}';
    }
    
    
}
