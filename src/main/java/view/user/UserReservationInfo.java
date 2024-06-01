package view.user;

import dao.user.UserReservationInfoDao;
import dao.user.UserReservationDeleteDao;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReservationInfo extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private UserReservationInfoDto selectedReservation;
    private Map<Integer, UserReservationInfoDto> reservationMap;
    private JFrame userStartViewFrame;

    public UserReservationInfo(JFrame userStartViewFrame) {
        this.userStartViewFrame = userStartViewFrame;
        reservationMap = new HashMap<>();
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("User Reservation Info");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"영화 제목", "상영 날짜", "상영관 번호", "좌석 열 번호", "좌석 행 번호", "판매 가격"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        styleTable(table);
        table.setFillsViewportHeight(true);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                selectedReservation = reservationMap.get(selectedRow);
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = createStyledButton("예매 정보 자세히 보기");
        refreshButton.addActionListener(e -> {
            if (selectedReservation != null) {
                showReservationDetails(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(refreshButton);

        JButton deleteButton = createStyledButton("예매 삭제");
        deleteButton.addActionListener(e -> {
            if (selectedReservation != null) {
                int confirmation = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    deleteReservation(selectedReservation);
                }
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(deleteButton);

        JButton changeMovieButton = createStyledButton("다른 영화 선택");
        changeMovieButton.addActionListener(e -> {
            if (selectedReservation != null) {
                openMovieSelectionPage(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(changeMovieButton);

        JButton changeScreeningTimeButton = createStyledButton("다른 상영 시간 선택");
        changeScreeningTimeButton.addActionListener(e -> {
            if (selectedReservation != null) {
                openScreeningScheduleSelectionPage(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(changeScreeningTimeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                userStartViewFrame.setVisible(true);
            }
        });
    }

    private void loadData() {
        long memberId = 1L; // 필요한 경우 적절하게 설정하세요
        UserReservationInfoDao dao = new UserReservationInfoDao();
        List<UserReservationInfoDto> reservations = dao.getUserReservationInfo(memberId);

        tableModel.setRowCount(0);
        reservationMap.clear();

        int row = 0;
        for (UserReservationInfoDto reservation : reservations) {
            Object[] rowData = {
                    reservation.title(),
                    reservation.startDate(),
                    reservation.screenId(),
                    reservation.rowNum(),
                    reservation.columnNum(),
                    reservation.sellingPrice()
            };
            tableModel.addRow(rowData);
            reservationMap.put(row, reservation);
            row++;
        }
    }

    private void deleteReservation(UserReservationInfoDto reservation) {
        UserReservationDeleteDao dao = new UserReservationDeleteDao();
        boolean success = dao.deleteReservation(reservation.reservationId());
        if (success) {
            JOptionPane.showMessageDialog(this, "예매가 취소되었습니다", "성공", JOptionPane.INFORMATION_MESSAGE);
            loadData(); // 데이터 갱신
        } else {
            JOptionPane.showMessageDialog(this, "예매 삭제에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openMovieSelectionPage(UserReservationInfoDto reservation) {
        new SearchMovieView(reservation.memberId(), this, reservation).setVisible(true);
        setVisible(false); // 현재 창 숨기기
    }

    private void openScreeningScheduleSelectionPage(UserReservationInfoDto reservation) {
        new MovieSchedulingInfoView(reservation.movieId(), reservation.memberId(), this, reservation).setVisible(true);
        setVisible(false); // 현재 창 숨기기
    }

    private void showReservationDetails(UserReservationInfoDto reservation) {
        UserReservationDetails details = new UserReservationDetails(this, reservation);
        details.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(Color.GRAY);
    }
}
