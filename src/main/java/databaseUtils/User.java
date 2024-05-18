package databaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User {

    private final static String URL ="jdbc:mysql://localhost:3306/db1";
    private final static String ID = "user1";
    private final static String PW = "user1";

    public static Connection getUserConnection(){
        try {
            return DriverManager.getConnection(URL, ID, PW);
        } catch (SQLException e) {
            System.out.println("user 연결 오류");
            throw new RuntimeException(e);
        }
    }
}
