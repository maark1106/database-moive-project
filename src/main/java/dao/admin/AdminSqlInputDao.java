package dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utils.databaseUtils.Admin;

public class AdminSqlInputDao {

    private Statement stmt = null;

    public void sqlInput(String sql, JPanel panel){
        AdminSqlInputDao dao = new AdminSqlInputDao();
        try {
            Connection conn = Admin.getAdminConnection();
            stmt = conn.createStatement();

            if (sql == null) {
                return;
            }

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(panel, "SQL 문이 성공적으로 실행되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "SQL 오류: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}
