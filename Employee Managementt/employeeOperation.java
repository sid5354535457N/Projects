import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class employeeOperation {
    public static void createEmp(Employee emp) throws SQLException{
        Connection con=DBconnection.connect();
        String query=Query.insert;
        PreparedStatement pstm=con.prepareStatement(query);
        pstm.setInt(1, emp.getID());
        pstm.setString(2, emp.getName());
        pstm.setString(3, emp.getEmail());
        pstm.setInt(4, emp.getSalary());

        JOptionPane.showMessageDialog(null,"Employee Data Inserted Successfully");
        pstm.executeUpdate();
        pstm.close();
    }
    public static void updateEmp(int id,String name) throws SQLException{
        Connection con=DBconnection.connect();
        String query=Query.update;
        PreparedStatement pstm=con.prepareStatement(query);
        pstm.setInt(1, id);
        pstm.setString(2, name);
        JOptionPane.showMessageDialog(null,"Employee data updated successfully");

        pstm.executeUpdate();
        //pstm.executeUpdate();
        pstm.close();
    }
    public static ArrayList<Employee> readAllEmployee() throws SQLException {
        ArrayList<Employee> empls=new ArrayList<Employee>();
        Connection con=DBconnection.connect();
        String query=Query.select;
        Statement stm=con.createStatement();
        ResultSet rs=stm.executeQuery(query);

        while(rs.next()) {
            Employee empp=new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            empls.add(empp);
        }
        stm.close();
        return empls;
    }
    public static void deletEmployee(int id) throws SQLException {
        Connection con=DBconnection.connect();
        String query=Query.delete;
        PreparedStatement pstm=con.prepareStatement(query);
        pstm.setInt(1, id);
        JOptionPane.showMessageDialog(null,"Data Deleted Successfully");

        pstm.executeUpdate();

        con.close();
    }
}
