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
public class UsuarioPlanta implements Serializable{
    private Planta planta;
    private Usuario usuario;
     public static final long serialVersionUID = 11L;
     
    public UsuarioPlanta() {
    }

    public UsuarioPlanta(Planta planta, Usuario usuario) {
        this.planta = planta;
        this.usuario = usuario;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "UsuarioPlanta{" + "planta=" + planta + ", usuario=" + usuario + '}';
    }
    
    
}
