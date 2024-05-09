package pojosbotanica;

import java.io.Serializable;

/**
 *
 * @author Alejandro
 */
public class Insecto implements Serializable{
    
    private Integer insectoId;
    private String nombreCientificoInsecto;
    private String nombreComunInsecto;
    private String descripcion;
    public static final long serialVersionUID = 1L;
    public Insecto() {
    }

    public Insecto(Integer insectoId, String nombreCientificoInsecto, String nombreComunInsecto, String descripcion) {
        this.insectoId = insectoId;
        this.nombreCientificoInsecto = nombreCientificoInsecto;
        this.nombreComunInsecto = nombreComunInsecto;
        this.descripcion = descripcion;
    }

    public Integer getInsectoId() {
        return insectoId;
    }

    public void setInsectoId(Integer insectoId) {
        this.insectoId = insectoId;
    }

    public String getNombreCientificoInsecto() {
        return nombreCientificoInsecto;
    }

    public void setNombreCientificoInsecto(String nombreCientificoInsecto) {
        this.nombreCientificoInsecto = nombreCientificoInsecto;
    }

    public String getNombreComunInsecto() {
        return nombreComunInsecto;
    }

    public void setNombreComunInsecto(String nombreComunInsecto) {
        this.nombreComunInsecto = nombreComunInsecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Insecto{" + "insectoId=" + insectoId + ", nombreCientificoInsecto=" + nombreCientificoInsecto + ", nombreComunInsecto=" + nombreComunInsecto + ", descripcion=" + descripcion + '}';
    }
    
    
}
