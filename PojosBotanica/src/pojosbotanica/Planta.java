package pojosbotanica;

import java.io.Serializable;

/**
 * Representa una planta en el sistema botánico.
 */
public class Planta implements Serializable {

    private static final long serialVersionUID = 7L;
    private Integer plantaId;
    private String nombreCientificoPlanta;
    private String nombreComunPlanta;
    private String descripcion;
    private String tipoPlanta;
    private String cuidadosEspecificos;
    private String imagen; // Atributo para almacenar la imagen en forma de bytes

    public Planta() {
    }

    public Planta(Integer plantaId, String nombreCientificoPlanta, String nombreComunPlanta, String descripcion, String tipoPlanta, String cuidadosEspecificos, String imagen) {
        this.plantaId = plantaId;
        this.nombreCientificoPlanta = nombreCientificoPlanta;
        this.nombreComunPlanta = nombreComunPlanta;
        this.descripcion = descripcion;
        this.tipoPlanta = tipoPlanta;
        this.cuidadosEspecificos = cuidadosEspecificos;
        this.imagen = imagen;
    }

    public Integer getPlantaId() {
        return plantaId;
    }

    public void setPlantaId(Integer plantaId) {
        this.plantaId = plantaId;
    }

    public String getNombreCientificoPlanta() {
        return nombreCientificoPlanta;
    }

    public void setNombreCientificoPlanta(String nombreCientificoPlanta) {
        this.nombreCientificoPlanta = nombreCientificoPlanta;
    }

    public String getNombreComunPlanta() {
        return nombreComunPlanta;
    }

    public void setNombreComunPlanta(String nombreComunPlanta) {
        this.nombreComunPlanta = nombreComunPlanta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(String tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public String getCuidadosEspecificos() {
        return cuidadosEspecificos;
    }

    public void setCuidadosEspecificos(String cuidadosEspecificos) {
        this.cuidadosEspecificos = cuidadosEspecificos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Planta{" +
                "plantaId=" + plantaId +
                ", nombreCientificoPlanta='" + nombreCientificoPlanta + '\'' +
                ", nombreComunPlanta='" + nombreComunPlanta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipoPlanta='" + tipoPlanta + '\'' +
                ", cuidadosEspecificos='" + cuidadosEspecificos + '\'' +
                '}';
    }
}
