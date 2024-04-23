public class Query {
    static String insert="INSERT INTO empp (id,name,email,salary) VALUES (?,?,?,?)";
    static String update="UPDATE empp SET name=? WHERE id=?";
    static String delete="DELETE FROM empp WHERE id=?";
    static String select="SELECT * FROM empp";
}
