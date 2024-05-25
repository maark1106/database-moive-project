package view.admin;

import dao.admin.AdminInitializeDao;
import dao.admin.AdminSqlInputDao;
import dao.admin.AdminTableDao;
import dto.MemberDto;
import dto.MovieDto;
import dto.ReservationDto;
import dto.ScreenDto;
import dto.ScreeningScheduleDto;
import dto.SeatDto;
import dto.TicketDto;
import view.StartView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminStartView extends JFrame {

    public AdminStartView(StartView parent) {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        getContentPane().add(adminPanel);
        adminButton(adminPanel);

        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.setVisible(true);
            }
        });
    }

    private void adminButton(JPanel panel) {
        panel.setLayout(null);

        // 초기화 버튼
        JButton initializeButton = new JButton("초기화");
        initializeButton.setBounds(30, 30, 100, 30);
        panel.add(initializeButton);
        initializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInitializeDao dao = new AdminInitializeDao();
                dao.initialize();
            }
        });

        // 입력 버튼
        JButton inputButton = new JButton("입력");
        inputButton.setBounds(150, 30, 100, 30);
        panel.add(inputButton);
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertDataView();
            }
        });

        // 삭제/변경 버튼
        JButton deleteUpdateButton = new JButton("삭제/변경");
        deleteUpdateButton.setBounds(270, 30, 100, 30);
        panel.add(deleteUpdateButton);
        deleteUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminSqlInputDao dao = new AdminSqlInputDao();
                String sql = JOptionPane.showInputDialog(panel, "SQL 문을 입력하세요:");
                dao.sqlInput(sql, panel);
            }
        });

        // 전체 테이블 보기 버튼
        JButton viewTableButton = new JButton("전체 테이블 보기");
        viewTableButton.setBounds(390, 30, 150, 30);
        panel.add(viewTableButton);
        viewTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame = new JFrame("Database Tables");
                tableFrame.setSize(800, 600);
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableFrame.setLocationRelativeTo(null);

                JPanel panel = new JPanel(new BorderLayout());
                JTabbedPane tabbedPane = new JTabbedPane();
                panel.add(tabbedPane, BorderLayout.CENTER);

                showAllTables(tabbedPane);

                tableFrame.add(panel);
                tableFrame.setVisible(true);
            }
        });
    }

    private void showAllTables(JTabbedPane tabbedPane) {
        AdminTableDao dao = new AdminTableDao();

        List<MovieDto> movies = dao.getAllMovies();
        showMovies(tabbedPane, movies);

        List<MemberDto> members = dao.getAllMembers();
        showMembers(tabbedPane, members);

        List<ScreenDto> screens = dao.getAllScreens();
        showScreens(tabbedPane, screens);

        List<ScreeningScheduleDto> schedules = dao.getAllScreenSchedules();
        showScreeningSchedules(tabbedPane, schedules);

        List<SeatDto> seats = dao.getAllSeats();
        showSeats(tabbedPane, seats);

        List<ReservationDto> reservations = dao.getAllReservations();
        showReservations(tabbedPane, reservations);

        List<TicketDto> tickets = dao.getAllTickets();
        showTickets(tabbedPane, tickets);
    }

    private void showMovies(JTabbedPane tabbedPane, List<MovieDto> movies) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집할 수 없도록 설정
            }
        };
        String[] columnNames = {"ID", "Title", "Running Time", "Director", "Actor", "Genre", "Description", "Release Date", "Rating", "Age Limit"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (MovieDto movie : movies) {
            Object[] row = {
                    movie.id(),
                    movie.title(),
                    movie.runningTime(),
                    movie.director(),
                    movie.actor(),
                    movie.genre(),
                    movie.description(),
                    movie.releaseDate(),
                    movie.rating(),
                    movie.ageLimit()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Movies", scrollPane);
    }

    private void showMembers(JTabbedPane tabbedPane, List<MemberDto> members) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Name", "Phone Number", "Email"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (MemberDto member : members) {
            Object[] row = {
                    member.id(),
                    member.name(),
                    member.phoneNumber(),
                    member.email()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Members", scrollPane);
    }

    private void showScreens(JTabbedPane tabbedPane, List<ScreenDto> screens) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Seat Count", "Is Used", "Column Seats", "Row Seats"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (ScreenDto screen : screens) {
            Object[] row = {
                    screen.id(),
                    screen.seatCount(),
                    screen.isUsed(),
                    screen.columnSeats(),
                    screen.rowSeats()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Screens", scrollPane);
    }

    private void showScreeningSchedules(JTabbedPane tabbedPane, List<ScreeningScheduleDto> schedules) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Start Date", "Day of Week", "Round", "Start Time", "Movie ID", "Screen ID", "Selling Price", "Standard Price"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (ScreeningScheduleDto schedule : schedules) {
            Object[] row = {
                    schedule.id(),
                    schedule.startDate(),
                    schedule.dayOfWeek(),
                    schedule.round(),
                    schedule.startTime(),
                    schedule.movieId(),
                    schedule.screenId(),
                    schedule.sellingPrice(),
                    schedule.standardPrice()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Screening Schedules", scrollPane);
    }

    private void showSeats(JTabbedPane tabbedPane, List<SeatDto> seats) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Is Used", "Row Number", "Column Number", "Screen ID"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (SeatDto seat : seats) {
            Object[] row = {
                    seat.id(),
                    seat.isUsed(),
                    seat.rowNum(),
                    seat.columnNum(),
                    seat.screenId()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Seats", scrollPane);
    }

    private void showReservations(JTabbedPane tabbedPane, List<ReservationDto> reservations) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Payment Method", "Payment Status", "Payment Amount", "Payment Date", "Member ID"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (ReservationDto reservation : reservations) {
            Object[] row = {
                    reservation.id(),
                    reservation.paymentMethod(),
                    reservation.paymentStatus(),
                    reservation.paymentAmount(),
                    reservation.paymentDate(),
                    reservation.memberId()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Reservations", scrollPane);
    }

    private void showTickets(JTabbedPane tabbedPane, List<TicketDto> tickets) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"ID", "Is Ticketed", "Screening Schedule ID", "Seat ID", "Reservation ID", "Screen ID"};
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        for (TicketDto ticket : tickets) {
            Object[] row = {
                    ticket.id(),
                    ticket.isTicketed(),
                    ticket.screeningScheduleId(),
                    ticket.seatId(),
                    ticket.reservationId(),
                    ticket.screenId()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Tickets", scrollPane);
    }
}
