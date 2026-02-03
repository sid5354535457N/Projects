import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
    static Connection conn=null;
    public static Connection connect() {
        try {
			String url="jdbc:mysql://localhost:3306/employ";
			String userName="root";
			String password="sid5354535457N";
			
			conn=DriverManager.getConnection(url,userName,password);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        return conn;
    }

}
