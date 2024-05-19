package dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import utils.databaseUtils.Admin;
import utils.sql.InitSql;

public class AdminInitializeDao {

    private Statement stmt = null;

    public void initialize(){
        try {
            Connection conn = Admin.getAdminConnection();
            stmt = conn.createStatement();

            String[] commands = InitSql.initial.split(";");
            for (String command : commands) {
                if (!command.trim().isEmpty()) {
                    stmt.execute(command);
                    System.out.println("Executed: " + command);
                }
            }
            System.out.println("DB initial success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
