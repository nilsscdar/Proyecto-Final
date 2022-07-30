/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.Horario;
import entidades.Nivel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Maximo
 */
public class ADNNivel {

    private static boolean Nuevo(Nivel p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "insert into nivel values(null,?,?,?,?,?,?)";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getTutor());
            ps.setInt(2, p.getCotutor());
            ps.setString(3, p.getGrado());
            ps.setString(4, p.getOrden());
            ps.setInt(5, p.getNrovacantes());
            ps.setString(6, p.getEstado());
            r = ps.executeUpdate();


        }
        return r == 1 ? true : false;

    }

    private static boolean Actualizar(Nivel p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "update nivel set tutor=?,cotutor=?,grado=?,orden=?,nrovacantes=? "
                + "where idnivel=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getTutor());
            ps.setInt(2, p.getCotutor());
            ps.setString(3, p.getGrado());
            ps.setString(4, p.getOrden());
            ps.setInt(5, p.getNrovacantes());
            ps.setInt(6, p.getIdNivel());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static boolean Existe(Nivel p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "select ifnull(count(idnivel),0) from nivel where idnivel=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdNivel());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                r = rs.getInt(1);
            }
        }
        return r > 0 ? true : false;
    }

    private static boolean DarBaja(Nivel p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "delete from nivel where idnivel=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdNivel());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static LinkedList<Nivel> Lista() throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<Nivel> Lista = new LinkedList<>();
        String sql = "select n.idnivel,n.tutor,(concat(e.nombres,' ',e.apellidos)),\n"
                + "n.grado,n.orden,n.nrovacantes,n.estado \n"
                + "from nivel n inner join empleados e \n"
                + "where n.tutor=e.idempleado";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new Nivel(rs.getInt(1), rs.getInt(2), rs.getString(3)
                            , rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
                }
            }
        }
        return Lista;
    }
    private static LinkedList<Nivel> Combo(String grado) throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Nivel> Lista = new LinkedList<>();
        String sql="select idnivel,concat(orden,' de ',grado)  from nivel where grado like ?";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            
            ps.setString(1, grado);
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Nivel(rs.getInt(1), rs.getString(2)));
               }
            }
        }
        return Lista;
    }
    private static LinkedList<Nivel> Combo2() throws ClassNotFoundException, SQLException
    {
        int r=0;
        LinkedList<Nivel> Lista = new LinkedList<>();
        String sql="select idnivel,concat(orden,' de ',grado)  from nivel";
        try (Connection cn= Conexion.Cadena();
                PreparedStatement ps= cn.prepareStatement(sql)){
            try (ResultSet rs= ps.executeQuery()){
               while(rs.next())
               {
                   Lista.add(new Nivel(rs.getInt(1), rs.getString(2)));
               }
            }
        }
        return Lista;
    }
    public static boolean Guardar(Nivel p) throws ClassNotFoundException, SQLException {
        if (Existe(p)) {
            return Actualizar(p);
        } else {
            return Nuevo(p);
        }
    }

    public static boolean Eliminar(Nivel p) throws ClassNotFoundException, SQLException {
        return DarBaja(p);
    }

    public static LinkedList<Nivel> ListaNiveles() throws ClassNotFoundException, SQLException {
        return Lista();
    }
    public static LinkedList<Nivel> CargarComboporGrado(String grado) throws ClassNotFoundException, SQLException {
        return Combo(grado);
    }
    public static LinkedList<Nivel> CargarCombo() throws ClassNotFoundException, SQLException {
        return Combo2();
    }
}
