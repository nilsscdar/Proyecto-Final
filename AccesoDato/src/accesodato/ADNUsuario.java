/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;


import entidades.Empleado;
import entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Maximo
 */
public class ADNUsuario {
    private static boolean Nuevo(Usuario p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="insert into usuarios values(null,?,?,?,?)";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdempleado());
            ps.setString(2,p.getNick());
            ps.setString(3,p.getClave());
            ps.setString(4,p.getTipo());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Actualizar(Usuario p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="update usuarios set nick=?,clave=?,tipo=? where idusuario=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNick());
            ps.setString(2,p.getClave());
            ps.setString(3,p.getTipo());
            ps.setInt(4,p.getIdusuario());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Existe(Usuario p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="select ifnull(count(idusuario),0) from usuarios where idusuario=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdusuario());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        return r>0?true:false;
    }
    private static boolean DarBaja(Usuario p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="delete from usuarios where idusuario=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdusuario());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static LinkedList<Usuario> Lista() throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Usuario> Lista = new LinkedList<>();
        String sql="select * from usuarios";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Usuario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
               }
            }
        }
        return Lista;
    }
    private static int Ingreso(Usuario p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="select ifnull(count(idusuario),0) from usuarios where nick=? and clave=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNick());
            ps.setString(2,p.getClave());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        return r;
    }
    public static boolean Guardar(Usuario p) throws ClassNotFoundException, SQLException
    {
        if (Existe(p)) {
            return Actualizar(p);
        }else
        {
            return Nuevo(p);
        }
    }
    public static boolean Eliminar(Usuario p) throws ClassNotFoundException, SQLException
    {
        return DarBaja(p);
    }
    
    public static LinkedList<Usuario> ListaUsuarios() throws ClassNotFoundException, SQLException
    {
        return Lista();
    }
    public static int IngresoLogin(Usuario p) throws ClassNotFoundException, SQLException
    {
        return Ingreso(p);
    }
}
