package view.admin;

import dao.admin.AdminInitializeDao;
import dao.admin.AdminSqlInputDao;
import dao.admin.AdminTableDao;
import dto.*;
import view.StartView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        adminPanel.setLayout(new GridBagLayout());
        adminPanel.setBackground(new Color(45, 45, 45));
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton initializeButton = createStyledButton("초기화");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(initializeButton, gbc);
        initializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInitializeDao dao = new AdminInitializeDao();
                dao.initialize();
            }
        });

        JButton inputButton = createStyledButton("입력");
        gbc.gridx = 1;
        panel.add(inputButton, gbc);
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertDataView();
            }
        });

        JButton deleteUpdateButton = createStyledButton("삭제/변경");
        gbc.gridx = 2;
        panel.add(deleteUpdateButton, gbc);
        deleteUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminSqlInputDao dao = new AdminSqlInputDao();
                String sql = JOptionPane.showInputDialog(panel, "SQL 문을 입력하세요:");
                dao.sqlInput(sql, panel);
            }
        });

        JButton viewTableButton = createStyledButton("전체 테이블 보기");
        gbc.gridx = 3;
        panel.add(viewTableButton, gbc);
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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
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

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(Color.GRAY);
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
        styleTable(table);
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
        styleTable(table);
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
        styleTable(table);
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
        styleTable(table);
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
        styleTable(table);
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
        styleTable(table);
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
        styleTable(table);
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
