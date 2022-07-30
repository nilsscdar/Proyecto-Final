/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.Alumno;
import entidades.Concepto;
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
public class ADNConcepto {

    private static boolean Nuevo(Concepto p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "insert into conceptos values(null,?,?,?,?)";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDate(2, (Date) p.getFechavencimiento());
            ps.setFloat(3, p.getPrecio());
            ps.setString(4, p.getEstado());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static boolean Actualizar(Concepto p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "update conceptos set nombre=?,fechavencimiento=?,precio=? where idconcepto=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDate(2, (Date) p.getFechavencimiento());
            ps.setFloat(3, p.getPrecio());
            ps.setInt(4, p.getIdconcepto());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static boolean Existe(Concepto p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "select ifnull(count( idconcepto),0) from conceptos where idconcepto=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdconcepto());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                r = rs.getInt(1);
            }
        }
        return r == 1 ? true : false;
    }

    private static boolean DarBaja(Concepto p) throws ClassNotFoundException, SQLException {
        int r = 0;
        String sql = "delete from conceptos where idconcepto=?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdconcepto());
            r = ps.executeUpdate();
        }
        return r == 1 ? true : false;
    }

    private static LinkedList<Concepto> Lista() throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<Concepto> Lista = new LinkedList<>();
        String sql = "select * from conceptos";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new Concepto(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getFloat(4), rs.getString(5)));
                }
            }
        }
        return Lista;
    }

    private static LinkedList<Concepto> Lista2(int idmat) throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<Concepto> Lista = new LinkedList<>();
        String sql = "select * from conceptos where idconcepto not in(select c.idconcepto\n"
                + "from conceptos c inner join detallepagos dp on dp.idconcepto=c.idconcepto\n"
                + "inner join pagos p on p.idpago=dp.idpago\n"
                + "inner join matriculas m on m.idmatricula=p.idmatricula where m.idmatricula=?)";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idmat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new Concepto(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getFloat(4), rs.getString(5)));
                }
            }
        }
        return Lista;
    }

    public static boolean Guardar(Concepto p) throws ClassNotFoundException, SQLException {
        if (Existe(p)) {
            return Actualizar(p);
        } else {
            return Nuevo(p);
        }
    }

    public static boolean Eliminar(Concepto p) throws ClassNotFoundException, SQLException {
        return DarBaja(p);
    }

    public static LinkedList<Concepto> ListaConceptos() throws ClassNotFoundException, SQLException {
        return Lista();
    }
    public static LinkedList<Concepto> ListaConceptoPendientes(int idmat) throws ClassNotFoundException, SQLException {
        return Lista2(idmat);
    }
}
