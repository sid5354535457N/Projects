package dao;

import db.MyConnection;
import structure.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExist(String email)throws SQLException {
        Connection con= MyConnection.getCon();
        PreparedStatement pstm=con.prepareStatement("select email from users");
        ResultSet rs=pstm.executeQuery();
        while (rs.next()) {
            String em=rs.getString(1);
            if(em.equals(email)) {
                return true;
            }
        }
        return false;
    }
    public static int saveUser(User user) throws SQLException{
        Connection con=MyConnection.getCon();
        PreparedStatement pstm=con.prepareStatement("insert into users values(default,?,?)");
        pstm.setString(1, user.getName());
        pstm.setString(2, user.getEmail());
        return pstm.executeUpdate();

    }
}
