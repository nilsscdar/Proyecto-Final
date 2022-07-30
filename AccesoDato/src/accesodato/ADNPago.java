/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodato;

import entidades.DetallePago;
import entidades.ListaPagos;
import entidades.Pago;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.print.DocFlavor;
import javax.swing.JOptionPane;

/**
 *
 * @author Maximo
 */
public class ADNPago {

    private static boolean Nuevo(Pago p) throws ClassNotFoundException, SQLException {
        int r = 0, maxid = 0, r2 = 0;
        String sql = "insert into pagos values(null,?,?,?,?)";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdmatricula());
            ps.setDate(2, (Date) p.getFecha());
            ps.setString(3, p.getHora());
            ps.setFloat(4, p.getTotal());
            r = ps.executeUpdate();
            //ultimo id guardado de pagos
            sql = "select max(idpago) from pagos";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            maxid = rs.getInt(1);
            //guardar en detallepagos

            sql = "insert into detallepagos values(?,?)";
            PreparedStatement ps2 = cn.prepareStatement(sql);
            for (DetallePago a : p.getLista()) {

                ps2.setInt(1, maxid);
                ps2.setInt(2, a.getIdconcepto());
                r2 = ps2.executeUpdate();
            }
            return r == 1 && r2 == 1 ? true : false;
        }
    }

    private static LinkedList<ListaPagos> Lista(String dni) throws ClassNotFoundException, SQLException {
        int r = 0;
        LinkedList<ListaPagos> Lista = new LinkedList<>();
        String sql = "select concat(a.nombres,' ',a.apellidos) as nombres,\n"
                + "        a.dni,concat(n.orden,'-', n.grado) as nivel,\n"
                + "        c.nombre,p.fecha from alumnos a \n"
                + "inner join matriculas m on m.idalumno=a.idalumno\n"
                + "inner join nivel n on m.idnivel=n.idnivel\n"
                + "inner join pagos p on p.idmatricula=m.idmatricula\n"
                + "inner join detallepagos dp on dp.idpago=p.idpago\n"
                + "inner join conceptos c on c.idconcepto=dp.idconcepto\n"
                + "where a.dni= ?";
        try (Connection cn = Conexion.Cadena();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lista.add(new ListaPagos(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5)));
                }
            }
        }
        return Lista;
    }

    public static boolean Guardar(Pago p) throws ClassNotFoundException, SQLException {  
        return Nuevo(p);

    }
    public static LinkedList<ListaPagos> ListaPagos(String dni) throws ClassNotFoundException, SQLException
    {
        return Lista(dni);
    }
}
