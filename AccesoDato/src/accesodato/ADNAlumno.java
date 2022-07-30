/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;
import entidades.Alumno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
/**
 *
 * @author Maximo
 */
public class ADNAlumno {
    private static boolean Nuevo(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="insert into alumnos values(null,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombres());
            ps.setString(2,p.getApellidos());
            ps.setString(3,p.getDni());
            ps.setString(4,p.getSexo());
            ps.setDate(5,(Date)p.getFechanac());
            ps.setString(6,p.getNompadre());
            ps.setString(7, p.getNommadre());
            ps.setString(8,p.getNomapoderado());
            ps.setString(9,p.getTelefono());
            ps.setString(10,p.getCelular());
            ps.setString(11,p.getDireccion());
            ps.setString(12,p.getDistrito());
            ps.setString(13,p.getEstado());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Actualizar(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="update alumnos set nombres=?,apellidos=?,dni=?,sexo=?,fechanac=?,"
                + "nompadre=?,nomapoderado=?,telefono=?,celular=?,direccion=?,distrito=?"
                + " where idalumno=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombres());
            ps.setString(2,p.getApellidos());
            ps.setString(3,p.getDni());
            ps.setString(4,p.getSexo());
            ps.setDate(5,(Date)p.getFechanac());
            ps.setString(6,p.getNompadre());
            ps.setString(7,p.getNomapoderado());
            ps.setString(8,p.getTelefono());
            ps.setString(9,p.getCelular());
            ps.setString(10,p.getDireccion());
            ps.setString(11,p.getDistrito());
            ps.setInt(12,p.getIdalumno());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Existe(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        //aca habia un problema
        String sql="select ifnull(count(idalumno),0) from alumnos where idalumno=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdalumno());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        
        return r>0?true:false;
    }
    private static boolean DarBaja(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="delete from alumnos where idalumno=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdalumno());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static LinkedList<Alumno> Lista(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Alumno> Lista = new LinkedList<>();
        String sql="select * from alumnos where apellidos like concat(?,'%')";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getApellidos());
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Alumno(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),rs.getString(14)));
               }
            }
        }
        return Lista;
    }
    private static LinkedList<Alumno> Lista2(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Alumno> Lista = new LinkedList<>();
        String sql="select * from alumnos where apellidos like concat(?,'%') and idalumno not in (select idalumno from matriculas)";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getApellidos());
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Alumno(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),rs.getString(14)));
               }
            }
        }
        return Lista;
    }
    private static LinkedList<Alumno> Lista3(Alumno p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Alumno> Lista = new LinkedList<>();
        String sql="select * from alumnos where apellidos like concat(?,'%') and idalumno in (select idalumno from matriculas)";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getApellidos());
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Alumno(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),rs.getString(14)));
               }
            }
        }
        return Lista;
    }
    public static boolean Guardar(Alumno p) throws ClassNotFoundException, SQLException
    {
        if (Existe(p)) {
            return Actualizar(p);
        }else
        {
            return Nuevo(p);
        }
    }
    public static boolean Eliminar(Alumno p) throws ClassNotFoundException, SQLException
    {
        return DarBaja(p);
    }
    public static LinkedList<Alumno> ListaAlumno(Alumno p) throws ClassNotFoundException, SQLException
    {
        return Lista(p);
    }
    public static LinkedList<Alumno> ListaAlumnoNoMatriculados(Alumno p) throws ClassNotFoundException, SQLException
    {
        return Lista2(p);
    }
    public static LinkedList<Alumno> ListaAlumnoSiMatriculados(Alumno p) throws ClassNotFoundException, SQLException
    {
        return Lista3(p);
    }
}
