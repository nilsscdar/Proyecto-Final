/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.ListaMatricula;
import entidades.Matricula;
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
public class ADNMatricula {

    private static boolean Nuevo(Matricula p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "insert into matriculas values(null,?,?,?,?,?,?)";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdalumno());
            ps.setInt(2, p.getIdnivel());
            ps.setString(3, p.getPeriodo());
            ps.setString(4, p.getSeccion());
            ps.setDate(5, (Date) p.getFecha());
            ps.setString(6, p.getHora());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static boolean Actualizar(Matricula p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "update matriculas set idalumno=?,idnivel=?,periodo=?,seccion=?,fecha=?,hora=?"
                + " where idmatricula=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdalumno());
            ps.setInt(2, p.getIdnivel());
            ps.setString(3, p.getPeriodo());
            ps.setString(4, p.getSeccion());
            ps.setDate(5, (Date) p.getFecha());
            ps.setString(6, p.getHora());
            ps.setInt(7, p.getIdmatricula());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static boolean Existe(Matricula p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "select ifnull(count(idmatricula),0) from matriculas where idmatricula=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdmatricula());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                r = rs.getInt(1);
            }
        }
        return r > 0 ? true : false;
    }

    private static boolean DarBaja(Matricula p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "delete from matriculas where idmatricula=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdmatricula());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static LinkedList<ListaMatricula> Lista(int idnivel) throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<ListaMatricula> Lista = new LinkedList<>();
        String sql = "select m.idmatricula,a.idalumno,concat(a.nombres,' ',a.apellidos),"
                + "concat(m.periodo,'-',n.orden,' de ',n.grado),m.seccion,a.fechanac,a.nomapoderado from matriculas m"
                + " inner join alumnos a on a.idalumno=m.idalumno"
                + " inner join nivel n on n.idnivel=m.idnivel"
                + " where n.idnivel=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idnivel);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new ListaMatricula(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7)));
                }
            }
            return Lista;
        }
    }
    private static LinkedList<ListaMatricula> Lista3(String nombre) throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<ListaMatricula> Lista = new LinkedList<>();
        String sql = "select m.idmatricula,a.idalumno,concat(a.nombres,' ',a.apellidos),"
                + "concat(m.periodo,'-',n.orden,' de ',n.grado),m.seccion,a.fechanac,a.nomapoderado from matriculas m"
                + " inner join alumnos a on a.idalumno=m.idalumno"
                + " inner join nivel n on n.idnivel=m.idnivel"
                + " where concat(a.nombres,' ',a.apellidos) like concat(?,'%')";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new ListaMatricula(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7)));
                }
            }
            return Lista;
        }
    }
    private static LinkedList<Matricula> Lista2(int idmatricula) throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<Matricula> Lista = new LinkedList<>();
        String sql = "select * from matriculas where idmatricula=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idmatricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new Matricula(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7)));
                }
            }
            return Lista;
        }
    }
    //Me webee:
//    private static LinkedList<ListaMatricula> Lista3(String dni) throws ClassNotFoundException, SQLException {
//        int r = 0;
//        LinkedList<ListaMatricula> Lista = new LinkedList<>();
//        String sql = "select m.idmatricula,a.idalumno,concat(a.nombres,' ',a.apellidos),"
//                + "concat(m.periodo,'-',n.orden,' de ',n.grado),m.seccion,a.fechanac,a.nomapoderado from matriculas m"
//                + " inner join alumnos a on a.idalumno=m.idalumno"
//                + " inner join nivel n on n.idnivel=m.idnivel"
//                + " where a.dni=?";
//        try (Connection cn = Conexion.Cadena();
//                PreparedStatement ps = cn.prepareStatement(sql)) {
//            ps.setString(1, dni);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Lista.add(new ListaMatricula(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7)));
//                }
//            }
//            return Lista;
//        }
//    }
    public static boolean Guardar(Matricula p) throws ClassNotFoundException, SQLException {
        if (Existe(p)) {
            return Actualizar(p);
        } else {
            return Nuevo(p);
        }
    }

    public static boolean Eliminar(Matricula p) throws ClassNotFoundException, SQLException {
        return DarBaja(p);
    }
    public static LinkedList<ListaMatricula> ListaMatriculas(int idnivel) throws ClassNotFoundException, SQLException
    {
        return Lista(idnivel);
    }
    public static LinkedList<Matricula> ListaTotalDatos(int idmatricula) throws ClassNotFoundException, SQLException
    {
        return Lista2(idmatricula);
    }
    public static LinkedList<ListaMatricula> ListaMatriculasNombre(String nombre) throws ClassNotFoundException, SQLException
    {
        return Lista3(nombre);
    }
}
