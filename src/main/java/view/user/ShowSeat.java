package view.user;

import dao.user.ScreenDao;
import dao.user.SeatDao;
import dao.user.UserReservationCreateDao;
import dao.user.UserReservationDeleteDao;
import dto.*;
import view.StartView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ShowSeat extends JFrame {

    private Long screenId;
    private Long movieId;
    private Long screeningScheduleId;
    private Long memberId;
    private JFrame previousFrame;
    private UserReservationInfoDto reservationToChange;
    private ScreenDto screen;
    private List<SeatDto> seats;
    private List<JButton> seatButtons;
    private List<SeatDto> selectedSeats;

    public ShowSeat(Long screenId, Long movieId, Long screeningScheduleId, Long memberId, JFrame previousFrame, UserReservationInfoDto reservationToChange) {
        this.screenId = screenId;
        this.movieId = movieId;
        this.screeningScheduleId = screeningScheduleId;
        this.memberId = memberId;
        this.previousFrame = previousFrame;
        this.reservationToChange = reservationToChange;
        this.seatButtons = new ArrayList<>();
        this.selectedSeats = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("좌석 예매");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                previousFrame.setVisible(true); // 이전 창 다시 표시
            }
        });

        loadScreenInfo();
        loadSeats();

        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(Color.BLACK);
        screenPanel.setPreferredSize(new Dimension(800, 50));
        screenPanel.add(new JLabel("SCREEN", SwingConstants.CENTER) {
            {
                setForeground(Color.WHITE);
            }
        });
        add(screenPanel, BorderLayout.NORTH);

        JPanel seatPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 좌석 간격을 더 크게 설정

        int rowSeats = screen.rowSeats();
        int columnSeats = screen.columnSeats();
        int startRow = (10 - rowSeats) / 2; // 10x10 grid에서 중앙에 배치하기 위한 시작 행 계산
        int startCol = (10 - columnSeats) / 2; // 10x10 grid에서 중앙에 배치하기 위한 시작 열 계산

        for (int row = 1; row <= rowSeats; row++) {
            for (int col = 1; col <= columnSeats; col++) {
                final int currentRow = row;
                final int currentCol = col;
                SeatDto seat = findSeat(currentRow, currentCol);
                JButton seatButton = new JButton(currentRow + "-" + currentCol);
                seatButton.setEnabled(seat == null || !seat.isUsed());
                seatButton.setBackground(seat != null && seat.isUsed() ? Color.RED : Color.LIGHT_GRAY);
                seatButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (seatButton.getBackground() == Color.LIGHT_GRAY) {
                            seatButton.setBackground(Color.DARK_GRAY);
                            selectedSeats.add(new SeatDto(null, true, currentRow, currentCol, screenId, screeningScheduleId));
                        } else if (seatButton.getBackground() == Color.DARK_GRAY) {
                            seatButton.setBackground(Color.LIGHT_GRAY);
                            selectedSeats.removeIf(s -> s.rowNum() == currentRow && s.columnNum() == currentCol);
                        }
                    }
                });
                seatButtons.add(seatButton);

                gbc.gridx = startCol + currentCol - 1;
                gbc.gridy = startRow + currentRow - 1;
                seatPanel.add(seatButton, gbc);
            }
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel entranceLabel = new JLabel("ENTRANCE", SwingConstants.LEFT);
        bottomPanel.add(entranceLabel, BorderLayout.WEST);

        add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        JButton reserveButton = createStyledButton("좌석 예매하기");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveSeats();
            }
        });

        add(reserveButton, BorderLayout.SOUTH);
    }

    private SeatDto findSeat(int row, int col) {
        for (SeatDto seat : seats) {
            if (seat.rowNum() == row && seat.columnNum() == col) {
                return seat;
            }
        }
        return null;
    }

    private void loadScreenInfo() {
        ScreenDao screenDao = new ScreenDao();
        screen = screenDao.getScreenById(screenId);

        if (screen == null) {
            JOptionPane.showMessageDialog(this, "스크린 정보를 불러오는 데 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void loadSeats() {
        SeatDao seatDao = new SeatDao();
        seats = seatDao.getSeatsByScreeningScheduleId(screeningScheduleId);
    }

    private void reserveSeats() {
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "좌석을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserReservationCreateDao reservationDao = new UserReservationCreateDao();
        UserReservationDeleteDao deleteDao = new UserReservationDeleteDao();

        ReservationDto reservation = new ReservationDto(null, "Credit Card", true, selectedSeats.size() * 10000, new Date(System.currentTimeMillis()), memberId);
        Long reservationId = reservationDao.createReservation(reservation);

        if (reservationId == null) {
            JOptionPane.showMessageDialog(this, "예약 생성에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean allSuccess = true;
        for (SeatDto seat : selectedSeats) {
            Long seatId = reservationDao.createSeat(seat);
            if (seatId == null) {
                allSuccess = false;
                break;
            }
            TicketDto ticket = new TicketDto(null, true, screeningScheduleId, seatId, reservationId, screenId);
            if (!reservationDao.createTicket(ticket)) {
                allSuccess = false;
                break;
            }
        }

        if (allSuccess) {
            if (reservationToChange != null) {
                if (!deleteDao.deleteReservation(reservationToChange.reservationId())) {
                    JOptionPane.showMessageDialog(this, "이전 예약 삭제에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "예매가 성공적으로 변경되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "예매가 성공적으로 완료되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
            }
            new UserStartView(new StartView()).setVisible(true);
            previousFrame.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "예매 처리 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
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
