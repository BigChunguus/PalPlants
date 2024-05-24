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
    private Double calificacionMedia;
    private Planta plantaId;
    private Usuario usuarioId;
    public static final long serialVersionUID = 4L;
    

    public Guia() {
    }

    public Guia(Integer guiaId, String titulo, String contenido) {
        this.guiaId = guiaId;
        this.titulo = titulo;
        this.contenido = contenido;
    }
    
    public Guia(Integer guiaId, String titulo, String contenido,Double calificacionMedia, Planta plantaId, Usuario usuarioId) {
        this.guiaId = guiaId;
        this.titulo = titulo;
        this.contenido = contenido;
        this.plantaId = plantaId;
        this.usuarioId = usuarioId; 
        this.calificacionMedia = calificacionMedia;
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

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getCalificacionMedia() {
        return calificacionMedia;
    }

    public void setCalificacionMedia(Double calificacionMedia) {
        this.calificacionMedia = calificacionMedia;
    }

    @Override
    public String toString() {
        return "Guia{" + "guiaId=" + guiaId + ", titulo=" + titulo + ", contenido=" + contenido + ", calificacionMedia=" + calificacionMedia + ", plantaId=" + plantaId + ", usuarioId=" + usuarioId + '}';
    }
    
    
    
    
}
