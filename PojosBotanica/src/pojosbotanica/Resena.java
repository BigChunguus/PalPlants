/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojosbotanica;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Alejandro
 */
public class Resena implements Serializable{
    private Integer resenaId;
    private Integer calificacion;
    private String comentario;
    private Date fechaResena;
    private Guia guiaId;
    private Usuario usuarioId;
    public static final long serialVersionUID = 8L;
     
    public Resena() {
    }

    public Resena(Integer resenaId, Integer calificacion, String comentario, Date fechaResena, Guia guiaId, Usuario usuarioId) {
        this.resenaId = resenaId;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fechaResena = fechaResena;
        this.guiaId = guiaId;
        this.usuarioId = usuarioId;
    }

    public Integer getResenaId() {
        return resenaId;
    }

    public void setResenaId(Integer resenaId) {
        this.resenaId = resenaId;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(String fechaResena) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultadoFecha;
        
        try{
            resultadoFecha = sdf.parse(fechaResena);
            this.fechaResena = resultadoFecha;
            
        }catch(ParseException pe){
            System.out.println(pe.getMessage());
        }
        
      
    }

    public Guia getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(Guia guiaId) {
        this.guiaId = guiaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Resena{" + "resenaId=" + resenaId + ", calificacion=" + calificacion + ", comentario=" + comentario + ", fechaResena=" + fechaResena + ", guiaId=" + guiaId + ", usuarioId=" + usuarioId + '}';
    }
}
