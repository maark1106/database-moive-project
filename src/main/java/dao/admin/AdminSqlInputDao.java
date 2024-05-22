package dao.admin;

import utils.databaseUtils.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminSqlInputDao {

    public boolean sqlInput(String sql, JPanel panel) {
        try (Connection conn = User.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(panel, "데이터가 성공적으로 갱신되었습니다", "성공", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "잘못된 데이터 값입니다", "오류", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
