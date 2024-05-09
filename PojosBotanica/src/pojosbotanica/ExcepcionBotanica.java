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
public class ExcepcionBotanica extends Exception implements Serializable{
    
    private String mensajeUsuario;
    private String mensajeErrorBd;
    private String sentenciaSQL;
    private Integer codigoErrorSQL;
    public static final long serialVersionUID = 3L;
    
    public ExcepcionBotanica(){
    }

    public ExcepcionBotanica(String mensajeUsuario, String mensajeErrorBd, String sentenciaSQL, Integer codigoErrorSQL) {
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeErrorBd = mensajeErrorBd;
        this.sentenciaSQL = sentenciaSQL;
        this.codigoErrorSQL = codigoErrorSQL;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getMensajeErrorBd() {
        return mensajeErrorBd;
    }

    public void setMensajeErrorBd(String mensajeErrorBd) {
        this.mensajeErrorBd = mensajeErrorBd;
    }

    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    public Integer getCodigoErrorSQL() {
        return codigoErrorSQL;
    }

    public void setCodigoErrorSQL(Integer codigoErrorSQL) {
        this.codigoErrorSQL = codigoErrorSQL;
    }

    @Override
    public String toString() {
        return "ExcepcionHr{" + "mensajeUsuario=" + mensajeUsuario + ", mensajeErrorBd=" + mensajeErrorBd + ", sentenciaSQL=" + sentenciaSQL + ", codigoErrorSQL=" + codigoErrorSQL + '}';
    }
    
    
}
