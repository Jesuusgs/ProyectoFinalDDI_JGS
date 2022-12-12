package sample.util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {
    //Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    //Conexión
    private static Connection conn = null;

    private static final String connStr = "jdbc:oracle:thin:hr/hr@localhost:1521/xe";


    //Conectar a la base de datos
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Configuración de la conexión
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("No puedo encontrar el driver.");
            e.printStackTrace();
            throw e;
        }

        System.out.println("El driver ha sido registrado correctamente");

        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            System.out.println("La conexión ha fallado. " + e);
            e.printStackTrace();
            throw e;
        }
    }

    //Cerrar la conexión
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }

    //Método executeQuery (para las consultas)
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Hay que conectarse primero a la base de datos
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");

            stmt = conn.createStatement();

            //Ejecutar la consulta
            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error en el executeQuery : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            //cerrar la conexión
            dbDisconnect();
        }
        return crs;
    }

    //DB Execute Update
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            //Hay que conectarse primero a la base de datos
            dbConnect();

            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error en el executeQuery : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            //Cerrar la conexión
            dbDisconnect();
        }
    }
}

