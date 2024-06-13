import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
    private boolean conexion;
    private Statement sentencia;

    public Db() {
        conexion = false;
    }

    public void ConectarDB(String usuario, String contraseña, String nom_db, String host, String puerto) {
        try {
            Connection con = null;
            String sURL = "jdbc:mariadb://" + host + ":" + puerto + "/" + nom_db;
            con = DriverManager.getConnection(sURL, usuario, contraseña);
            sentencia = con.createStatement();
            conexion = true;
            System.out.println("la conexion a sido exitosa");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("la conexion a fallado");
        }
    }

    public String Read() {
        if (conexion) {
            try {
                ResultSet res = sentencia.executeQuery("CALL listarProductos()");
                String cadena = "";
                while (res.next()) {
                    cadena += res.getInt(1)+"|"+res.getString(2)+"|"+res.getFloat(3)+"|"+res.getInt(4)+"\n";
                }
                return cadena;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "error";
            }
        } else
            return "No hay conexion a la base de datos";
    }

    public void Create(int id,String nombre,float precio, int id_fabricante){
        if (conexion) {
            try {
                sentencia.execute("CALL insertar("+id+",'"+nombre+"',"+precio+","+id_fabricante+")");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        } else
            System.out.println("No hay conexion a una base de datos");
    }

    public void Delete(int id){
        if (conexion) {
            try {
                sentencia.execute("CALL eliminar("+id+")");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        } else
            System.out.println("No hay conexion a una base de datos");
    }

    public void Update(int id,String nombre,float precio, int id_fabricante){
        if (conexion) {
            try {
                sentencia.execute("CALL modificaciones("+id+",'"+nombre+"',"+precio+","+id_fabricante+")");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        } else
            System.out.println("No hay conexion a una base de datos");
    }

    public boolean isConexion() {
        return conexion;
    }

    public Statement getSentencia() {
        return sentencia;
    }

}
