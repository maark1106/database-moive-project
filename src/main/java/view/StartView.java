package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import view.admin.AdminStartView;
import view.user.UserStartView;

public class StartView extends JFrame {


    public StartView() {
        setTitle("User & Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        showAdminAndUser(panel);

        setVisible(true);
    }

    private void showAdminAndUser(JPanel panel) {
        panel.setLayout(null);

        // 관리자 버튼
        JButton adminButton = new JButton("관리자");
        adminButton.setBounds(160, 60, 100, 30);
        panel.add(adminButton);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminStartView();
            }
        });

        // 사용자 버튼
        JButton userButton = new JButton("사용자");
        userButton.setBounds(30, 60, 100, 30);
        panel.add(userButton);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserStartView();
            }
        });

    }
}
