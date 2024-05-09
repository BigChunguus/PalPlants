/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadbotanica;



import java.sql.*;
import java.sql.Statement;
import java.util.*;
import pojosbotanica.*;
/**
 * En esta clase se muestra:
 *  1. Cómo ejecutar sentencias DML sobre una BD Oracle
 * @author Alejandro
 * @version 1.0
 * @since AaD 1.0
*/
public class CadBotanica {

    private Connection conexion;
    
    /**
     * Crea el driver de conexion, al hacer el constructor
     * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
    */
    public CadBotanica() throws ExcepcionBotanica{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException ex) {
            ExcepcionBotanica eh = new ExcepcionBotanica();
            eh.setMensajeErrorBd(ex.getMessage());
            eh.setMensajeUsuario("Error general de sistema. Consulte con un administrador");
            throw eh;
        }
    }
    /**
     * Permite crear la conexión con la BB.DD. cada vez que se llama.
     * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
    */
    public void conectar() throws ExcepcionBotanica{
        try{
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "BOTANICA", "kk");
            
        }catch(SQLException ex){
            ExcepcionBotanica eh = new ExcepcionBotanica();
            eh.setCodigoErrorSQL(ex.getErrorCode());
            eh.setMensajeErrorBd(ex.getMessage());
            eh.setMensajeUsuario("Error general de sistema. Consulte con un administrador");
            throw eh;
        }
    }
    
    public void desconectar() throws ExcepcionBotanica{
        try{
            conexion.close();
        }catch(SQLException ex){
            ExcepcionBotanica eh = new ExcepcionBotanica();
            eh.setCodigoErrorSQL(ex.getErrorCode());
            eh.setMensajeErrorBd(ex.getMessage());
            eh.setMensajeUsuario("Error general de sistema. Consulte con un administrador");
            throw eh;
        }
    }
    /**
     * Permite introducir una clase Usuario en la tabla "Usuario" de la BB.DD.
     * @return devuelve la cantidad de campos insertados en la tabla.
     * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
    */
    public int insertarUsuario(Usuario u) throws ExcepcionBotanica {
    conectar();
    int registrosAfectados = 0;
    String dml = "INSERT INTO USUARIO (NOMBREUSUARIO, CONTRASEÑA, EMAIL) VALUES (?, ?, ?)";

    try {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

        sentenciaPreparada.setString(1, u.getNombreUsuario());
        sentenciaPreparada.setString(2, u.getContrasena());
        sentenciaPreparada.setString(3, u.getEmail());
     
        registrosAfectados = sentenciaPreparada.executeUpdate();
        
        sentenciaPreparada.close();
        

    } catch (SQLException ex) {
        ExcepcionBotanica eh = new ExcepcionBotanica();
        eh.setCodigoErrorSQL(ex.getErrorCode());
        eh.setMensajeErrorBd(ex.getMessage());
        eh.setSentenciaSQL(dml);
        switch (ex.getErrorCode()) {
            case 2291:
                eh.setMensajeUsuario("Error: El interés seleccionado no existe");
                break;
            case 1407:
                eh.setMensajeUsuario("Error: Los siguientes datos son obligatorios: \nNombre\nApellido1\nDNI\nEmail");
                break;
            case 2290:
                eh.setMensajeUsuario("Error: La dirección debe ser '@gmail.com'");
                break;
            case 1:
                eh.setMensajeUsuario("Error: El email no puede repetirse");
                break;
            default:
                eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
                break;
        }

        throw eh;
    } finally {
        desconectar();
    }
    return registrosAfectados;
}

    
    /**
 * Permite modificar el usuario con el email dado.
 * @return devuelve la cantidad de campos modificados en la tabla.
 * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
*/
public int modificarUsuario(String email, Usuario usuario) throws ExcepcionBotanica {
    conectar();
    int registrosAfectados = 0;
    String dml = "UPDATE USUARIO SET ";
    boolean primerCampo = true;

    if (usuario.getNombreUsuario() != null) {
        dml += "NOMBREUSUARIO=?, ";
        primerCampo = false;
    }
    if (usuario.getApellido1() != null) {
        dml += (primerCampo ? "" : ", ") + "APELLIDO1=?, ";
        primerCampo = false;
    }
    if (usuario.getApellido2() != null) {
        dml += (primerCampo ? "" : ", ") + "APELLIDO2=?, ";
        primerCampo = false;
    }
    if (usuario.getDni() != null) {
        dml += (primerCampo ? "" : ", ") + "DNI=?, ";
        primerCampo = false;
    }
    if (usuario.getContrasena() != null) {
        dml += (primerCampo ? "" : ", ") + "CONTRASEÑA=?, ";
        primerCampo = false;
    }
    if (usuario.getInteres() != null) {
        dml += (primerCampo ? "" : ", ") + "INTERESBOTANICOINTERESID=? ";
    }
    
    dml += " WHERE EMAIL=?";

    try {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

        int contador = 1;
        if (usuario.getNombreUsuario() != null) {
            sentenciaPreparada.setString(contador++, usuario.getNombreUsuario());
        }
        if (usuario.getApellido1() != null) {
            sentenciaPreparada.setString(contador++, usuario.getApellido1());
        }
        if (usuario.getApellido2() != null) {
            sentenciaPreparada.setString(contador++, usuario.getApellido2());
        }
        if (usuario.getDni() != null) {
            sentenciaPreparada.setString(contador++, usuario.getDni());
        }
        if (usuario.getContrasena() != null) {
            sentenciaPreparada.setString(contador++, usuario.getContrasena());
        }
        if (usuario.getInteres() != null) {
            sentenciaPreparada.setInt(contador++, usuario.getInteres().getInteresId());
        }
        sentenciaPreparada.setString(contador, email);

        registrosAfectados = sentenciaPreparada.executeUpdate();
        
        sentenciaPreparada.close();
        conexion.close();
        
    } catch (SQLException ex) {
        ExcepcionBotanica eh = new ExcepcionBotanica();
        eh.setCodigoErrorSQL(ex.getErrorCode());
        eh.setMensajeErrorBd(ex.getMessage());
        eh.setSentenciaSQL(dml);
        switch (ex.getErrorCode()) {
            case 2291:
                eh.setMensajeUsuario("Error: El interés seleccionado no existe");
                break;
            case 1407:
                eh.setMensajeUsuario("Error: Los siguientes datos son obligatorios: \nNombre\nApellido1\nDNI\nEmail");
                break;
            case 2290:
                eh.setMensajeUsuario("Error: La dirección debe ser '@gmail.com'");
                break;
            case 1:
                eh.setMensajeUsuario("Error: El email no puede repetirse");
                break;
            default:
                eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
                break;
        }
        throw eh;
    }
    return registrosAfectados;
}

    
    /**
    * Permite eliminar el usuario con el email dado.
    * @return devuelve la cantidad de campos eliminados en la tabla.
    * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
   */
   public int eliminarUsuario(String email) throws ExcepcionBotanica {
       conectar();
       int registrosAfectados = 0;
       String dml = "DELETE FROM USUARIO WHERE EMAIL = ?";

       try {
           PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
           sentenciaPreparada.setString(1, email);

           registrosAfectados = sentenciaPreparada.executeUpdate();

           sentenciaPreparada.close();
           conexion.close();

       } catch (SQLException ex) {
           ExcepcionBotanica eh = new ExcepcionBotanica();
           eh.setCodigoErrorSQL(ex.getErrorCode());
           eh.setMensajeErrorBd(ex.getMessage());
           eh.setSentenciaSQL(dml);
           switch (ex.getErrorCode()) {
               case 2292:
                   eh.setMensajeUsuario("Error: No se puede eliminar el usuario porque tiene dependencias en otras tablas");
                   break;
               default:
                   eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
                   break;
           }
           throw eh;
       }
       return registrosAfectados;
   }

    /**
    * Permite buscar un usuario por su email.
    * @return devuelve el usuario que coincide con el email dado.
    * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
   */
   public Usuario leerUsuario(String nombreUsuario) throws ExcepcionBotanica {
       conectar();
       String dml = "SELECT U.USUARIOID, U.CORREO, U.NOMBRE, U.APELLIDO1, U.APELLIDO2, U.DNI, U.CONTRASEÑA, U.INTERESBOTANICOINTERESID, I.NOMBREINTERES " +
            "FROM USUARIO U " +
            "JOIN INTERESBOTANICO I ON U.INTERESBOTANICOINTERESID = I.INTERESID " +
            "WHERE U.NOMBREUSUARIO = '" + nombreUsuario + "'";
       
       Usuario lu = new Usuario();
       
       try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(dml);
            System.out.println(dml);
           if (resultSet.next()) {
               Integer usuarioID = resultSet.getInt("USUARIOID");
               
               String nombre = resultSet.getString("NOMBRE");
               String apellido1 = resultSet.getString("APELLIDO1");
               String apellido2 = resultSet.getString("APELLIDO2");
               String dni = resultSet.getString("DNI");
               String correo = resultSet.getString("CORREO");
               String contraseña = resultSet.getString("CONTRASEÑA");
               Integer interesId = resultSet.getInt("INTERESBOTANICOINTERESID");
               String nombreInteres = resultSet.getString("NOMBREINTERES");
               
               System.out.println("Resultados");
               InteresBotanico interesBotanico = new InteresBotanico(interesId, nombreInteres);
               lu = new Usuario(usuarioID, nombreUsuario, nombre, apellido1, apellido2, correo, dni, contraseña, interesBotanico);

               statement.close();
               resultSet.close();
               conexion.close();
           } else 
               System.out.println("Sin resultados");

       } catch (SQLException ex) {
           ExcepcionBotanica eh = new ExcepcionBotanica();
           eh.setCodigoErrorSQL(ex.getErrorCode());
           eh.setMensajeErrorBd(ex.getMessage());
           eh.setSentenciaSQL(dml);
           eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
           throw eh;
       }
       return lu;
   }
    /**
    * Permite mostrar todos los usuarios de la tabla Usuario.
    * @return devuelve el Array con la lista de usuarios.
    * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
   */
   public ArrayList<Usuario> leerUsuarios() throws ExcepcionBotanica {
       conectar();
       ArrayList<Usuario> listaUsuarios = new ArrayList<>();

       String dml = "SELECT U.USUARIOID, U.NOMBREUSUARIO, U.NOMBRE, U.APELLIDO1, U.APELLIDO2, U.CORREO, U.DNI, U.CONTRASEÑA, U.INTERESBOTANICOINTERESID, I.NOMBREINTERES " +
                    "FROM USUARIO U " +
                    "JOIN INTERESBOTANICO I ON U.INTERESBOTANICOINTERESID = I.INTERESID";

       try {
           Statement statement = conexion.createStatement();
           ResultSet resultSet = statement.executeQuery(dml);

           while (resultSet.next()) {
               Integer usuarioId = resultSet.getInt("USUARIOID");
               String nombreUsuario = resultSet.getString("NOMBREUSUARIO");
               String nombre = resultSet.getString("NOMBRE");
               String apellido1 = resultSet.getString("APELLIDO1");
               String apellido2 = resultSet.getString("APELLIDO2");
               String email = resultSet.getString("CORREO");
               String dni = resultSet.getString("DNI");
               String contraseña = resultSet.getString("CONTRASEÑA");
               Integer interesId = resultSet.getInt("INTERESBOTANICOINTERESID");
               String nombreInteres = resultSet.getString("NOMBREINTERES");

               InteresBotanico interesBotanico = new InteresBotanico(interesId, nombreInteres);
               Usuario usuario = new Usuario(usuarioId, nombreUsuario, nombre, apellido1, apellido2, email, dni, contraseña, interesBotanico);
               listaUsuarios.add(usuario);
           }

           resultSet.close();
           statement.close();
           conexion.close();

       } catch (SQLException ex) {
           ExcepcionBotanica eh = new ExcepcionBotanica();
           eh.setCodigoErrorSQL(ex.getErrorCode());
           eh.setMensajeErrorBd(ex.getMessage());
           eh.setSentenciaSQL(dml);
           eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
           throw eh;
       }

       return listaUsuarios;
    }
   
   
   
   public int insertarResena(Resena r) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar una reseña en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int modificarResena(Integer resenaId, Resena resena) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para modificar una reseña en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarResena(Integer resenaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar una reseña de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public Resena leerResena(Integer resenaId) throws ExcepcionBotanica {
        conectar();
        Resena resena = null;
        // Código para leer una reseña de la base de datos
        desconectar();
        return resena; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<Resena> leerResenas() throws ExcepcionBotanica {
        conectar();
        ArrayList<Resena> listaResenas = new ArrayList<>();
        // Código para leer todas las reseñas de la base de datos
        desconectar();
        return listaResenas; // Cambiar el valor de retorno según corresponda
    }

    public int insertarGuia(Guia g) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar una guía en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int modificarGuia(Integer guiaId, Guia guia) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para modificar una guía en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarGuia(Integer guiaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar una guía de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public Guia leerGuia(Integer guiaId) throws ExcepcionBotanica {
        conectar();
        Guia guia = null;
        // Código para leer una guía de la base de datos
        desconectar();
        return guia; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<Guia> leerGuias() throws ExcepcionBotanica {
        conectar();
        ArrayList<Guia> listaGuias = new ArrayList<>();
        // Código para leer todas las guías de la base de datos
        desconectar();
        return listaGuias; // Cambiar el valor de retorno según corresponda
    }
    
    public int insertarInsecto(Insecto insecto) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar un insecto en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int modificarInsecto(Integer insectoId, Insecto insecto) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para modificar un insecto en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarInsecto(Integer insectoId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar un insecto de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public Insecto leerInsecto(Integer insectoId) throws ExcepcionBotanica {
        conectar();
        Insecto insecto = null;
        // Código para leer un insecto de la base de datos
        desconectar();
        return insecto; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<Insecto> leerInsectos() throws ExcepcionBotanica {
        conectar();
        ArrayList<Insecto> listaInsectos = new ArrayList<>();
        // Código para leer todos los insectos de la base de datos
        desconectar();
        return listaInsectos; // Cambiar el valor de retorno según corresponda
    }

    public int insertarInsectoPlanta(InsectoPlanta insectoPlanta) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar una relación InsectoPlanta en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarInsectoPlanta(InsectoPlanta insectoPlanta) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar una relación InsectoPlanta de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }
    public ArrayList<InsectoPlanta> leerInsectosPlantas(Integer plantaId) throws ExcepcionBotanica {
        conectar();
        ArrayList<InsectoPlanta> listaInsectosPlantas = new ArrayList<>();
        // Código para leer todas las relaciones InsectoPlanta de la base de datos
        desconectar();
        return listaInsectosPlantas; // Cambiar el valor de retorno según corresponda
    }
    
    public int insertarInteresBotanico(InteresBotanico interes) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar un interés botánico en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int modificarInteresBotanico(Integer interesId, InteresBotanico interes) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para modificar un interés botánico en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarInteresBotanico(Integer interesId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar un interés botánico de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public InteresBotanico leerInteresBotanico(Integer interesId) throws ExcepcionBotanica {
        conectar();
        InteresBotanico interes = null;
        // Código para leer un interés botánico de la base de datos
        desconectar();
        return interes; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<InteresBotanico> leerInteresesBotanicos() throws ExcepcionBotanica {
        conectar();
        ArrayList<InteresBotanico> listaIntereses = new ArrayList<>();
        // Código para leer todos los intereses botánicos de la base de datos
        desconectar();
        return listaIntereses; // Cambiar el valor de retorno según corresponda
    }
    
    public int insertarPlanta(Planta planta) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar una planta en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int modificarPlanta(Integer plantaId, Planta planta) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para modificar una planta en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarPlanta(Integer plantaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar una planta de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public Planta leerPlanta(Integer plantaId) throws ExcepcionBotanica {
        conectar();
        Planta planta = null;
        // Código para leer una planta de la base de datos
        desconectar();
        return planta; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<Planta> leerPlantas() throws ExcepcionBotanica {
        conectar();
        ArrayList<Planta> listaPlantas = new ArrayList<>();
        // Código para leer todas las plantas de la base de datos
        desconectar();
        return listaPlantas; // Cambiar el valor de retorno según corresponda
    }

    public int insertarUsuarioPlanta(UsuarioPlanta usuarioPlanta) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para insertar una relación InsectoPlanta en la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }

    public int eliminarUsuarioPlanta(Integer usuarioId, Integer plantaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        // Código para eliminar una relación InsectoPlanta de la base de datos
        desconectar();
        return registrosAfectados; // Cambiar el valor de retorno según corresponda
    }
    public ArrayList<UsuarioPlanta> leerUsuariosPlantas(Integer usuarioId) throws ExcepcionBotanica {
        conectar();
        ArrayList<UsuarioPlanta> listaInsectosPlantas = new ArrayList<>();
        // Código para leer todas las relaciones InsectoPlanta de la base de datos
        desconectar();
        return listaInsectosPlantas; // Cambiar el valor de retorno según corresponda
    }

}
