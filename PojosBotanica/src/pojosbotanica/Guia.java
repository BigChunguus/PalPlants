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
public class Guia implements Serializable{
    private Integer guiaId;
    private String titulo;
    private String contenido;
    private Planta plantaId;
    public static final long serialVersionUID = 4L;
    

    public Guia() {
    }

    public Guia(Integer guiaId, String titulo, String contenido, Planta plantaId) {
        this.guiaId = guiaId;
        this.titulo = titulo;
        this.contenido = contenido;
        this.plantaId = plantaId;
    }

    public Integer getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(Integer guiaId) {
        this.guiaId = guiaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Planta getPlantaId() {
        return plantaId;
    }

    public void setPlantaId(Planta plantaId) {
        this.plantaId = plantaId;
    }

    @Override
    public String toString() {
        return "Guia{" + "guiaId=" + guiaId + ", titulo=" + titulo + ", contenido=" + contenido + ", plantaId=" + plantaId + '}';
    }
    
    
}
