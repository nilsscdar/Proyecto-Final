/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Maximo
 */
public class Conexion {
    public static Connection Cadena() throws ClassNotFoundException, SQLException
    {
        //nombre del controlador
        Class.forName("com.mysql.jdbc.Driver");
        //url de la conexion, le agregamos usuario y contraseña
        Connection cn= DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcolegio2","root","root");
        return cn;
    }
}
