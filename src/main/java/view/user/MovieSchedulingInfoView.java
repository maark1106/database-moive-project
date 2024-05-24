package view.user;

import dao.user.ScreeningScheduleDao;
import dto.ScreeningScheduleDto;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovieSchedulingInfoView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<ScreeningScheduleDto> schedules;
    private Long memberId;
    private JFrame previousFrame;
    private UserReservationInfoDto reservationToChange;

    public MovieSchedulingInfoView(Long movieId, Long memberId, JFrame previousFrame, UserReservationInfoDto reservationToChange) {
        this.memberId = memberId;
        this.previousFrame = previousFrame;
        this.reservationToChange = reservationToChange;
        initUI(movieId);
    }

    private void initUI(Long movieId) {
        setTitle("Movie Scheduling Info");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                previousFrame.setVisible(true); // 이전 창 다시 표시
            }
        });

        String[] columnNames = {"ID", "Start Date", "Day of Week", "Round", "Start Time", "Selling Price", "Standard Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadSchedules(movieId);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton bookSeatButton = new JButton("좌석 예매");
        bookSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSeat();
            }
        });
        add(bookSeatButton, BorderLayout.SOUTH);
    }

    private void loadSchedules(Long movieId) {
        ScreeningScheduleDao dao = new ScreeningScheduleDao();
        schedules = dao.getScreeningSchedules(movieId);

        tableModel.setRowCount(0);
        for (ScreeningScheduleDto schedule : schedules) {
            Object[] rowData = {
                    schedule.id(),
                    schedule.startDate(),
                    schedule.dayOfWeek(),
                    schedule.round(),
                    schedule.startTime(),
                    schedule.sellingPrice(),
                    schedule.standardPrice()
            };
            tableModel.addRow(rowData);
        }
    }

    private void bookSeat() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "좌석을 예매하려면 상영 일정을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ScreeningScheduleDto selectedSchedule = schedules.get(selectedRow);
        new ShowSeat(selectedSchedule.screenId(), selectedSchedule.movieId(), selectedSchedule.id(), memberId, this, reservationToChange).setVisible(true);
        setVisible(false); // 현재 창 숨기기
    }
}
