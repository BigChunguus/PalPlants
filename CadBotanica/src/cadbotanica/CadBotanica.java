/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadbotanica;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.sql.Date;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    
    private SecretKey cargarClave() {
        
        String path = new File("claveAES.dat").getAbsolutePath();
         path = path.replace("\\", "\\\\");
        System.out.println(path);
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path))) {
            return (SecretKey) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se pudo cargar la clave: " + e.getMessage());
            return null;
        }
    }
    
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
    String dml = "INSERT INTO USUARIO (NOMBREUSUARIO, CONTRASEÑA, CORREO, INTERESBOTANICOINTERESID) VALUES (?, ?, ?, ?)";

    try {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

        String contrasenaCifrada = cifrarAES(u.getContrasena());
        sentenciaPreparada.setString(1, u.getNombreUsuario());
        sentenciaPreparada.setString(2, contrasenaCifrada);
        sentenciaPreparada.setString(3, u.getEmail());
        sentenciaPreparada.setInt(4, 1);
     
        System.out.println(contrasenaCifrada);
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
                eh.setMensajeUsuario("Error: Los siguientes datos son obligatorios: Nombre, Contraseña y Correo");
                break;
            case 2290:
                eh.setMensajeUsuario("Error: La dirección debe ser '@gmail.com'");
                break;
            case 1:
                eh.setMensajeUsuario("Error: El email y nombre de usuario no pueden repetirse");
                break;
            default:
                eh.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
                break;
        }

        throw eh;
    }   catch (GeneralSecurityException ex) {
            ex.printStackTrace();
        } finally {
        desconectar();
    }
    return registrosAfectados;
}
    
    private String cifrarAES(String textoPlano) throws GeneralSecurityException {
        SecretKey clave = cargarClave();
        if (clave == null) {
            throw new GeneralSecurityException("Error al cargar la clave AES");
        }

        Cipher cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        byte[] textoCifrado = cifrador.doFinal(textoPlano.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(textoCifrado));
        return Base64.getEncoder().encodeToString(textoCifrado);
    }

    
    /**
 * Permite modificar el usuario con el email dado.
 * @return devuelve la cantidad de campos modificados en la tabla.
 * @throws ExcepcionHr La excepción se produce cuando hay un problema con la conexión de la base de datos.
*/
public int modificarUsuario(String nombreUsuario, Usuario usuario) throws ExcepcionBotanica {
    conectar();
    int registrosAfectados = 0;
    String dml = "UPDATE USUARIO SET ";
    boolean primerCampo = true;

    if (usuario.getNombre() != null) {
        dml += "NOMBRE='" + usuario.getNombre() + "' ";
        primerCampo = false;
    }
    if (usuario.getApellido1() != null) {
        dml += (primerCampo ? "" : ", ") + "APELLIDO1='" + usuario.getApellido1() + "' ";
        primerCampo = false;
    }
    if (usuario.getApellido2() != null) {
        dml += (primerCampo ? "" : ", ") + "APELLIDO2='" + usuario.getApellido2() + "' ";
        primerCampo = false;
    }
    if (usuario.getDni() != null) {
        dml += (primerCampo ? "" : ", ") + "DNI='" + usuario.getDni() + "' ";
        primerCampo = false;
    }
    if (usuario.getInteres() != null) {
        dml += (primerCampo ? "" : ", ") + "INTERESBOTANICOINTERESID=" + usuario.getInteres().getInteresId() + " ";
    }

    dml += " WHERE NOMBREUSUARIO='" + nombreUsuario + "'";

    try {
        System.out.println(dml);
        Statement statement = conexion.createStatement();
        registrosAfectados = statement.executeUpdate(dml);
        statement.close();
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
   public int eliminarUsuario(String nombreUsuario) throws ExcepcionBotanica {
       conectar();
       int registrosAfectados = 0;
       System.out.println(nombreUsuario);
       String dml = "DELETE FROM USUARIO WHERE NOMBREUSUARIO = '" + nombreUsuario + "'";

       try {
           Statement sentencia = conexion.createStatement();
           registrosAfectados = sentencia.executeUpdate(dml);
           System.out.println(registrosAfectados);
           sentencia.close();
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
               String contraseña = descifrarAES(resultSet.getString("CONTRASEÑA"));
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
       } catch (GeneralSecurityException ex) {
            //Error de descifrado
        }
       return lu;
   }
    private String descifrarAES(String textoCifrado) throws GeneralSecurityException {
        SecretKey clave = cargarClave();
        if (clave == null) {
            throw new GeneralSecurityException("Error al cargar la clave AES");
        }

        Cipher descifrador = Cipher.getInstance("AES");
        descifrador.init(Cipher.DECRYPT_MODE, clave);
        byte[] textoCifradoBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] textoDescifradoBytes = descifrador.doFinal(textoCifradoBytes);
        return new String(textoDescifradoBytes);
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
   
   
   
    public int insertarResena(Resena resena) throws ExcepcionBotanica {
        
        System.out.println(resena);
        conectar();
        int filasAfectadas = 0;
        String dml = "INSERT INTO RESEÑA (CALIFICACION, COMENTARIO, USUARIOUSUARIOID, GUIAGUIAID) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
            // Asignar los valores a los parámetros de la consulta
            preparedStatement.setInt(1, resena.getCalificacion());
            preparedStatement.setString(2, resena.getComentario());
            preparedStatement.setInt(3, resena.getUsuarioId().getUsuarioID());
            preparedStatement.setInt(4, resena.getGuiaId().getGuiaId());

            // Ejecutar la inserción
            filasAfectadas = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } 
        desconectar();
        return filasAfectadas;
    }



    public int modificarResena(int idResena, Resena resena) throws ExcepcionBotanica {
        conectar();
        int filasAfectadas = 0;
        String dml = "UPDATE RESEÑA SET CALIFICACION = ?, COMENTARIO = ? WHERE RESEÑAID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
            // Asignar los valores a los parámetros de la consulta
            preparedStatement.setInt(1, resena.getCalificacion());
            preparedStatement.setString(2, resena.getComentario());
            preparedStatement.setInt(3, idResena);

            // Ejecutar la actualización
            filasAfectadas = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }
        desconectar();
        return filasAfectadas;
    }


    public int eliminarResena(int resenaId) throws ExcepcionBotanica {
        conectar();
        int filasAfectadas = 0;
        String dml = "DELETE FROM RESEÑA WHERE RESEÑAID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
            // Asignar el valor del ID de la reseña al parámetro de la consulta
            preparedStatement.setInt(1, resenaId);

            // Ejecutar la eliminación
            filasAfectadas = preparedStatement.executeUpdate();

            
            

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } 
        desconectar();
        return filasAfectadas;
    }

    public ArrayList<Resena> leerResenasGuia(int guiaId) throws ExcepcionBotanica {
        conectar();
        ArrayList<Resena> listaResenas = new ArrayList<>();

        String dml = "SELECT R.RESEÑAID, R.CALIFICACION, R.COMENTARIO, R.FECHARESEÑA, " +
                     "R.USUARIOUSUARIOID, U.NOMBREUSUARIO, U.NOMBRE, U.APELLIDO1, U.APELLIDO2, U.CORREO, U.DNI, U.CONTRASEÑA, " +
                     "U.INTERESBOTANICOINTERESID, I.NOMBREINTERES " +
                     "FROM RESEÑA R " +
                     "JOIN USUARIO U ON R.USUARIOUSUARIOID = U.USUARIOID " +
                     "JOIN INTERESBOTANICO I ON U.INTERESBOTANICOINTERESID = I.INTERESID " +
                     "WHERE R.GUIAGUIAID = " + guiaId;

        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(dml)) {

            while (resultSet.next()) {
                // Datos de la reseña
                Integer resenaId = resultSet.getInt("RESEÑAID");
                Integer calificacion = resultSet.getInt("CALIFICACION");
                String comentario = resultSet.getString("COMENTARIO");
                Date fechaResena = resultSet.getDate("FECHARESEÑA");

                // Datos del usuario
                int usuarioId = resultSet.getInt("USUARIOUSUARIOID");
                String nombreUsuario = resultSet.getString("NOMBREUSUARIO");
                String nombre = resultSet.getString("NOMBRE");
                String apellido1 = resultSet.getString("APELLIDO1");
                String apellido2 = resultSet.getString("APELLIDO2");
                String dni = resultSet.getString("DNI");
                String correo = resultSet.getString("CORREO");
                String contraseña = resultSet.getString("CONTRASEÑA");

                // Datos del interés
                int interesId = resultSet.getInt("INTERESBOTANICOINTERESID");
                String nombreInteres = resultSet.getString("NOMBREINTERES");

                InteresBotanico interes = new InteresBotanico(interesId, nombreInteres);
                Usuario usuario = new Usuario(usuarioId, nombreUsuario, nombre, apellido1, apellido2, correo, dni, contraseña, interes);

                // Crear el objeto Resena
                Resena resena = new Resena(resenaId, calificacion, comentario, fechaResena, new Guia(), usuario);

                listaResenas.add(resena);
            }

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } finally {
            desconectar();
        }

        return listaResenas;
    }

    public int insertarGuia(Guia guia) throws ExcepcionBotanica {
        conectar();
        int filasAfectadas = 0;
        String dml = "INSERT INTO GUIA (TITULO, CONTENIDO, PLANTAPLANTAID, USUARIOUSUARIOID) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
            // Asignar los valores a los parámetros de la consulta
            preparedStatement.setString(1, guia.getTitulo());
            preparedStatement.setString(2, guia.getContenido());
            preparedStatement.setInt(3, guia.getPlantaId().getPlantaId());
            preparedStatement.setInt(4, guia.getUsuarioId().getUsuarioID());

            // Ejecutar la inserción
            filasAfectadas = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }
         desconectar();
        return filasAfectadas;
    }


    public int modificarGuia(Integer guiaId, Guia guia) throws ExcepcionBotanica {
    conectar();
    int registrosAfectados = 0;
    String dml = "UPDATE GUIA SET TITULO = ?, CONTENIDO = ? WHERE GUIAID = ?";

    try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
        // Asignar los valores a los parámetros de la consulta
        preparedStatement.setString(1, guia.getTitulo());
        preparedStatement.setString(2, guia.getContenido());
        preparedStatement.setInt(3, guiaId);

        // Ejecutar la actualización
        registrosAfectados = preparedStatement.executeUpdate();

    } catch (SQLException ex) {
        ExcepcionBotanica excepcion = new ExcepcionBotanica();
        excepcion.setCodigoErrorSQL(ex.getErrorCode());
        excepcion.setMensajeErrorBd(ex.getMessage());
        excepcion.setSentenciaSQL(dml);
        excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
        throw excepcion;
    }
    desconectar();
    return registrosAfectados;
}


    public int eliminarGuia(int guiaId) throws ExcepcionBotanica {
        conectar();
        int filasAfectadas = 0;
        String dml = "DELETE FROM GUIA WHERE GUIAID = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(dml)) {
            // Asignar el valor del ID de la guía al parámetro de la consulta
            preparedStatement.setInt(1, guiaId);

            // Ejecutar la eliminación
            filasAfectadas = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } 
        desconectar();
        return filasAfectadas;
        
    }

    public ArrayList<Guia> leerGuias(Integer plantaIdGuia) throws ExcepcionBotanica {
    conectar();
    ArrayList<Guia> listaGuias = new ArrayList<>();
    
    String dml = "SELECT G.GUIAID, G.TITULO, G.CONTENIDO, G.CALIFICACIONMEDIA, " +
                "P.NOMBRECIENTIFICOPLANTA, P.NOMBRECOMUNPLANTA, P.DESCRIPCION, P.TIPOPLANTA, P.CUIDADOSESPECIFICOS, P.IMAGEN,"+
                "U.USUARIOID, U.NOMBREUSUARIO, U.NOMBRE, U.APELLIDO1, U.APELLIDO2, U.CORREO, U.DNI, U.CONTRASEÑA, U.INTERESBOTANICOINTERESID, I.NOMBREINTERES "+
                "FROM GUIA G "+
                "JOIN USUARIO U ON G.USUARIOUSUARIOID = U.USUARIOID "+
                "JOIN INTERESBOTANICO I ON U.INTERESBOTANICOINTERESID = I.INTERESID "+
                "JOIN PLANTA P ON G.PLANTAPLANTAID = P.PLANTAID "+
                "WHERE G.PLANTAPLANTAID = " + plantaIdGuia;

    try (Statement statement = conexion.createStatement();
         ResultSet resultSet = statement.executeQuery(dml)) {

        while (resultSet.next()) {
            // Datos de la guía
            Integer guiaId = resultSet.getInt("GUIAID");
            String titulo = resultSet.getString("TITULO");
            String contenido = resultSet.getString("CONTENIDO");
            Double calificacionMedia = resultSet.getDouble("CALIFICACIONMEDIA");
            // Datos de la planta
            String nombreCientificoPlanta = resultSet.getString("NOMBRECIENTIFICOPLANTA");
            String nombreComunPlanta = resultSet.getString("NOMBRECOMUNPLANTA");
            String descripcion = resultSet.getString("DESCRIPCION");
            String tipoPlanta = resultSet.getString("TIPOPLANTA");
            String cuidadosEspecificos = resultSet.getString("CUIDADOSESPECIFICOS");
            String imagen = resultSet.getString("IMAGEN");

            //Datos usuario
            int usuarioId = resultSet.getInt("USUARIOID");
            String nombreUsuario = resultSet.getString("NOMBREUSUARIO");
            String nombre = resultSet.getString("NOMBRE");
            String apellido1 = resultSet.getString("APELLIDO1");
            String apellido2 = resultSet.getString("APELLIDO2");
            String dni = resultSet.getString("DNI");
            String correo = resultSet.getString("CORREO");
            String contraseña = resultSet.getString("CONTRASEÑA");
            
            //Datos interes
            int interesId = resultSet.getInt("INTERESBOTANICOINTERESID");
            String nombreInteres = resultSet.getString("NOMBREINTERES");
            
            InteresBotanico interes = new InteresBotanico(interesId, nombreInteres);
            Planta planta = new Planta(plantaIdGuia, nombreCientificoPlanta, nombreComunPlanta, descripcion, tipoPlanta, cuidadosEspecificos, imagen);
            Usuario usuario = new Usuario(usuarioId, nombreUsuario, nombre, apellido1, apellido2, correo, dni, contraseña, interes);
            Guia guia = new Guia(guiaId, titulo, contenido, calificacionMedia, planta,usuario);

            listaGuias.add(guia);
        }

    } catch (SQLException ex) {
        ExcepcionBotanica excepcion = new ExcepcionBotanica();
        excepcion.setCodigoErrorSQL(ex.getErrorCode());
        excepcion.setMensajeErrorBd(ex.getMessage());
        excepcion.setSentenciaSQL(dml);
        excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
        throw excepcion;
    } finally {
        desconectar();
    }

    return listaGuias;
}

    public ArrayList<Insecto> leerInsectos(int idPlanta) throws ExcepcionBotanica {
        conectar();
        ArrayList<Insecto> listaInsectos = new ArrayList<>();
        String query = "SELECT i.INSECTOID, i.NOMBRECIENTIFICOINSECTO, i.NOMBRECOMUNINSECTO, i.DESCRIPCION " +
                       "FROM PLANTA_INSECTO pi " +
                       "JOIN INSECTO i ON pi.INSECTOINSECTOID = i.INSECTOID " +
                       "WHERE pi.PLANTAPLANTAID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idPlanta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int insectoId = rs.getInt("INSECTOID");
                String nombreCientificoInsecto = rs.getString("NOMBRECIENTIFICOINSECTO");
                String nombreComunInsecto = rs.getString("NOMBRECOMUNINSECTO");
                String descripcion = rs.getString("DESCRIPCION");
                Insecto insecto = new Insecto(insectoId, nombreCientificoInsecto, nombreComunInsecto, descripcion);
                listaInsectos.add(insecto);
            }
        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(query);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } finally {
            desconectar();
        }
        return listaInsectos;
    }

    public ArrayList<InteresBotanico> leerInteresesBotanicos() throws ExcepcionBotanica {
        conectar();
        ArrayList<InteresBotanico> listaIntereses = new ArrayList<>();
        String dml = "SELECT * FROM BOTANICA.INTERESBOTANICO";
        
        try{
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(dml);
            
            while (resultSet.next()) {
                int idInteres = resultSet.getInt("INTERESID");
                String nombreInteres = resultSet.getString("NOMBREINTERES");
                
                InteresBotanico ib = new InteresBotanico(idInteres, nombreInteres);
                listaIntereses.add(ib);
            }
        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }
        desconectar();
        return listaIntereses; // Cambiar el valor de retorno según corresponda
    }

    public Planta leerPlanta(Integer plantaIdBuscar) throws ExcepcionBotanica {
        conectar();
        Planta planta = null;
        
        String dml = "SELECT PLANTA.NOMBRECIENTIFICOPLANTA, PLANTA.NOMBRECOMUNPLANTA , PLANTA.DESCRIPCION, PLANTA.TIPOPLANTA, PLANTA.CUIDADOSESPECIFICOS, PLANTA.IMAGEN " +
                     "FROM PLANTA WHERE PLANTAID = '" + plantaIdBuscar + "'";
        
        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(dml);
            
            while (resultSet.next()) {
                
                String nombreCientifico = resultSet.getString("NOMBRECIENTIFICOPLANTA");
                String nombreComun = resultSet.getString("NOMBRECOMUNPLANTA");
                String descripcion = resultSet.getString("DESCRIPCION");
                String tipoPlanta = resultSet.getString("TIPOPLANTA");
                String cuidadosEspecificos = resultSet.getString("CUIDADOSESPECIFICOS");
                String imagen = resultSet.getString("IMAGEN");

                planta = new Planta(plantaIdBuscar, nombreCientifico, nombreComun, descripcion, tipoPlanta, cuidadosEspecificos, imagen);
                
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }

        desconectar();
        return planta; // Cambiar el valor de retorno según corresponda
    }

    public ArrayList<Planta> leerPlantas() throws ExcepcionBotanica {
        conectar();
        ArrayList<Planta> listaPlantas = new ArrayList<>();
        String dml = "SELECT * FROM PLANTA";
        try{
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(dml);
            System.out.println(dml);
            while (resultSet.next()) {
                
                Integer plantaId = resultSet.getInt("PLANTAID");
                String nombreCientifico = resultSet.getString("NOMBRECIENTIFICOPLANTA");
                String nombreComun = resultSet.getString("NOMBRECOMUNPLANTA");
                String descripcion = resultSet.getString("DESCRIPCION");
                String tipoPlanta = resultSet.getString("TIPOPLANTA");
                String cuidadosEspecificos = resultSet.getString("CUIDADOSESPECIFICOS");
                String imagen = resultSet.getString("IMAGEN");

                Planta planta = new Planta(plantaId, nombreCientifico, nombreComun, descripcion, tipoPlanta, cuidadosEspecificos, imagen);
                listaPlantas.add(planta);
            }
        } catch (SQLException ex){
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }
        desconectar();
        return listaPlantas; // Cambiar el valor de retorno según corresponda
    }

    public int insertarUsuarioPlanta(int usuarioId, int plantaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        String dml = "INSERT INTO USUARIO_PLANTA VALUES (" + usuarioId + ", " + plantaId + ")";
        try (Statement stmt = conexion.createStatement()){
            
            registrosAfectados = stmt.executeUpdate(dml);
            
            desconectar(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(CadBotanica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registrosAfectados;
    }

    public int eliminarUsuarioPlanta(Integer usuarioId, Integer plantaId) throws ExcepcionBotanica {
        conectar();
        int registrosAfectados = 0;
        System.out.println("Entrada a elimianar");
        String dml = "DELETE FROM USUARIO_PLANTA WHERE USUARIOUSUARIOID = " + usuarioId + " AND PLANTAPLANTAID = " + plantaId;
        try (Statement stmt = conexion.createStatement()) {
            registrosAfectados = stmt.executeUpdate(dml);
            System.out.println(registrosAfectados);
        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        } finally {
            desconectar();
        }
        return registrosAfectados;
    }

    public ArrayList<Planta> leerUsuariosPlantas(String nombreUsuario) throws ExcepcionBotanica {
        conectar();
        ArrayList<Planta> listaPlantas = new ArrayList<>();

        String dml = "SELECT PLANTA.PLANTAID, PLANTA.NOMBRECIENTIFICOPLANTA, PLANTA.NOMBRECOMUNPLANTA , PLANTA.DESCRIPCION, PLANTA.TIPOPLANTA, PLANTA.CUIDADOSESPECIFICOS, PLANTA.IMAGEN " +
                       "FROM USUARIO " +
                       "JOIN USUARIO_PLANTA ON USUARIO.USUARIOID = USUARIO_PLANTA.USUARIOUSUARIOID " +
                       "JOIN PLANTA ON USUARIO_PLANTA.PLANTAPLANTAID = PLANTA.PLANTAID " +
                       "WHERE USUARIO.NOMBREUSUARIO = '" + nombreUsuario + "'";

        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(dml);
            System.out.println(dml);
            while (resultSet.next()) {
                
                Integer plantaId = resultSet.getInt("PLANTAID");
                String nombreCientifico = resultSet.getString("NOMBRECIENTIFICOPLANTA");
                String nombreComun = resultSet.getString("NOMBRECOMUNPLANTA");
                String descripcion = resultSet.getString("DESCRIPCION");
                String tipoPlanta = resultSet.getString("TIPOPLANTA");
                String cuidadosEspecificos = resultSet.getString("CUIDADOSESPECIFICOS");
                String imagen = resultSet.getString("IMAGEN");

                Planta planta = new Planta(plantaId, nombreCientifico, nombreComun, descripcion, tipoPlanta, cuidadosEspecificos, imagen);
                listaPlantas.add(planta);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ExcepcionBotanica excepcion = new ExcepcionBotanica();
            excepcion.setCodigoErrorSQL(ex.getErrorCode());
            excepcion.setMensajeErrorBd(ex.getMessage());
            excepcion.setSentenciaSQL(dml);
            excepcion.setMensajeUsuario("Error en el sistema. Consulta con el administrador");
            throw excepcion;
        }

        desconectar();
        
        return listaPlantas;
    }


}
