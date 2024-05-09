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
public class Usuario implements Serializable{
    
    public static final long serialVersionUID = 10L;
    private Integer usuarioID;
    private String nombreUsuario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private String dni;
    private String contrasena;
    private InteresBotanico interes;
    
     
    public Usuario() {
    
    }
    
    public Usuario(String nombreUsuario, String nombre, String apellido1, String apellido2, String email, String dni, String contrasena, InteresBotanico interes) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.dni = dni;
        this.contrasena = contrasena;
        this.interes = interes;
    }

    public Usuario(Integer usuarioID, String nombreUsuario, String nombre, String apellido1, String apellido2, String email, String dni, String contrasena, InteresBotanico interes) {
        this.usuarioID = usuarioID;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.dni = dni;
        this.contrasena = contrasena;
        this.interes = interes;
    }

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public InteresBotanico getInteres() {
        return interes;
    }

    public void setInteres(InteresBotanico interes) {
        this.interes = interes;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuarioID=" + usuarioID + ", nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", email=" + email + ", dni=" + dni + ", contrase\u00f1a=" + contrasena + ", interes=" + interes + '}';
    }

    
}
