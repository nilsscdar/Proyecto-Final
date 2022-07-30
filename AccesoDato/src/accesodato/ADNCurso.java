/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.*;
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
public class ADNCurso {
    private static boolean Nuevo(Curso p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="insert into cursos values(null,?,?)";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombre());
            ps.setString(2,p.getEstado());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Actualizar(Curso p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="update cursos set nombre=? where idcurso=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombre());
            ps.setInt(2,p.getIdcurso());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static boolean Existe(Curso p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="select ifnull(count( idcurso),0) from cursos where idcurso=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdcurso());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        return r==1?true:false;
    }
    private static boolean DarBaja(Curso p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="delete from cursos where idcurso=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setInt(1,p.getIdcurso());
            r=ps.executeUpdate();
        }
        return r==1?true:false;
    }
    private static LinkedList<Curso> Lista() throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Curso> Lista = new LinkedList<>();
        String sql="select * from cursos";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Curso(rs.getInt(1), rs.getString(2), rs.getString(3)));
               }
            }
        }
        return Lista;
    }
    private static LinkedList<Curso> Combo() throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Curso> Lista = new LinkedList<>();
        String sql="select idcurso,nombre from cursos";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Curso(rs.getInt(1), rs.getString(2)));
               }
            }
        }
        return Lista;
    }
    private static int ObtenerId(Curso p) throws ClassNotFoundException, SQLException
    {
        int r=0;
        String sql="select idcurso from cursos where nombre=?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            ps.setString(1,p.getNombre());
            try (ResultSet rs= ps.executeQuery()){
                rs.next();
                r=rs.getInt(1);
            }
        }
        return r;
    }
    public static boolean Guardar(Curso p) throws ClassNotFoundException, SQLException
    {
        if (Existe(p)) {
            return Actualizar(p);
        }else
        {
            return Nuevo(p);
        }
    }
    public static boolean Eliminar(Curso p) throws ClassNotFoundException, SQLException
    {
        return DarBaja(p);
    }
    public static LinkedList<Curso> ListaCursos() throws ClassNotFoundException, SQLException
    {
        return Lista();
    }
    public static LinkedList<Curso> CargarCombo() throws ClassNotFoundException, SQLException
    {
        return Combo();
    }
    public static int ObtenerIdCurso(Curso c) throws ClassNotFoundException, SQLException
    {
        return ObtenerId(c);
    }
}
