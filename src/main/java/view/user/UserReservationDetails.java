package view.user;

import dao.user.ReservationDetailsDao;
import dto.ReservationDetailsDto;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserReservationDetails extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;

    public UserReservationDetails(Frame parent, UserReservationInfoDto reservation) {
        super(parent, "Reservation Details", true);
        setSize(1200, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columnNames = {"예매자 이름", "영화 상영일", "상영 요일", "회차", "시작 시간", "상영관 번호", "결제 날짜", "결제 총액", "결제 상태"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadReservationDetails(reservation);
    }

    private void loadReservationDetails(UserReservationInfoDto reservation) {
        ReservationDetailsDao dao = new ReservationDetailsDao();
        List<ReservationDetailsDto> details = dao.getReservationDetails(reservation);

        tableModel.setRowCount(0);

        for (ReservationDetailsDto detail : details) {
            Object[] rowData = {
                    detail.name(),
                    detail.startDate(),
                    detail.dayOfWeek(),
                    detail.round(),
                    detail.startTime(),
                    detail.screenId(),
                    detail.paymentDate(),
                    detail.paymentAmount(),
                    detail.paymentStatus()
            };
            tableModel.addRow(rowData);
        }
    }
}
