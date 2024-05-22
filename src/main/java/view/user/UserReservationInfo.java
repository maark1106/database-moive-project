package view.user;

import dao.user.UserReservationInfoDao;
import dao.user.UserReservationDeleteDao;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReservationInfo extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private UserReservationInfoDto selectedReservation;
    private Map<Integer, UserReservationInfoDto> reservationMap;

    public UserReservationInfo() {
        reservationMap = new HashMap<>();
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("User Reservation Info");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"영화 제목", "상영 날짜", "상영관 번호", "좌석 열 번호", "좌석 행 번호", "판매 가격"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                selectedReservation = reservationMap.get(selectedRow);
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("예매 정보 자세히 보기");
        refreshButton.addActionListener(e -> {
            if (selectedReservation != null) {
                showReservationDetails(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(refreshButton);

        JButton deleteButton = new JButton("예매 삭제");
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

        JButton changeMovieButton = new JButton("다른 영화 선택");
        changeMovieButton.addActionListener(e -> {
            if (selectedReservation != null) {
                openMovieSelectionPage(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(changeMovieButton);

        JButton changeScreeningTimeButton = new JButton("다른 상영 시간 선택");
        changeScreeningTimeButton.addActionListener(e -> {
            if (selectedReservation != null) {
                openScreeningScheduleSelectionPage(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(changeScreeningTimeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        UserReservationInfoDao dao = new UserReservationInfoDao();
        List<UserReservationInfoDto> reservations = dao.getUserReservationInfo();

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
        boolean isChanged = false;
        // 상우 영화 선택 페이지 호출
        // 성공하면 isChanged true, or false

        if (isChanged) {
            UserReservationDeleteDao dao = new UserReservationDeleteDao();
            boolean success = dao.deleteReservation(reservation.reservationId());

            if (success) {
                JOptionPane.showMessageDialog(this, "예매가 변경되었습니다", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // 데이터 갱신
            } else {
                JOptionPane.showMessageDialog(this, "예매 변경이 취소되었습니다", "취소", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openScreeningScheduleSelectionPage(UserReservationInfoDto reservation) {
        boolean isChanged = false;
        // 상우 상영 시간 선택 페이지 호출
        // 여기서 movie title 넘겨줘서 해당 영화의 상영 정보 페이지 뜨게 하기
        // 성공하면 isChanged true, or false

        if (isChanged) {
            UserReservationDeleteDao dao = new UserReservationDeleteDao();
            boolean success = dao.deleteReservation(reservation.reservationId());

            if (success) {
                JOptionPane.showMessageDialog(this, "상영 시간이 변경되었습니다", "성공", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // 데이터 갱신
            } else {
                JOptionPane.showMessageDialog(this, "상영 시간 변경이 취소되었습니다", "취소", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showReservationDetails(UserReservationInfoDto reservation) {
        UserReservationDetails details = new UserReservationDetails(this, reservation);
        details.setVisible(true);
    }

    public static void main(String[] args) {
        UserReservationInfo userReservationInfo = new UserReservationInfo();
        userReservationInfo.setVisible(true);
    }
}
