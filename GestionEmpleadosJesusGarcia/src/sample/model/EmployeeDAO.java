package sample.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class EmployeeDAO {

    //Selección de un empleado en función de su ID
    public static Employee searchEmployee (String empId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM employees WHERE employee_id="+empId;

        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            Employee employee = getEmployeeFromResultSet(rsEmp);

            //Devuelve el empleado con el id introducido
            return employee;
        } catch (SQLException e) {
            System.out.println("Mientras se buscaba al empleado con id " + empId + ", se ha producido un error: " + e);
            throw e;
        }
    }

    private static Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException
    {
        Employee emp = null;
        if (rs.next()) {
            emp = new Employee();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
        }
        return emp;
    }

    //Selección de TODOS los empleados
    public static ObservableList<Employee> searchEmployees () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM employees";

        try {
            //Obtener el resultset
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //carga el observablelist en función del resultset obtenido.
            ObservableList<Employee> empList = getEmployeeList(rsEmps);

            return empList;
        } catch (SQLException e) {
            System.out.println("La consulta ha fallado: " + e);
            throw e;
        }
    }

    private static ObservableList<Employee> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declaración de la lista cargada con objetos de tipo empleado
        ObservableList<Employee> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Employee emp = new Employee();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));

            empList.add(emp);
        }

        return empList;
    }

    //Actualizar el email de un empleado dado su id
    public static void updateEmpEmail (String empId, String empEmail) throws SQLException, ClassNotFoundException {

        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE employees\n" +
                        "      SET EMAIL = '" + empEmail + "'\n" +
                        "    WHERE EMPLOYEE_ID = " + empId + ";\n" +
                        "   COMMIT;\n" +
                        "END;";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Se ha producido un error con el UPDATE: " + e);
            throw e;
        }
    }

    //Borrar empleado
    public static void deleteEmpWithId (String empId) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM employees\n" +
                        "         WHERE employee_id ="+ empId +";\n" +
                        "   COMMIT;\n" +
                        "END;";


        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Se ha producido un error en la eliminación del empleado: " + e);
            throw e;
        }
    }

    //Método para insertar un empleado
    public static void insertEmp (String name, String lastname, String email) throws SQLException, ClassNotFoundException {
        //El IDNEW será el id que tendrá el empleado nuevo
        int idNew = seleccionarUltId() + 1;
        System.out.println(seleccionarUltId());
        //genera un teléfono aleatorio para que al consultar el nuevo empleado insertado no se le quede vacía la caja de teléfono en la tabla
        int telf = (int) ((Math.random()*((99999999-10000000)+1))+10000000);
        String telephone = "6" + telf;

        String updateStmt =
                "BEGIN\n" +
                        "INSERT INTO employees\n" +
                        "(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID)\n" +
                        "VALUES\n" +
                        "('" + idNew+ "', '"+name+"', '"+lastname+"', '"+email+"', '" + telephone + "', SYSDATE, 'IT_PROG');\n" +
                        "END;";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Se ha producido un error en la inserción del empleado: " + e);
            throw e;
        }
    }

    private static int seleccionarUltId() {
        String seq = "SELECT EMPLOYEE_ID FROM employees WHERE EMPLOYEE_ID = (SELECT MAX(EMPLOYEE_ID) FROM employees)";
        int num = 1;

        try {
            ResultSet rslt = DBUtil.dbExecuteQuery(seq);
            while (rslt.next()){
                num =  rslt.getInt(1);
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

}
