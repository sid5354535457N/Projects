import java.util.*;

import javax.swing.JOptionPane;
public class Face {
    public static void main(String[] args) throws Exception{
        while(true) {
            
            String input = JOptionPane.showInputDialog("\"Welcome to Employee Management App\n Enter your choice:\n1)Create Data             2)Read Data\n" + 
                "3)Update Data         4)Delete Data        5)Exit");
            int choice=Integer.parseInt(input);

            if(choice<1 || choice>=6) {
                break;
            }
            switch(choice) {
                case 1:
                        int id=Integer.parseInt(JOptionPane.showInputDialog("Enter your id"));
                    //sc.nextLine();
                    String name=JOptionPane.showInputDialog("Enter your name");
                    String email=JOptionPane.showInputDialog("Enter your email");
                    int salary=Integer.parseInt(JOptionPane.showInputDialog("Enter your salary"));
                    Employee emp=new Employee(id, name, email, salary);
                    employeeOperation.createEmp(emp);
                    break;

                case 2:StringBuilder employeeData = new StringBuilder();
                    ArrayList<Employee> list = employeeOperation.readAllEmployee();
                    for (Employee empList : list) {
                    employeeData.append(empList).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, employeeData.toString());
                    break;

                case 3:
                    id = Integer.parseInt(JOptionPane.showInputDialog("Enter id:"));
                    name = JOptionPane.showInputDialog("Enter name:");
                    employeeOperation.updateEmp(id, name);
                    break;

                case 4:
                    id = Integer.parseInt(JOptionPane.showInputDialog("Enter id:"));
                    employeeOperation.deletEmployee(id);
                    break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Thank you");
                    return;
            
            }
        }
    }
}
