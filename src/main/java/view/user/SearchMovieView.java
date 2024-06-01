package view.user;

import dao.user.SearchMovieDao;
import dto.MovieDto;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchMovieView extends JFrame {

    private JTextField titleField;
    private JTextField directorField;
    private JTextField actorField;
    private JTextField genreField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<MovieDto> movies;
    private Long memberId;
    private JFrame previousFrame;
    private UserReservationInfoDto reservationToChange;

    // Constructor for changing a reservation
    public SearchMovieView(Long memberId, JFrame previousFrame, UserReservationInfoDto reservationToChange) {
        this.memberId = memberId;
        this.previousFrame = previousFrame;
        this.reservationToChange = reservationToChange;
        initUI();
    }

    // Constructor for normal use (no reservation change)
    public SearchMovieView(Long memberId, JFrame previousFrame) {
        this(memberId, previousFrame, null);
    }

    private void initUI() {
        setTitle("Search Movie Service");
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

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        searchPanel.add(createStyledLabel("영화명:"), gbc);
        titleField = createStyledTextField();
        gbc.gridx = 1;
        searchPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(createStyledLabel("감독명:"), gbc);
        directorField = createStyledTextField();
        gbc.gridx = 1;
        searchPanel.add(directorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(createStyledLabel("배우명:"), gbc);
        actorField = createStyledTextField();
        gbc.gridx = 1;
        searchPanel.add(actorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(createStyledLabel("장르:"), gbc);
        genreField = createStyledTextField();
        gbc.gridx = 1;
        searchPanel.add(genreField, gbc);

        JButton searchButton = createStyledButton("조회");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMovies();
            }
        });

        add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "영화명", "상영시간", "감독명", "배우명", "장르", "설명", "개봉일", "평점", "연령제한"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        styleTable(resultTable);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        JButton showScheduleButton = createStyledButton("영화 상영 일정 확인");
        showScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMovieSchedule();
            }
        });
        add(showScheduleButton, BorderLayout.SOUTH);
    }

    private void searchMovies() {
        String title = titleField.getText();
        String director = directorField.getText();
        String actor = actorField.getText();
        String genre = genreField.getText();

        SearchMovieDao dao = new SearchMovieDao();
        movies = dao.searchMovies(title, director, actor, genre);

        tableModel.setRowCount(0);
        for (MovieDto movie : movies) {
            Object[] rowData = {
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
            tableModel.addRow(rowData);
        }
    }

    private void showMovieSchedule() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "상영 일정을 보기 위해 영화를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long movieId = (Long) tableModel.getValueAt(selectedRow, 0);
        new MovieSchedulingInfoView(movieId, memberId, this, reservationToChange).setVisible(true);
        setVisible(false); // 현재 창 숨기기
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setBackground(new Color(245, 245, 245));
        table.setGridColor(Color.GRAY);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25)); // 입력 칸 크기를 설정
        return textField;
    }
}
