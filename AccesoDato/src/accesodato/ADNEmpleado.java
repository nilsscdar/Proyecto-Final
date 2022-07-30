/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.Alumno;
import entidades.Empleado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Maximo
 */
public class ADNEmpleado {
    private static boolean Nuevo(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="insert into empleados values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombres());
            ps.setString(2,p.getApellidos());
            ps.setString(3,p.getDni());
            ps.setString(4,p.getSexo());
            ps.setDate(5,(Date)p.getFechanac());
            ps.setString(6,p.getTelefono());
            ps.setString(7,p.getCelular());
            ps.setString(8,p.getDireccion());
            ps.setString(9,p.getDistrito());
            ps.setString(10,p.getCargo());
            ps.setString(11,p.getEspecialidad());
            ps.setString(12,p.getEstado());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Actualizar(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="update empleados set nombres=?,apellidos=?,dni=?,sexo=?,fechanac=?,"
                + "telefono=?,celular=?,direccion=?,distrito=?,cargo=?,especialidad=?"
                + " where idempleado=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombres());
            ps.setString(2,p.getApellidos());
            ps.setString(3,p.getDni());
            ps.setString(4,p.getSexo());
            ps.setDate(5,(Date)p.getFechanac());
            ps.setString(6,p.getTelefono());
            ps.setString(7,p.getCelular());
            ps.setString(8,p.getDireccion());
            ps.setString(9,p.getDistrito());
            ps.setString(10,p.getCargo());
            ps.setString(11,p.getEspecialidad());
            ps.setInt(12,p.getIdempleado());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Existe(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="select ifnull(count(idempleado),0) from empleados where idempleado=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdempleado());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        return r>0?true:false;
    }
    private static boolean DarBaja(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="delete from empleados where idempleado=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdempleado());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static LinkedList<Empleado> Lista(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Empleado> Lista = new LinkedList<>();
        String sql="select * from empleados where apellidos like concat(?,'%') and cargo like 'Docente'";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getApellidos());
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Empleado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
               }
            }
        }
        return Lista;
    }
    private static LinkedList<Empleado> Lista2(Empleado p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Empleado> Lista = new LinkedList<>();
        String sql="select * from empleados where apellidos like concat(?,'%')";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getApellidos());
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Empleado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
               }
            }
        }
        return Lista;
    }
    private static String ObtenerNombre(int id) throws ClassNotFoundException, SQLException
    {
        String r="";
        String sql="select concat(nombres,' ',apellidos) from empleados where idempleado=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,id);
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getString(1);
            }
        }
        return r;
    }
    public static boolean Guardar(Empleado p) throws ClassNotFoundException, SQLException
    {
        if (Existe(p)) {
            return Actualizar(p);
        }else
        {
            return Nuevo(p);
        }
    }
    public static boolean Eliminar(Empleado p) throws ClassNotFoundException, SQLException
    {
        return DarBaja(p);
    }
    public static LinkedList<Empleado> ListaDocentes(Empleado p) throws ClassNotFoundException, SQLException
    {
        return Lista(p);
    }
    public static LinkedList<Empleado> ListaEmpleados(Empleado p) throws ClassNotFoundException, SQLException
    {
        return Lista2(p);
    }
    public static String ObtenerNombreEmpleado(int id) throws ClassNotFoundException, SQLException
    {
        return ObtenerNombre(id);
    }
}
