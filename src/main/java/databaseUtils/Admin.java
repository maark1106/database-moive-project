package databaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Admin {

    private final static String URL ="jdbc:mysql://localhost:3306/db1";
    private final static String ID = "root";
    private final static String PW = "1234";

    public static Connection getAdminConnection() {
        try {
            return DriverManager.getConnection(URL, ID, PW);
        } catch (SQLException e) {
            System.out.println("admin 연결 오류");
            throw new RuntimeException(e);
        }
    }
}
