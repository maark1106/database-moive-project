import databaseUtils.Admin;
import databaseUtils.User;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection admin = Admin.getAdminConnection();
            Connection user = User.getUserConnection();
            System.out.println("DB 연결완료");
        }catch (ClassNotFoundException e){
            System.out.println("JDBC 드라이버 로드 오류");
        } catch (RuntimeException e) {
            System.out.println("DB 연결 오류");
        }
    }
}