package view.user;

import dao.user.UserReservationInfoDao;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserReservationInfo extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private UserReservationInfoDto selectedReservation;

    public UserReservationInfo() {
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("User Reservation Info");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"영화 제목", "상영 날짜", "상영관 번호", "좌석 열 번호", "좌석 행 번호", "판매 가격", "Reservation Object"};
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
                selectedReservation = (UserReservationInfoDto) tableModel.getValueAt(selectedRow, 6); // 6번째 열을 사용하여 객체를 가져옴
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshButton = new JButton("예매 정보 자세히 보기");
        refreshButton.addActionListener(e -> {
            if (selectedReservation != null) {
                showReservationDetails(selectedReservation);
            } else {
                JOptionPane.showMessageDialog(this, "예약 정보를 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(refreshButton, BorderLayout.SOUTH);
    }

    private void loadData() {
        UserReservationInfoDao dao = new UserReservationInfoDao();
        List<UserReservationInfoDto> reservations = dao.getUserReservationInfo();

        tableModel.setRowCount(0);

        for (UserReservationInfoDto reservation : reservations) {
            Object[] rowData = {
                    reservation.title(),
                    reservation.startDate(),
                    reservation.screenId(),
                    reservation.rowNum(),
                    reservation.columnNum(),
                    reservation.sellingPrice(),
                    reservation
            };
            tableModel.addRow(rowData);
        }

        table.removeColumn(table.getColumn("Reservation Object"));
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
