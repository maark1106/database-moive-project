package view.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserStartView extends JFrame {

    private static final Long MEMBER_ID = 1L;
    private JFrame startViewFrame;

    public UserStartView(JFrame startViewFrame) {
        this.startViewFrame = startViewFrame;

        setTitle("User Start View");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45));
        GridBagConstraints constraints = new GridBagConstraints();

        JButton bookMovieButton = createStyledButton("영화 예매");
        bookMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchMovieView searchMovieService = new SearchMovieView(MEMBER_ID, UserStartView.this);
                setVisible(false); // 현재 창 숨기기
                searchMovieService.setVisible(true);  // 프레임을 표시합니다.
            }
        });

        JButton checkReservationButton = createStyledButton("예매 정보 확인");
        checkReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserReservationInfo reservationInfo = new UserReservationInfo(UserStartView.this);
                setVisible(false); // 현재 창 숨기기
                reservationInfo.setVisible(true);  // 프레임을 표시합니다.
            }
        });

        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(bookMovieButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(checkReservationButton, constraints);

        add(panel);

        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                startViewFrame.setVisible(true);
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
