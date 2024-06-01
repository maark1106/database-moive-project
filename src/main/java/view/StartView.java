package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import view.admin.AdminStartView;
import view.user.UserStartView;

public class StartView extends JFrame {

    public StartView() {
        setTitle("User & Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(45, 45, 45));
        getContentPane().add(panel);
        showAdminAndUser(panel);

        setVisible(true);
    }

    private void showAdminAndUser(JPanel panel) {
        JButton adminButton = createStyledButton("관리자");
        adminButton.setBounds(160, 60, 100, 30);
        panel.add(adminButton);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminStartView(StartView.this).setVisible(true);
                setVisible(false); // StartView 숨기기
            }
        });

        JButton userButton = createStyledButton("사용자");
        userButton.setBounds(30, 60, 100, 30);
        panel.add(userButton);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserStartView(StartView.this).setVisible(true); // StartView 숨기기
                setVisible(false);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
}
