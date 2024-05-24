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

        // 윈도우 타이틀 설정
        setTitle("User Start View");

        // 기본 레이아웃 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // 패널 생성 및 레이아웃 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // "영화 예매" 버튼 생성 및 이벤트 리스너 추가
        JButton bookMovieButton = new JButton("영화 예매");
        bookMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchMovieView searchMovieService = new SearchMovieView(MEMBER_ID, UserStartView.this);
                setVisible(false); // 현재 창 숨기기
                searchMovieService.setVisible(true);  // 프레임을 표시합니다.
            }
        });

        // "예매 정보 확인" 버튼 생성 및 이벤트 리스너 추가
        JButton checkReservationButton = new JButton("예매 정보 확인");
        checkReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserReservationInfo reservationInfo = new UserReservationInfo(UserStartView.this);
                setVisible(false); // 현재 창 숨기기
                reservationInfo.setVisible(true);  // 프레임을 표시합니다.
            }
        });

        // 버튼을 패널에 추가
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(bookMovieButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(checkReservationButton, constraints);

        // 패널을 프레임에 추가
        add(panel);

        // 프레임 표시
        setVisible(true);

        // UserStartView가 닫힐 때 StartView를 다시 보이게 설정
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                startViewFrame.setVisible(true);
            }
        });
    }
}
